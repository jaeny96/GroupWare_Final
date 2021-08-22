package com.group.employee.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/employee/*")
public class EmployeeController {
	private Logger log = Logger.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeService service;
	
//	@Autowired
//	private DepartmentService depService;
//	
	/**
	 * 사원 전체 조회 
	 * @return
	 */
	@GetMapping("/all")
	public Object allEmployee(){
	Map<String, Object> result = new HashMap<>();	
	
	try {
		List<Employee> emplist = service.showAll();
		if(emplist.size()==0) {
			result.put("status", 0);
			return result;
		}else {
			return emplist;
		}
	
	} catch (FindException e) {
		result.put("status", -1);
		e.printStackTrace();
		return result;
	}
	}
	
	
	
	/**
	 * 부서별 사원 목록을 조회한다
	 * @param dept 특정 부서번호
	 * @return 부서별 사원 목록
	 * @throws FindException
	 */
	@GetMapping("/byDept/{dept}")
	@ResponseBody
	public Object showEmpByDept(@PathVariable String dept) {
		Map<String, Object> result = new HashMap<>();
		
		try {
			List<Employee> emplist = service.showByDept(dept);
			//만약 검색 결과가 없다면 status를 0으로 
			if(emplist.size()==0) {
				result.put("status", 0);
				return result;
			}else {
			//검색 결과가 존재한다면, 리스트를 반환
				return emplist;
			}
		} catch (FindException e) {
			//에러가 날 경우 -1 
			result.put("status", -1);
			e.printStackTrace();
			return result;
		}
	}

	/**
	 * 사원을 검색한다
	 * @param word 검색 단어
	 * @return 특정 검색 단어에 해당하는 사원 목록
	 * @throws FindException
	 */
	
	@PostMapping("/search/{word}")
	@ResponseBody
	public Object searchEmpByWord(@PathVariable String word) {
		Map<String, Object> result = new HashMap<>();
		
		try {
			List<Employee> emplist = service.searchEmp(word);
			if(emplist.size()==0) {
				result.put("status", 0);
				return result;
			}else {
				return emplist;
			}
		} catch (FindException e) {
			result.put("status", -1);
			e.printStackTrace();
			return result;
		}
	}

	
	/**
	 * 특정 사원의 상세 정보를 조회한다
	 * @param emp
	 * @return
	 */
	@GetMapping("/{id}")
	@ResponseBody
	public Object checkEmpDetail(@PathVariable String id) {
		Map<String, Object> result = new HashMap<>();
		
		Employee emp = new Employee();
		emp.setEmployeeId(id);

		try {
			Employee empDetail = service.showDetail(id);
			return empDetail;
		} catch (FindException e) {
			result.put("status", -1);
			e.printStackTrace();
			return result;
		}

	}

}
