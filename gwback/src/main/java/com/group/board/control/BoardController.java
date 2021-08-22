package com.group.board.control;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.board.dto.Board;
import com.group.board.service.BoardService;
import com.group.board.service.PageBeanService;
import com.group.employee.dto.Employee;

@RestController
@RequestMapping("/board/*")
public class BoardController {
	private Logger log = Logger.getLogger(BoardController.class.getClass());

	@Autowired
	private BoardService service;
	@Autowired
	private PageBeanService beanservice;
	/**
	 * 현재페이지에 해당하는 게시글 불러옴
	 * @param currentPage
	 * @return
	 */
	@GetMapping("/bdpage")
	public Object getBdpage(/* HttpSession session */) {
		Map<String, Object> map = new HashMap<>();
		int currentPage = 1;
		try {
			List<Board> bdList = service.showBdAll(currentPage);
			return bdList;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	/**
	 * 게시글 검색
	 * @param category, word
	 * @return 카테고리와 단어의 키워드 해당하는 게시글 목록
	 */
	//이친구는..테스트를 어떻게 해줘야하지..?
	@GetMapping("/searchboard/{category}/{word}")
	public Object getSearchboard(String category, String word) {
		Map<String, Object> map = new HashMap<>();
		
		try {
			List<Board> bdList = service.searchBd(category, word);
			if (bdList.size() == 0) {
				map.put("status", 0);
				return map;
			} else {
				return bdList;
			}
		} catch (Exception e) {
			// 에러가 날 경우 -1
			map.put("status", -1);
			e.printStackTrace();
			return map;
		}
	}

	/**
	 * 페이지 그룹당 보여주는 페이지수
	 * @param int PageGroup
	 * @return list
	 */
	@GetMapping("/pagegroup")
	public Object getPagegroup() {
		Map<String, Object> map = new HashMap<>();
		int PageGroup = 1;
		try {
			List<Integer> list = beanservice.selectPageGroup(PageGroup);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	/**
	 * 총 페이지수 보여줌
	 * @return totalPage
	 */
	@GetMapping("/totalpage")
	public Object getTotalpage() {
		Map<String, Object> map = new HashMap<>();
		try {
			int totalPage = beanservice.selectTotalPage();
			return totalPage;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	/**
	 * 게시글 등록
	 * @param board bd
	 * @return
	 */
	@PostMapping("/addboard")
	public Object getAddboard(@RequestBody Board bd) {
		Map<String, Object> map = new HashMap<>();
		String id = "DEV004";
		Employee emp = new Employee();
		emp.setEmployeeId(id);
		bd.setWriter(emp);
		try {
			service.addBd(bd);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	/**
	 * 파일업로드
	 * 진행중...
	 */
//	@PostMapping("/fileuploadinbd")
//	public Object getFileuploadinbd() {
//		
//	}
	/**
	 * 게시글 수정
	 * @param 
	 * @return
	 */
	@PutMapping("/modifybd/{bdNo}")
	public Object getModifybd(@RequestBody Board bd) {
		Map<String, Object> map = new HashMap<>();
		String id = "DEV005";
		String bdNo = "BD3";
		Employee emp = new Employee();
		emp.setEmployeeId(id);
		bd.setWriter(emp);
		bd.setBdNo(bdNo);
		try {
			service.modifyBd(bd);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	// 게시글 상세조회
	@GetMapping("/{bdNo}")
	public Object getBddetail(@PathVariable String bdNo) {
		Map<String, Object> result = new HashMap<>();
		Board bd = new Board();
		bd.setBdNo(bdNo);

		try {
			Board bdDetail = service.showBdDetail(bdNo);
			return bdDetail;
		} catch (Exception e) {
			result.put("status", -1);
			e.printStackTrace();
			return result;
		}
	}

	// 게시글삭제
	@DeleteMapping("/removebd/{bdNo}")
	public Map<String, Object> getRemovebd(@PathVariable String bdNo) {
		Map<String, Object> map = new HashMap<>();
		String id = "SEC002";
		Board bd = new Board();
		Employee emp = new Employee();
		emp.setEmployeeId(id);
		bd.setWriter(emp);
		bd.setBdNo(bdNo);
		try {
			service.removeBd(bd);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}
}
