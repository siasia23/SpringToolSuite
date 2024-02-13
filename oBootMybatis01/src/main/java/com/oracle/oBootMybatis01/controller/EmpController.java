package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.Paging;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor	// 이 부분이 DI
@Slf4j
public class EmpController {

	private final EmpService es;
	
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
		
		int totalEmp = es.condTotalEmp(emp);
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
	
}
