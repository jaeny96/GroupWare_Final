package com.group.employee.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.employee.service.DepartmentService;
import com.group.employee.service.EmployeeService;
import com.group.exception.FindException;


@RestController
public class DepartmentController {
	private Logger log = Logger.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentService service;
	
	/**
	 * 부서의 목록을 조회한다
	 * @return 조회한 모든 부서들
	 */
	@GetMapping("/employee/dep")
	@ResponseBody
	public Object allDepartment() {
		Map<String, Object> result = new HashMap<>();
		
		try {
			List<Department> dep = service.showDept();
			return dep;
		} catch (FindException e) {
			result.put("status", -1);
			result.put("msg", e.getMessage());
			e.printStackTrace();
			return result;
		}

	}
	
//	{
//		  "method": "GET",
//		  "transformRequest": [
//		    null
//		  ],
//		  "transformResponse": [
//		    null
//		  ],
//		  "jsonpCallbackParam": "callback",
//		  "url": "http://localhost:8888/gwback/employee/dep",
//		  "headers": {
//		    "Accept": "application/json, text/plain, */*"
//		  },
//		  "data": "",
//		  "timeout": {}
//		}
}
