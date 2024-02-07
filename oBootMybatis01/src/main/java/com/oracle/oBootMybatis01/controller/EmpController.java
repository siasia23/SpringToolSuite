package com.oracle.oBootMybatis01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.service.EmpService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EmpController {

	private final EmpService es;
	
	public String empList(Emp emp, Model model) {
		
		System.out.println("EmpController empList() Start!");
		
		int totalEmp = es.totalEmp();
		
		System.out.println("EmpController empList() totalEmp : " + totalEmp);
		
		return "list";
		
	}
	
}
