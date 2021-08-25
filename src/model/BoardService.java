package model;

import java.util.ArrayList;
import java.util.HashMap;

import dao.BoardBean;
import dao.BoardDAO;

public class BoardService {
	private BoardDAO dao;
	
	public BoardService() {
		dao = new BoardDAO();
	}

	public boolean BoardUpdate(BoardBean vo) {
		return dao.updateBoard(vo);		
	}

	public boolean boardInsert(BoardBean vo) {		
		return dao.boardInsert(vo);
	}

	public int getSeq() {
		return dao.getSeq();
	}

	public boolean deleteBoard(int boardNum) {
		return dao.deleteBoard(boardNum);
	}

	public String getFileName(int boardNum) {
		return dao.getFileName(boardNum);
	}

	public BoardBean getDetail(int boardNum) {
		return dao.getDetail(boardNum);
	}

	public boolean updateCount(int boardNum) {		
		return dao.updateCount(boardNum);
	}
	
	public int getBoardListCount(HashMap<String, Object> listOpt) {
		return dao.getBoardListCount(listOpt);
	}
	
	public ArrayList<BoardBean> getBoardList(HashMap<String, Object> listOpt) {
		return dao.getBoardList(listOpt);
	}	
}
