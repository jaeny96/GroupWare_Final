package com.admin.empmanage.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.admin.empmanage.service.EmployeeManageService;
import com.group.employee.control.EmployeeController;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.employee.dto.Position;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.UpdateException;

@RestController
@RequestMapping("/admin/employee/*")
public class EmployeeManageController {	

	@Autowired
	private EmployeeManageService service;
	
	private Logger log = Logger.getLogger(EmployeeController.class);

	
	/**
	 * 직원 추가
	 * @param emp
	 * @return
	 */
	@PostMapping("/add")
	public Map<String, Object> addEmployee(@RequestBody Employee emp) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			service.addEmp(emp);
			map.put("status", 0);
			log.error("직원 추가 완료(Controller)"+emp);
			return map;
		} catch (AddException e) {
			e.printStackTrace();
			map.put("status", -1);
			return map;
		}
		
	}
	
	/**
	 * 직원 수정 
	 * @param emp
	 * @return
	 */
	@PutMapping("/update")
	public Map<String, Object> updateEmployee(@RequestBody Employee emp){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Employee employee = new Employee();
		Department dep = new Department();
		dep.setDepartmentId(emp.getDepartment().getDepartmentId());
		Job job = new Job();
		job.setJobId(emp.getJob().getJobId());
		Position p = new Position();
		p.setPositionId(emp.getPosition().getPositionId());
		
		log.error(dep);
		log.error(job);
		employee.setEmployeeId(emp.getEmployeeId());
		employee.setName(emp.getName());
		employee.setDepartment(dep);
		employee.setJob(job);
		employee.setPosition(p);
		
		employee.setPhoneNumber(emp.getPhoneNumber());
		employee.setEmail(emp.getEmail());
		employee.setHireDate(emp.getHireDate());
		//System.out.println(new Timestamp(0));
		employee.setenabled(emp.getenabled());
		log.error("emp"+emp);
		
		try {
			service.modifyEmp(employee);
			log.info("직원 수정 완료(Controller)");
			map.put("status", 0);
			return map;
		} catch (UpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", -1);
			return map;
		}
	}
	
	
	@GetMapping("/position")
	public List<Position> showPosition() throws FindException{
	
		return service.showPosition();
	}
	
	@GetMapping("/job")
	public List<String> showJob() throws FindException{
		
		return service.showJob();
	}
	
	@GetMapping("/department")
	public List<Department> showDepartment() throws FindException{
		
		return service.showDepartment();
	}
}
