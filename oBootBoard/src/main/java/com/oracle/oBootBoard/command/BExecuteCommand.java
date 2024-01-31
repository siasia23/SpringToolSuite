package com.oracle.oBootBoard.command;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.oracle.oBootBoard.dao.BDao;
import com.oracle.oBootBoard.dto.BDto;

import jakarta.servlet.http.HttpServletRequest;

@Service		// boot스러운 방식
public class BExecuteCommand {

	private final BDao jdbcDao;
	
	public BExecuteCommand(BDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	// 게시판 목록
	public void bListCmd(Model model) {
		
		// DAO 연결. boot스럽게

		ArrayList<BDto> boardDtoList = jdbcDao.boardList();
		System.out.println("BExecuteCommand boardDtoList.size() : " + boardDtoList.size());
		
		model.addAttribute("boardList", boardDtoList);
		
	}

	// write_view
	public void bWriteCmd(Model model) {

//		1) model이용 , map 선언
		// asMap() : model이를 Map으로 만들자
		Map<String, Object> map = model.asMap();
		
//		2) request 이용 ->  bName  ,bTitle  , bContent  추출
		// Map 형태로 되어 있는 model이를 진짜 Map으로 꺼내자
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
//		3) dao  instance 선언
		// 전역에서 이미 해둬서 Model이가 불러와줌
		
//		4) write method 이용하여 저장(bName, bTitle, bContent)
		jdbcDao.write(bName, bTitle, bContent);
		
	}

	// HW2
	// content_view
	// 게시된 글 하나 눌렀을때 정보 뜨게
	public void bContentCmd(Model model) {

		// 1. model이를 Map으로 전환
		Map<String, Object> map = model.asMap();
		
		// 2. request -> bId Get
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		int bId = Integer.parseInt(request.getParameter("bId"));
		
		// 3. HW3
		BDto board = jdbcDao.contentView(bId);
		
		model.addAttribute("mvc_board", board);
		
	}
	
}
