package com.oracle.oBootMybatis01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.EmpDao;
import com.oracle.oBootMybatis01.model.Emp;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor	// 이 부분이 DI
public class EmpServiceImpl implements EmpService {

	private final EmpDao ed;
	
	@Override
	public int totalEmp() {

		System.out.println("EmpServiceImpl totalEmp() Start!");
		
		int totEmpCnt = ed.totalEmp();
		System.out.println("EmpServiceImpl totalEmp() totEmpCnt : " + totEmpCnt);
		
		return totEmpCnt;
	}

	@Override
	public List<Emp> listEmp(Emp emp) {

		List<Emp> empList = null;
		System.out.println("EmpServiceImpl listEmp() Start!");
		
		empList = ed.listEmp(emp);
		System.out.println("EmpServiceImpl listEmp() empList.size() : " + empList.size());
		
		return empList;
	}

	@Override
	public Emp detailEmp(int empno) {

		Emp emp =null;
		System.out.println("EmpServiceImpl detailEmp() Start!");
		
		emp = ed.detailEmp(empno);
		System.out.println("EmpServiceImpl detailEmp() empno = " + empno);
		
		return emp;
	}

	@Override
	public int updateEmp(Emp emp) {

		int updateCount = ed.updateEmp(emp);
		
//      1. EmpService안에 updateEmp method 선언
//      1) parameter : Emp
//      2) Return      updateCount (int)
//
//   2. EmpDao updateEmp method 선언
////                              mapper ID   ,    Parameter
//		updateCount = session.update("siaEmpUpdate",emp);
		
		return updateCount;
	}

}
