package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.oBootMybatis01.model.Member1;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Member1DaoImpl implements Member1Dao {

	// transaction 관리
	private final PlatformTransactionManager transactionManager;
	
	//MyBatis DB 연동
	private final SqlSession session;
	
	@Override
	public int memCount(String id) {

		int result = 0;
		System.out.println("Member1DaoImpl id : " + id);
		
		try {
			
			result = session.selectOne("memCount", id);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	@Override
	public List<Member1> listMem(Member1 member1) {

		System.out.println("Member1DaoImpl listMem Start!");
	    
		List<Member1> listMember1 = null;
	    
		try {
			
	         listMember1 = session.selectList("listMember1", member1);
	         
	    } catch(Exception e) {
	         System.out.println("Member1DaoImpl listMem() 에러 : " + e.getMessage());
	    }
		
	    return listMember1;
	}

	@Override
	public int transactionInsertUpdate() {

		System.out.println("Member1DaoImpl transactionInsertUpdate() Start!");
		
		int result = 0;
		
		Member1 member1 = new Member1();
		Member1 member2 = new Member1();
		
		try {
			
			// SqlSession 은 실행할때마다 auto commit
			member1.setId("1005");
			member1.setPassword("2345");
			member1.setName("테스트");
			
			result = session.insert("insertMember1", member1);
			System.out.println("member1 result : " + result);
			
			// 동일한 PK로 실패 유도해보자
			// 데이터 무결성 깨져서 실패했으니 auto rollback
			member2.setId("1005");
			member1.setPassword("3457");
			member1.setName("실패테스트");
			
			result = session.insert("insertMember1", member2);
			System.out.println("member2 result : " + result);
			
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Member1DaoImpl transactionInsertUpdate() 에러 : " + e.getMessage());
			
			result = -1;
			
		}
		
		return result;
	}

	@Override
	public int transactionInsertUpdate3() {
		
		System.out.println("Member1DaoImpl transactionInsertUpdate3() Start!");
		
		int result = 0;
		
		Member1 member1 = new Member1();
		Member1 member2 = new Member1();
		
		// transaction 설정
		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			
			member1.setId("1007");
			member1.setPassword("2345");
			member1.setName("트랜테스트");
			
			result = session.insert("insertMember1", member1);
			System.out.println("member1 result : " + result);
			
			// 동일한 PK로 실패 유도해보자
			member2.setId("1007");
			member2.setPassword("3457");
			member2.setName("국수");
			
			result = session.insert("insertMember1", member2);
			System.out.println("member2 result : " + result);
			
			// 정상이면 한꺼번에 commit
			transactionManager.commit(txStatus);
			
		} catch (Exception e) {

			// 에러나면 한꺼번에 rollback
			transactionManager.rollback(txStatus);
			
			e.printStackTrace();
			System.out.println("Member1DaoImpl transactionInsertUpdate() 에러 : " + e.getMessage());
			
			result = -1;
			
		}
		
		return result;
	}

}
