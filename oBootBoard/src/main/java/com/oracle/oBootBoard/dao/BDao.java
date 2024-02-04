package com.oracle.oBootBoard.dao;

import java.util.ArrayList;

import com.oracle.oBootBoard.dto.BDto;

// DAO interface

// DB (oracle, mySQL)를 바꿀때 수정 유연하게 하기 위해 DAO는 interface로 씀
// interface를 통해 표준화, 모델화, 모듈화, 부품화
public interface BDao {

	public ArrayList<BDto> boardList();

	public void write(String bName, String bTitle, String bContent);

	public BDto contentView(int bId);

	public void modify(int bId, String bName, String bTitle, String bContent);

	public BDto reply_view(int bId);

	public void reply(int bId, String bName, String bTitle, String bContent, int bGroup, int bStep, int bIndent);

	public void delete(int bId);
	
}
