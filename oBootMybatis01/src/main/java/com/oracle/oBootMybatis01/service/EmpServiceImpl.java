package com.oracle.oBootMybatis01.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.DeptDao;
import com.oracle.oBootMybatis01.dao.EmpDao;
import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor	// 이 부분이 DI
public class EmpServiceImpl implements EmpService {

	private final EmpDao ed;
	private final DeptDao dd;
	
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

		int updateCount = 0;
		System.out.println("EmpServiceImpl updateEmp() Start!");
		
		updateCount = ed.updateEmp(emp);
		
//      1. EmpService안에 updateEmp method 선언
//      1) parameter : Emp
//      2) Return      updateCount (int)
//
//   2. EmpDao updateEmp method 선언
////                              mapper ID   ,    Parameter
//		updateCount = session.update("siaEmpUpdate",emp);
		
		return updateCount;
	}

	@Override
	public List<Emp> listManager() {

		List<Emp> empList = null;
		System.out.println("EmpServiceImpl listManager() Start!");
		
		empList = ed.listManager();
		System.out.println("EmpServiceImpl listManager() empList.size() : " + empList.size());
		
		return empList;
	}

	@Override
	public List<Dept> deptSelect() {

		List<Dept> deptList = null;
		System.out.println("EmpServiceImpl deptSelect() Start!");
		
		deptList = dd.deptSelect();
		System.out.println("EmpServiceImpl deptSelect() deptList.size() : " + deptList.size());
		
		return deptList;
	}

	@Override
	public int insertEmp(Emp emp) {

		int result = 0;
		System.out.println("EmpServiceImpl insertEmp() Start!");
		
		result = ed.insertEmp(emp);
		System.out.println("EmpServiceImpl insertEmp() result : " + result);
		
		return result;
	}

	@Override
	public int deleteEmp(int empno) {

		int result = 0;
		System.out.println("EmpServiceImpl deleteEmp() Start!");
		
		result = ed.deleteEmp(empno);
		System.out.println("EmpServiceImpl deleteEmp() result : " + result);
		
		return result;
	}

	@Override
	public List<Emp> listSearchEmp(Emp emp) {

		List<Emp> empSearchList = null;
		System.out.println("EmpServiceImpl listSearchEmp() Start!");
		
		empSearchList = ed.empSearchList3(emp);
		System.out.println("empSearchList.size() : " + empSearchList.size());
		
		return empSearchList;
	}

	@Override
	public int condTotalEmp(Emp emp) {

		System.out.println("EmpServiceImpl condTotalEmp() Start!");
		
		int totSearchCnt = ed.condTotalEmp(emp);
		System.out.println("EmpServiceImpl totSearchCnt : " + totSearchCnt);
		
		return totSearchCnt;
	}

	@Override
	public List<EmpDept> listEmpDept() {

		System.out.println("EmpServiceImpl listEmpDept() Start!");
		
		List<EmpDept> listEmpDept = ed.listEmpDept();
		System.out.println("listEmpDept.size() : " + listEmpDept.size());
		
		return listEmpDept;
	}

	@Override
	public void insertDept(DeptVO deptVO) {

		System.out.println("EmpServiceImpl insertDept() Start!");
		
		dd.insertDept(deptVO);
		
	}

	@Override
	public void selListDept(HashMap<String, Object> map) {

		System.out.println("EmpServiceImpl selListDept() Start!");
		
		dd.selListDept(map);
	
	}

}
