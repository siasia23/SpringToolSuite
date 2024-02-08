package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Emp;

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
		
		updateCount = session.update("siaEmpUpdate",emp);
		
		return updateCount;
	}


}
