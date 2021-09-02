package com.admin.notice.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.board.dto.Board;
import com.group.board.dto.BoardComment;
import com.group.board.service.BoardCommentService;
import com.group.employee.dto.Employee;

@RestController
@RequestMapping("/admin/boardcomment*")
public class AdminBoardCommentController {
	private Logger log = Logger.getLogger(AdminBoardCommentController.class.getClass());

	@Autowired
	private BoardCommentService service;

	// 게시글 내 댓글 조회
	@GetMapping("/{bdNo}")
	public Object getShowbdcm(@PathVariable String bdNo) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<BoardComment> bdList = service.showCm(bdNo);
			if (bdList.size() == 0) {
				map.put("status", 0);
				return map;
			} else {
				return bdList;
			}
		} catch (Exception e) {
			map.put("status", -1);
			e.printStackTrace();
			return map;
		}
	}

	// 댓글 등록
	@PostMapping("/addbdcm/{bdNo}")
	public Map<String, Object> addBoardcm(HttpSession session,@PathVariable String bdNo, @RequestBody BoardComment cm) {
		Map<String, Object> map = new HashMap<>();
		String id = (String) session.getAttribute("id");
		try {
			cm.setBdNo(bdNo);
			Employee emp = new Employee();
			emp.setEmployeeId(id);
			cm.setCmWriter(emp);
			service.addCm(cm);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}
	// 댓글 삭제
	@DeleteMapping("/removecm/{bdNo}/{cmNo}")
	public Map<String, Object> getRemovebd(HttpSession session, @PathVariable String bdNo, @PathVariable int cmNo) {
		Map<String, Object> map = new HashMap<>();
		String id = (String) session.getAttribute("id");
//		String id = "MSD002";
		Board bd = new Board();
		bd.setBdNo(bdNo);
		Employee emp = new Employee();
		BoardComment cm = new BoardComment();
		emp.setEmployeeId(id);
		cm.setCmWriter(emp);
		cm.setCmNo(cmNo);
		cm.setBdNo(bdNo);	//boardcomment.dto에도 bdNo가 있기 때문에 따로 set을 해줘야 함 
		try {
			service.removeCm(cm);;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}
}
