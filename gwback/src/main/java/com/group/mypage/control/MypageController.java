package com.group.mypage.control;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
//	@GetMapping("/detail")
//	public Map<String, Object> showEmpDetail(String id){
//		Map<String, Object> map = new HashMap<>();
//	try {
//		EmployeeLeave empLeave = service.showDetail(id);
//		map.put("status", 1);
//		return map;
//	} catch (FindException e) {
//		map.put("status", -1);
//		e.printStackTrace();
//		return map;
//	}	
//	
//	
//}

	//핸드폰번호, 비밀번호 변경
	@PutMapping("/update")
	public Map<String, Object> modifyEmp(Employee emp){
		Map<String, Object> map = new HashMap<>();
		try {
			service.modify(emp);
			map.put("status", 1);
			return map;
		} catch (ModifyException e) {
			map.put("status", -1);
			e.printStackTrace();
			return map;
		}
	}
	
}
