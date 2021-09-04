package com.admin.notice.control;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.group.board.dto.Board;
import com.group.board.service.BoardService;
import com.group.board.service.PageBeanService;
import com.group.employee.dto.Employee;

@RestController
@RequestMapping("/admin/*")
public class AdminBoardController {
	private Logger log = Logger.getLogger(AdminBoardController.class.getClass());
	
	@Autowired
	private BoardService service;
	@Autowired
	private PageBeanService beanservice;
	@Autowired
	private ServletContext servletContext;

	/**
	 * 현재페이지에 해당하는 게시글 불러옴
	 * @param currentPage
	 * @return
	 */
	@GetMapping(value="/bdpage/{currnetpage}")
	public Object getBdpage(@PathVariable int currentPage) {
		Map<String, Object> map = new HashMap<>();
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
	@GetMapping("/searchboard/{category}/{word}")
	public Object getSearchboard(@PathVariable String category, @PathVariable String word) {
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
	 * 페이지 그룹당 5개씩 보여줌
	 * @param int PageGroup
	 * @return list
	 */
	@GetMapping(value = "/pagegroup/{pagegroupno}")
	public Object getPagegroup(@PathVariable int pagegroupno) {
		Map<String, Object> map = new HashMap<>();
		//int PageGroup = 1;
		try {
			List<Integer> list = beanservice.selectPageGroup(pagegroupno);
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
	public Object getAddboard(HttpSession session, @RequestBody Board bd) {
		Map<String, Object> map = new HashMap<>();
		String id = (String) session.getAttribute("id");
//		String id = "admin";
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
	
	@PutMapping("/modifybd/{bdNo}")
	public Object getModifybd(HttpSession session, @PathVariable String bdNo,@RequestBody Board bd) {
		Map<String, Object> map = new HashMap<>();
		String id = (String) session.getAttribute("id");
//		String id = "MSD002";
		Employee emp = new Employee();
		emp.setEmployeeId(id);
		bd.setWriter(emp);
		bd.setBdNo(bdNo);
		System.out.println(bdNo);
		try {
			service.modifyBd(bd);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}
	
	/**
	 * 게시글상세조회
	 * @param bdNo
	 * @return
	 */
	@GetMapping("/{bdNo}")
	public Map<String, Object> getBddetail(@PathVariable String bdNo) {
		Map<String, Object> result = new HashMap<>();
		String uploadPath = servletContext.getRealPath("upload");
		File f = new File(uploadPath);
		
		Board bd = new Board();
		bd.setBdNo(bdNo);
		try {
			Board bdDetail = service.showBdDetail(bdNo);
			//String thisBoardNo = bdDetail.getBdNo();
			String thisBoardId = bdDetail.getWriter().getEmployeeId();
			if(f.isDirectory()) {
				File[] fList = f.listFiles();
				for(File ff: fList) {
					if(ff.getName().contains(thisBoardId)) {
						String thisFileName = ff.getName();
						System.out.println(thisFileName);
						result.put("fileName", thisFileName);
					}
				}
			}
			result.put("board", bdDetail);
		} catch (Exception e) {
			result.put("status", -1);
			e.printStackTrace();
			
		}
		return result;
	}
	
	// 게시글삭제
		@DeleteMapping("/removebd/{bdNo}")
		public Map<String, Object> getRemovebd(HttpSession session,@PathVariable String bdNo) {
			Map<String, Object> map = new HashMap<>();
			String id = (String) session.getAttribute("id");
//			String id = "MSD002";
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
		
		/**
		 * 파일업로드
		 * 
		 */
		@PostMapping("/fileuploadinbd")
		public Map<String, Object> getFileUploadinbd(@RequestPart MultipartFile fileUploadBoard, HttpSession session) {
			Map<String, Object> result = new HashMap<>();
		
			
			String uploadPath = servletContext.getRealPath("upload");
			System.out.println("업로드 실제경로"+uploadPath);
			//경로가 없으면 경로 생성
			if(!new File(uploadPath).exists()) {
				log.info("업로드실제경로생성"+uploadPath);
				new File(uploadPath).mkdirs();
			}
		
					if(fileUploadBoard != null) {
						String UploadfileName = fileUploadBoard.getOriginalFilename();
						System.out.println("파일크기:"+fileUploadBoard.getSize()+", 파일이름:"+fileUploadBoard.getOriginalFilename());
					
						String fileName = session.getAttribute("id").toString()+"_"+ UploadfileName;
						System.out.println(fileName);
						File file = new File(uploadPath, fileName);
						try {
							FileCopyUtils.copy(fileUploadBoard.getBytes(), file);
							result.put("status", 1);
							result.put("msg","파일업로드까지 성공!");
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
					return result;
			}
	
	
}
