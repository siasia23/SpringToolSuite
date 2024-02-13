package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpDaoImpl implements EmpDao {

	// Mybatis DB 연동에 필요한 객체 SqlSession
	private final SqlSession session;
	
	@Override
	public int totalEmp() {

		int totEmpCount = 0;
		System.out.println("EmpDaoImpl Start total...");
		
		try {
			
											// selectOne() : .xml mapper 의 query 호출
			totEmpCount = session.selectOne("com.oracle.oBootMybatis01.EmpMapper.empTotal");
			System.out.println("EmpDaoImpl totalEmp() totEmpCount : " + totEmpCount);
			
		} catch (Exception e) {
			
			System.out.println("EmpDaoImpl totalEmp() Exception : " + e.getMessage());
			
		}
		
		return totEmpCount;
		
	}

	@Override
	public List<Emp> listEmp(Emp emp) {

		List<Emp> empList = null;
		
		empList = session.selectList("siaEmpList", emp);
		
		return empList;
	}

	@Override
	public Emp detailEmp(int empno) {

		Emp emp = null;
		
		emp = session.selectOne("siaDetailEmp", empno);
		System.out.println("emp1 : " + emp);
		
		return emp;
	}

	@Override
	public int updateEmp(Emp emp) {

		int updateCount = 0;
		
	//   2. EmpDao updateEmp method 선언
////    mapper ID   ,    Parameter
//updateCount = session.update("siaEmpUpdate",emp);
		
		updateCount = session.update("siaEmpUpdate", emp);
		
		return updateCount;
	}

	@Override
	public List<Emp> listManager() {

		List<Emp> empList = null;
		
		empList = session.selectList("siaSelectManager");
		
		return empList;
	}

	@Override
	public int insertEmp(Emp emp) {

		int result = 0;
		
		try {
			
			result = session.insert("siaEmpInsert", emp);
			System.out.println("EmpDaoImpl insertEmp() result : " + result);
			
		} catch (Exception e) {
			
			System.out.println("EmpDaoImpl insertEmp() Exception : " + e.getMessage());

		}
		
		return result;
	}

	@Override
	public int deleteEmp(int empno) {

		int result = 0;
		
		try {
			
			result = session.delete("siaEmpDelete", empno);
			System.out.println("EmpDaoImpl deleteEmp() result : " + result);
			
		} catch (Exception e) {
			
			System.out.println("EmpDaoImpl deleteEmp() Exception : " + e.getMessage());

		}
		
		return result;
	}

	@Override
	public List<Emp> empSearchList3(Emp emp) {

		List<Emp> empSearchList3 = null;
		
		System.out.println("EmpDaoImpl empSearchList3() emp : " + emp);
		
		try {
			
			empSearchList3 = session.selectList("siaEmpSearchList3", emp);
			System.out.println("empSearchList3.size() : " + empSearchList3.size());
			
		} catch (Exception e) {
			
			System.out.println("EmpDaoImpl empSearchList3() Exception : " + e.getMessage());

		}
		
		return empSearchList3;
	}

	@Override
	public int condTotalEmp(Emp emp) {

		int totSearchCnt = session.selectOne("siaCondTotalEmp", emp);
		System.out.println("EmpDaoImpl totSearchCnt : " + totSearchCnt);
		
		return totSearchCnt;
	}

	@Override
	public List<EmpDept> listEmpDept() {

		List<EmpDept> listEmpDept = null;
		
		try {
			
			listEmpDept = session.selectList("siaListEmpDept");
			System.out.println("listEmpDept.size() : " + listEmpDept.size());
			
		} catch (Exception e) {
			System.out.println("EmpDaoImpl listEmpDept() Exception : " + e.getMessage());
		}
		
		return listEmpDept;
	}

}
