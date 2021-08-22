package com.group.mypage.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group.employee.dto.Employee;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.mypage.dto.EmployeeLeave;
import com.group.mypage.service.EmployeeLeaveService;

@RestController
@RequestMapping("/mypage/*")
public class MypageController {

	@Autowired
	private EmployeeLeaveService service;
	
	//자신의 모든 정보 조회
	@GetMapping("/detail")
	public Object showEmpDetail(HttpSession session){
		Map<String, Object> map = new HashMap<>();
		String id = (String) session.getAttribute("id");		
	try {
		EmployeeLeave empLeave = service.showDetail(id);
		map.put("responseData", empLeave);
		return map;
	} catch (FindException e) {
		map.put("status", -1);
		e.printStackTrace();
		return map;
	}	
	
	
}

	//핸드폰번호 변경
	@PutMapping("/updatePhone")
	public Map<String, Object> modifyEmp(HttpSession session, 
			@RequestBody Employee emp){
		String id = (String) session.getAttribute("id");
		Employee empToData = new Employee();
		Map<String, Object> map = new HashMap<>();
		try {

			String modiPhone = emp.getPhoneNumber();
			empToData.setEmployeeId(id);
			empToData.setPhoneNumber(modiPhone);
			System.out.println(modiPhone);
		
			service.modify(empToData);
			map.put("status", 1);
			return map;
		} catch (ModifyException e) {
			map.put("status", -1);
			e.printStackTrace();
			return map;
		}
	}
	
	//핸드폰번호, 비밀번호 변경
	@PutMapping("/updatePwd")
	public Map<String, Object> modifyPwd(HttpSession session, 
			@RequestBody Employee emp){
		String id = (String) session.getAttribute("id");
		String modiPwd = emp.getPassword();
		System.out.println(modiPwd);
		Map<String, Object> map = new HashMap<>();
		try {
			Employee empToData = new Employee();

			empToData.setEmployeeId(id);
			empToData.setPassword(modiPwd);
			service.modify(empToData);
			map.put("status", 1);
		
			return map;
		} catch (ModifyException e) {
			map.put("status", -1);
			e.printStackTrace();
			return map;
		}
	}
	
}
