package com.group.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.board.dao.BoardCommentDAO;
import com.group.board.dto.BoardComment;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.RemoveException;
@Service
public class BoardCommentService {
	@Autowired
	private BoardCommentDAO dao;
	@Autowired
	private BoardCommentService service;

	/**
	 * 특정 게시글에 대한 댓글목록을 조회한다
	 * @param bd_no 특정 게시글 번호
	 * @return 댓글 목록
	 * @throws FindException
	 */
	public List<BoardComment> showCm(String bdNo) throws FindException {
		return dao.selectAll(bdNo);
	}

	/**
	 * 특정 게시글에 대한 댓글을 작성한다
	 * @param cm 댓글의 내용을 담은 객체
	 * @throws AddException
	 */
	public void addCm(BoardComment cm) throws AddException {
		boolean flag = false;
		if (cm.getCmWriter().getEmployeeId() != null && !"".equals(cm.getCmWriter().getEmployeeId())) {
			if (cm.getCmContent() != null && !"".equals(cm.getCmContent())) {
				flag = true;
			} else {
				System.out.println("입력한 내용이 없습니다");
			}
		} else {
			System.out.println("로그인 되지 않았습니다");
		}
		if (flag) {
			dao.insert(cm);
		}
	}

	/**
	 * 댓글을 삭제한다
	 * @param cm 삭제할 댓글을 담고 있는 객체
	 * @throws RemoveException
	 * 로그인한 사용자가 댓글을 작성한 작성자인지 비교하는 조건 추가함
	 */
	public void removeCm(BoardComment cm) throws RemoveException {
		List<BoardComment> cmList;
		try {
			cmList = service.showCm(cm.getBdNo());
			System.out.println(cmList.size());
			BoardComment compare = cmList.get(cmList.size()-cm.getCmNo());
			if(compare.getCmWriter().getEmployeeId().equals(cm.getCmWriter().getEmployeeId())) {
				dao.delete(cm);
			}else if(cm.getCmWriter().getEmployeeId().equals("admin")){
				dao.deleteAdmin(cm);
			}else{
				System.out.println("댓글을 작성한 작성자가 아닙니다");
			}
		} catch (FindException e) {
			e.printStackTrace();
		}
	}

}