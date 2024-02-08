package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.Paging;

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
		
		
		
		int updateCount = es.updateEmp(emp);
		
		model.addAttribute(null, model)
		
		return "forward:listEmp";
		
	}
	
}
