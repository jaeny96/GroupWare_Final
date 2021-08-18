package com.group.main.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.approval.dto.Document;
import com.group.board.dto.Board;
import com.group.calendar.dto.Schedule;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Leave;
import com.group.exception.FindException;
import com.group.main.service.MainService;
import com.group.mypage.dto.EmployeeLeave;

@RestController
public class MainController {
	private Logger log = Logger.getLogger(MainController.class.getClass());
	
	@Autowired
	private MainService service;
	
	@GetMapping("/main")
	public Object getProfile(/*HttpSession session*/) {
		Map<String, Object> map = new HashMap<>();
//		String id = session.getAttribute("id").toString();
		String id = "MSD002";
		try {
			Employee emp = service.showProfile(id);
			return emp;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status",-1);
			map.put("msg",e.getMessage());
		}
		return map;
	}
	
	@GetMapping("/main/leave")
	public Object getLeave(/*HttpSession session*/) {
		Map<String, Object> map = new HashMap<>();
//		String id = session.getAttribute("id").toString();
		String id = "MSD002";
		try {
			Leave leave = service.showLeave(id);
			return leave;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status",-1);
			map.put("msg",e.getMessage());
		}
		return map;
	}

	@GetMapping("/main/document")
	public Object getDocExpected(/*HttpSession session*/) {
		Map<String, Object> map = new HashMap<>();
//		String id = session.getAttribute("id").toString();
		String id = "MSD002";
		try {
			List<Document> docList = service.showDocExpected(id);
			return docList;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status",-1);
			map.put("msg",e.getMessage());
		}
		return map;
	}

	@GetMapping("/main/board")
	public Object getLatestBoardList() {
		Map<String, Object> map = new HashMap<>();
		try {
			List<Board> bdList = service.showRecentBd();
			return bdList;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status",-1);
			map.put("msg",e.getMessage());
		}
		return map;
	}
	
	@GetMapping("/main/todaySkd")
	public Object getTodaySkdList(/*HttpServletRequest request*/) {
		Employee emp = new Employee();
		Department dept = new Department();
		dept.setDepartmentId("MSD");
		emp.setEmployeeId("MSD002");
		emp.setDepartment(dept);
		
		Map<String, Object> map = new HashMap<>();
		try {
			List<Schedule> skdList = service.showTodaySkd(emp);
			return skdList;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status",-1);
			map.put("msg",e.getMessage());
		}
		return map;
	}
}
