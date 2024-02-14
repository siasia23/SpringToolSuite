package com.oracle.oBootMybatis01.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.Paging;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor	// 이 부분이 DI
@Slf4j
public class EmpController {

	private final EmpService es;
	private final JavaMailSender mailSender;
	
	@RequestMapping(value = "listEmp")
	public String empList(Emp emp, Model model) {
		
		System.out.println("EmpController empList() Start!");
		
//		Paging class 에서 이미 방어 함
//		if (emp.getCurrentPage() == null) emp.setCurrentPage("1");
		
		int totalEmp = es.totalEmp();
		System.out.println("EmpController empList() totalEmp : " + totalEmp);
		
		// 페이지 작업
		Paging page = new Paging(totalEmp, emp.getCurrentPage());
		emp.setStart(page.getStart());
		emp.setEnd(page.getEnd());
		
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController empList() listEmp.size() : " + listEmp.size());
		
		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp", listEmp);
		model.addAttribute("page", page);
		
		return "list";
		
	}
	
	@GetMapping(value = "detailEmp")
	public String detailEmp(Emp emp1, Model model) {
		
		System.out.println("EmpController detailEmp() Start!");
		
		Emp emp = es.detailEmp(emp1.getEmpno());
		
		model.addAttribute("emp", emp);
		
		return "detailEmp";
		
	}
	
	@GetMapping(value = "updateFormEmp")
	public String updateFormEmp(Emp emp1, Model model) {
		
		System.out.println("EmpController updateFormEmp() Start!");
		
		Emp emp = es.detailEmp(emp1.getEmpno());
		System.out.println("emp.getEname() : " + emp.getEname());
		System.out.println("emp.getHiredate() : " + emp.getHiredate());
		
//		System.out.println("hiredate : " + hiredate);
		
		// 문제 
		// 1. DTO : String hiredate
		// 2. View : 단순조회 OK ,JSP에서 input type="date" 문제 발생
		// 3. 해결책 : 년월일만 짤라 넣어 주어야 함
		
		String hiredate = "";
		
		if (emp.getHiredate() != null) {
			
			hiredate = emp.getHiredate().substring(0, 10);
			emp.setHiredate(hiredate);
			
		}

		System.out.println("hiredate : " + hiredate);
		
		model.addAttribute("emp", emp);
		
		return "updateFormEmp";
		
	}
	
	@PostMapping(value = "updateEmp")
	public String updateEmp(Emp emp, Model model) {
		
		log.info("EmpController updateEmp() Start!");
		System.out.println("EmpController updateEmp() Start!");
		
		int updateCount = es.updateEmp(emp);
		
		model.addAttribute("emp", emp);
		
		model.addAttribute("uptCnt", updateCount);
		model.addAttribute("kk3", "Message Test");
		
//		return "forward:listEmp";		// model에 저장한 parameter를 갖고 이동
		return "redirect:listEmp";		// 단순히 페이지만 이동
		
	}
	
	@RequestMapping(value = "writeFormEmp")
	public String writeFormEmp(Model model) {
		
		System.out.println("EmpController writeFormEmp() Start!");
		
		// 매니저들만 리스트로 뽑자
		List<Emp> empList = es.listManager();
		
		model.addAttribute("empMngList", empList);
		System.out.println("EmpController writeFormEmp() empList.size() : " + empList.size());
		
		// 부서명만 리스트로 뽑자
		List<Dept> deptList = es.deptSelect();
		
		model.addAttribute("deptList", deptList);
		System.out.println("EmpController writeFormEmp() deptList.size() : " + deptList.size());
		
		return "writeFormEmp";
		
	}
	
	@PostMapping(value = "writeEmp")
	public String writeEmp(Emp emp, Model model) {
		
		System.out.println("EmpController writeEmp() Start!");
		
		int insertResult = es.insertEmp(emp);
		
		if (insertResult > 0) return "redirect:listEmp";
		
		else {
			
			model.addAttribute("msg", "입력 실패");
			
			return "forward:writeFormEmp";
			
		}
		
	}
	
	@RequestMapping(value = "writeFormEmp3")
	public String writeFormEmp3(Model model) {
		
		System.out.println("EmpController writeFormEmp3() Start!");
		
		// 매니저들만 리스트로 뽑자
		List<Emp> empList = es.listManager();
		
		model.addAttribute("empMngList", empList);
		System.out.println("EmpController writeFormEmp3() empList.size() : " + empList.size());
		
		// 부서명만 리스트로 뽑자
		List<Dept> deptList = es.deptSelect();
		
		model.addAttribute("deptList", deptList);
		System.out.println("EmpController writeFormEmp3() deptList.size() : " + deptList.size());
		
		return "writeFormEmp3";
		
	}
	
	@PostMapping(value = "writeEmp3")
	public String writeEmp3(@ModelAttribute("emp") @Valid Emp emp,
										BindingResult result,
										Model model) {
		
		System.out.println("EmpController writeEmp3() Start!");
		
		if (result.hasErrors()) {
			
			System.out.println("EmpController writeEmp3() hasErrors!");
			
			model.addAttribute("msg", "BindingResult 입력 실패");
			
			return "forward:writeFormEmp3";
			
		}
		
		int insertResult = es.insertEmp(emp);
		
		if (insertResult > 0) return "redirect:listEmp";
		
		else {
			
			model.addAttribute("msg", "입력 실패");
			
			return "forward:writeFormEmp3";
			
		}
		
	}
	
