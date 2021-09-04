package com.group.calendar.control;

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

import com.group.calendar.dto.Schedule;
import com.group.calendar.service.ScheduleService;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.DuplicatedException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;

@RestController
@RequestMapping("/schedule/*")
public class ScheduleController {
	private Logger log = Logger.getLogger(ScheduleController.class);

	@Autowired
	private ScheduleService service;
	
	@GetMapping("/allSkd")
	public Object allSkd(HttpSession session){
	String id = session.getAttribute("id").toString();
	String deptId = session.getAttribute("dept").toString();
		Employee emp = new Employee();
		Department dept = new Department();
		//String id = "MSD003";
		//dept.setDepartmentId("MSD");
		dept.setDepartmentId(deptId);
		emp.setEmployeeId(id);
		emp.setDepartment(dept);
		Map<String,Object>map = new HashMap<>();
		try {
			List<Schedule> list = service.findSkdAll(emp);
			return list;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}
	@GetMapping("/skdDate/{sdate}/{edate}")
	public Object skdDate(@PathVariable String sdate, @PathVariable String edate ,HttpSession session) {
		String id = session.getAttribute("id").toString();
		//String id = "MSD003";
		Department dept = new Department();
//		dept.setDepartmentId("SEC");
		String deptId = session.getAttribute("dept").toString();
		dept.setDepartmentId(deptId);
        Employee em = new Employee(id, null, dept, null, null, null, null, null, 1, null);
//        em.setEmployeeId(id);
//        em.setDepartment(dept);
        Map<String,Object>map = new HashMap<>();
        try {
			List<Schedule> list = service.findByDate(em, sdate, edate);
			return list;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
        return map;
	}
	@GetMapping("/skdTeam")
	public Object skdTeam(HttpSession session) {
		String id = session.getAttribute("id").toString();
		//String dept = "MSD";
		String dept = session.getAttribute("dept").toString();
		 Map<String,Object>map = new HashMap<>();
		 try {
			List<Schedule> list = service.findSkdTeam(dept);
			return list;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		 return map;
	}
	@GetMapping("/skdPersonal")
	public Object skdPersonal(HttpSession session) {
		//String id = "MSD003";
		String id = session.getAttribute("id").toString();
		 Map<String,Object>map = new HashMap<>();
		 try {
			List<Schedule> list = service.findSkdPersonal(id);
			return list;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		 return map;
	}
	@GetMapping("/skdContent/{title}/{content}")
	public Object skdContent(@PathVariable String title, @PathVariable String content ,HttpSession session) {
		String id = session.getAttribute("id").toString();
		//String id = "MSD003";
		Employee em = new Employee();
		em.setEmployeeId(id);
        Schedule skd = new Schedule(em, title, content);
        skd.setSkdId(em);
        Map<String,Object>map = new HashMap<>();
        try {
        	List<Schedule> list = service.findByContent(skd);
			return list;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
        return map;
	}
	@GetMapping("/skdDetail/{no}")
	public Object skdDetail(@PathVariable int no) {
		Map<String,Object>map = new HashMap<>();
		 try {
			Schedule list = service.findByDetail(no);
			return list;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		 return map;
	}
	@PostMapping("/addSkd")
	public Map<String, Object> addSkd(@RequestBody Schedule s, HttpSession session){
		Map<String, Object> map = new HashMap<>();
		//String id = "MSD003";
		String id = session.getAttribute("id").toString();
		Employee em = new Employee();
		em.setEmployeeId(id);
		s.setSkdId(em);
		try {
			service.addSkd(s);	
		} catch (AddException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		} catch (DuplicatedException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}
	@PutMapping("/modify/{no}")
	public Map<String, Object> modify(@PathVariable int no,@RequestBody Schedule s,  HttpSession session){
		log.error(s+"스케줄s");
		Map<String, Object> map = new HashMap<>();
//		String id = "MSD003";
		String id = session.getAttribute("id").toString();
		Employee em = new Employee();
		em.setEmployeeId(id);
		s.setSkdId(em);
		s.setSkdNo(no);
		try {
			service.modifySkd(s);
			
		} catch (ModifyException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}
	@DeleteMapping("/remove/{no}")
	public Map<String, Object> remove(@PathVariable int no,HttpSession session){
		Map<String, Object> map = new HashMap<>();
		//String id = "MSD003";
		String id = session.getAttribute("id").toString();
		Schedule s = new Schedule();
		Employee emp = new Employee();
		emp.setEmployeeId(id);
		s.setSkdId(emp);
		s.setSkdNo(no);
		try {
			service.deleteSkd(s);
		} catch (RemoveException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}
}
	
