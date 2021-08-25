package model;

import java.util.ArrayList;
import java.util.HashMap;

import dao.ContentsDAO;
import dao.ContentsVO;
import dao.TheChefVO;

public class ContentsService {
	private ContentsDAO dao;
	
	public ContentsService() {
		dao = new ContentsDAO();
	}
	
	public ArrayList<ContentsVO> getContentsList(HashMap<String, Object> listOpt){
		return dao.getContentsList(listOpt);		
	}
	
	public boolean checkFavoriteData(ContentsVO cvo, TheChefVO vo) {
		return dao.selectFavoriteCount(cvo, vo) > 0;		
	}
	
	public void insertOrDeleteFovorite(ContentsVO cvo, TheChefVO vo) {
		if(checkFavoriteData(cvo, vo)) { // ���� ���� ������ 0���� ŭ : �� ����
			dao.deleteFavorite(cvo, vo);
		} else {                         // ���� ���� ������ 0 : �� ���
			dao.insertFavorite(cvo, vo);
		}
	}
	
	public ArrayList<ContentsVO> getFavoriteList(String memId){
		return dao.getFavoriteList(memId);		
	}
	
	public ArrayList<ContentsVO> getFavoriteListInfo(String memId){
		return dao.getFavoriteListInfo(dao.getFavoriteList(memId));	
	}
}
