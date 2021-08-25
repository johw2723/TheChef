package model;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import dao.TheChefDAO;
import dao.TheChefVO;

public class TheChefService {
	private TheChefDAO tcdao;
	
	public TheChefService() {
		tcdao = new TheChefDAO();
	}
	
	public void insertMember(TheChefVO vo) {
		tcdao.insertMember(vo);
	}

	public int loginCheck(String memId, String pw) {
		return tcdao.loginCheck(memId, pw);
	}

	public List<TheChefVO> MemberList() {
		return tcdao.MemberList();
	}

	public TheChefVO MemberInfo(String memId) {
		return tcdao.MemberInfo(memId);
	}

	public void updateMember(TheChefVO vo) {
		tcdao.updateMember(vo);
	}

	public void deleteMember(String memId) {
		tcdao.deleteMember(memId);
	}

	public boolean idCheck(String memId) {
		return tcdao.duplicateIdCheck(memId);
	}
}