	@GetMapping(value = "confirm")
	public String confirm(Emp emp1, Model model) {
		
		Emp emp = es.detailEmp(emp1.getEmpno());
		
		model.addAttribute("empno", emp1.getEmpno());
		
		if (emp != null) {
			
			System.out.println("EmpController confirm() 중복된 사번");
			
			model.addAttribute("msg", "중복된 사번");
			
			return "forward:writeFormEmp";
			
		} else {
			
			System.out.println("EmpController confirm() 사용 가능한 사번");
			
			model.addAttribute("msg", "사용 가능한 사번");
			
			return "forward:writeFormEmp";
			
		}
		
	}
	
	@RequestMapping(value = "deleteEmp")
	public String deleteEmp(Emp emp, Model model) {
		
		System.out.println("EmpController deleteEmp() Start!");
		
		int result = es.deleteEmp(emp.getEmpno());
		
		return "redirect:listEmp";
		
	}
	
	@RequestMapping(value = "listSearch3")
	public String listSearch3(Emp emp, Model model) {
		
		System.out.println("EmpController listSearch3() Start!");
		
		int totalEmp = es.condTotalEmp(emp);	// conditionTotalEmp
		System.out.println("EmpController listSearch3() totalEmp : " + totalEmp);
		
		// 페이징 작업
		Paging page = new Paging(totalEmp, emp.getCurrentPage());
		
		emp.setStart(page.getStart());
		emp.setEnd(page.getEnd());
		
		List<Emp> listSearchEmp = es.listSearchEmp(emp);
		System.out.println("listSearchEmp.size() : " + listSearchEmp.size());
		
		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp", listSearchEmp);
		model.addAttribute("page", page);
		
		return "list";
		
	}
	
	@GetMapping(value = "listEmpDept")
	public String listEmpDept(Model model) {
		
		System.out.println("EmpController listEmpDept() Start!");
		
		List<EmpDept> listEmpDept = es.listEmpDept();
		
		model.addAttribute("listEmpDept", listEmpDept);
		
		return "listEmpDept";
		
	}
	
	@RequestMapping(value = "mailTransport")
	public String mailTransport(HttpServletRequest request, Model model) {
		
		System.out.println("mailSending...");
		
		// 받는 사람 이메일
		String tomail = "ttaekwang@naver.com";
		System.out.println(tomail);
		
		// 보내는 사람 이메일
		String setfrom = "amazing9.code@gmail.com";
		
		// 제목
		String title = "mailTransport 입니다.";
		
		try {
			
			// Mime 전자우편 인터넷 표준 format
			MimeMessage message = mailSender.createMimeMessage();
			
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			messageHelper.setFrom(setfrom);		// 보내는 사람 생략하면 정상작동 안 함
			messageHelper.setTo(tomail);			// 받는 사람 이메일
			messageHelper.setSubject(title);		// 메일 제목은 생략 가능
			
			// 랜덤하게 임시 비밀번호 생성
			String tempPassword = (int) (Math.random() * 999999) + 1 + "";
			
			// 메일 내용
			messageHelper.setText("임시 비밀번호입니다 : " + tempPassword);
			System.out.println("임시 비밀번호입니다 : " + tempPassword);
			
			mailSender.send(message);
			
			model.addAttribute("check", 1);			// 정상 전달
			
		} catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("mailTransport e.getMessage()" + e.getMessage());
			
			model.addAttribute("check", 2);			// 메일 전달 실패
			
		}
		
		return "mailResult";
		
	}
	
	// Procedure Test 입력화면
	@RequestMapping(value = "writeDeptIn")
	public String writeDeptIn(Model model) {
		
		System.out.println("writeDeptIn Start!");
		
		return "writeDept3";
		
	}
	
	// Procedure 방식
	// 프로시저 통해 Dept 입력 후 VO 전달
	@PostMapping(value = "writeDept")
	public String writeDept(DeptVO deptVO, Model model) {
		
		es.insertDept(deptVO);
		
		if (deptVO == null) System.out.println("deptVO null");
		
		else {
			
			System.out.println("deptVO.getOdeptno() : " + deptVO.getOdeptno());
			System.out.println("deptVO.getOdname() : " + deptVO.getOdname());
			System.out.println("deptVO.getOloc() : " + deptVO.getOloc());
			
			model.addAttribute("msg", "정상 입력 되었습니다.");
			model.addAttribute("dept", deptVO);
			
		}
		
		return "writeDept3";
		
	}
	
	// Map 방식 
	// 장단점 : 개발 시 유연함. 유지보수 시에는 어려움.
	@GetMapping(value = "writeDeptCursor")
	public String writeDeptCursor(Model model) {
		
		System.out.println("EmpController writeDeptCursor Start!");
		
		// 부서 범위 조회
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("sDeptno", 10);		// start deptno
		map.put("eDeptno", 55);		// end deptno
		
		es.selListDept(map);
		
		List<Dept> deptLists = (List<Dept>) map.get("dept");
		
		for (Dept dept : deptLists) {
			
			System.out.println("dept.getDname() : " + dept.getDname());
			System.out.println("dept.getLoc() : " + dept.getLoc());
			
		}
		
		System.out.println("deptLists.size() : " + deptLists.size());
		
		model.addAttribute("deptList", deptLists);
		
		return "writeDeptCursor";
		
	}
	
}
