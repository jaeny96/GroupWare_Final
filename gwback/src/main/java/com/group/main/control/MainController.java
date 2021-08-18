package com.group.main.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.employee.dto.Employee;
import com.group.exception.FindException;
import com.group.main.service.MainService;

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
			System.out.println(emp);
			return emp;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status",-1);
			map.put("msg",e.getMessage());
		}
		return map;
	}
}
