package com.oracle.oBootBoard.command;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.oracle.oBootBoard.dao.BDao;
import com.oracle.oBootBoard.dao.JdbcDao;
import com.oracle.oBootBoard.dto.BDto;

// Service. DI 방식(=Annotation) 말고 고전적인 방법(=상속)으로
public class BListCommand implements BCommand {

	DataSource dataSource;
	
	@Override
	public void execute(Model model) {	// Model 쓰면 request.getParameter() 쓸 필요 없음

		// DAO 연결. DI 방식 말고 고전적인 방법으로
		BDao dao = new JdbcDao(dataSource);
		
		ArrayList<BDto> boardDtoList = dao.boardList();
		System.out.println("BListCommand boardDtoList.size() : " + boardDtoList.size());
		
		model.addAttribute("boardList", boardDtoList);
		
	}

}
