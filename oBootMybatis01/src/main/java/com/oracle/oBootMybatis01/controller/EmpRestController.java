package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.SampleVO;
import com.oracle.oBootMybatis01.service.EmpService;

import lombok.RequiredArgsConstructor;

@RestController		// = @Controller + @ResponseBody
@RequiredArgsConstructor
public class EmpRestController {

	private final EmpService es;
	
	@RequestMapping(value = "/helloText")
	public String helloText() {
		
		System.out.println("EmpRestController Start!");
		
		String hello = "안녕";
		
		return hello;
		
		// String return 하면
		// HttpMessageConverter 가
		// StringConverter 를 발동시킴!
		
	}
	
	// http://jsonviewer.stack.hu/
	@RequestMapping(value = "/sample/sendVO2")
	public SampleVO sendVO2(Dept dept) {
		
		System.out.println("@RestController dept.getDeptno() : " + dept.getDeptno());
		
		SampleVO vo = new SampleVO();
		
		vo.setFirstName("짱구");
		vo.setLastName("윤");
		vo.setMno(dept.getDeptno());
		
		return vo;
		
		// Instance return 하면
		// HttpMessageConverter 가
		// JsonConverter 를 발동시킴!
		
	}
	
	@RequestMapping("/sendVO3")
	public List<Dept> sendVO3() {
		
		System.out.println("@RestController sendVO3 Start!");
		
		List<Dept> deptList = es.deptSelect();
		
		return deptList;
		
		// Instance return 하면
		// HttpMessageConverter 가
		// JsonConverter 를 발동시킴!
		
	}
	
	@RequestMapping("/empnoDelete")
	public String empnoDelete(Emp emp) {
		
		System.out.println("@RestController empnoDelete Start!");
		System.out.println("emp.getEname() : " + emp.getEname());
		
		int delStatus = es.deleteEmp(emp.getEmpno());
		
		String delStatusStr = Integer.toString(delStatus);
		
		return delStatusStr;
		
	}
	
}
