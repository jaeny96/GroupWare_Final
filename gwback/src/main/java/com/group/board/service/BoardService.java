package com.group.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.board.dao.BoardDAO;
import com.group.board.dto.Board;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;

@Service
public class BoardService {
	@Autowired
	private BoardDAO dao;
	
	/**
	 * 현재 페이지의 게시글 목록을 조회한다
	 * 
	 * @param currentPage 현재 페이지
	 * @return 게시글 목록
	 * @throws FindException
	 */
	public List<Board> showBdAll(int currentPage) throws FindException {
		return dao.selectAll(currentPage);
	}

	/**
	 * 게시글(제목, 작성자)을 검색한다
	 * 
	 * @param category 제목으로 검색할지 작성자로 검색할지 결정한 카테고리
	 * @param word     검색할 단어
	 * @return 단어에 대한 게시글 목록
	 * @throws FindException
	 */
	public List<Board> searchBd(String category, String word) throws FindException {
		return dao.selectByWord(category, word);
	}

	/**
	 * 특정 게시글 상세정보를 조회한다
	 * 
	 * @param bd_no 특정 게시글 번호
	 * @return 게시글의 상세 정보
	 * @throws FindException
	 */
	public Board showBdDetail(String bdNo) throws FindException {
		return dao.selectBdInfo(bdNo);
	}

	/**
	 * 게시글을 등록한다
	 * 
	 * @param bd 등록할 내용 담은 객체
	 * @throws AddException
	 */
	public void addBd(Board bd) throws AddException {
		if (bd.getBdTitle() != null) {
			dao.insert(bd);
		} else {
			System.out.println("제목이 입력되지 않았습니다");
		}
	}

	/**
	 * 게시글을 수정한다
	 * @param bd 변경할 내용 담고 있는 객체
	 * @throws ModifyException 로그인한 사용자가 글을 작성한 사원인지 비교하는 조건 필요 - 어디서? 서비스에서 할지 후에
	 *                         할지 고민중
	 */
	public void modifyBd(Board bd) throws ModifyException {
		if(bd.getWriter().getEmployeeId().equals("admin")&&!"".equals(bd.getBdTitle()) && bd.getBdTitle() != null) {
			dao.updateAdmin(bd);
		}
		else if (!"".equals(bd.getBdTitle()) && bd.getBdTitle() != null) {
			dao.update(bd);
		} else {
			System.out.println("게시글바꼈죵");
		}
	}

	/**
	 * 게시글을 삭제한다
	 * @param bd 삭제할 게시글 정보 담은 객체
	 * @throws RemoveException 로그인한 사용자가 글을 작성한 사원인지 비교하는 조건 필요 - 어디서? 서비스에서 할지 후에
	 *                         할지 고민중
	 */
	public void removeBd(Board bd) throws RemoveException {
		System.out.println(bd.getWriter().getEmployeeId());
		if(bd.getWriter().getEmployeeId().equals("admin")) {
			dao.deleteAdmin(bd);
		}else {
		dao.delete(bd);
		}
	}

}