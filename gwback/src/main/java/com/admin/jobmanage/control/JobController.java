package com.admin.jobmanage.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.admin.jobmanage.service.JobService;
import com.google.gson.JsonArray;
import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.UpdateException;

@RestController
@RequestMapping("/admin/*")
public class JobController {

	@Autowired
	private JobService service;
	private Logger log = Logger.getLogger(JobController.class);

	/**
	 * 직무 목록을 조회한다
	 * @return 조회한 모든 직무
	 */
	@GetMapping(value={"/job/all"})
	@ResponseBody
	public Object allJob() {
		Map<String, Object> map = new HashMap<>();
		
		try {
			List<Job> job = service.showJob();
			return job;
		} catch (FindException e) {
			map.put("status", -1);
			map.put("msg", e.getMessage());
			e.printStackTrace();
			return map;
		}
	}
	
	/**(x버튼 관련)
	 * 해당 직무를 가지고 있는 사원들의 이름을 가지고온다. 
	 * @param jobId
	 * @return 사용자의 이름 리스트
	 * @exception FindException
	 */
	
	@GetMapping(value={"/job/emp-all/{jobId}"})
	@ResponseBody
	public List<Employee> allJobEmp(@PathVariable (name="jobId") Optional<String> optjobId) {
		List<Employee> job= new ArrayList<Employee>();
		try {
			 job = service.findJobEmp(optjobId.get());
			 System.out.println(job);
		} catch (FindException e) {
			e.printStackTrace();
			//return map;
		}
		return job;
	}

	/**(저장버튼 관련)
	 * 직무를 추가하기 전에 
	 * 모든 직무를 검색후 모든 직무 삭제한다. 그 다음 새로운 직무들을 추가한다 
	 * @throws AddException
	 * @param List<Job>
	 */
	@PutMapping("/job/save")
	public Object saveProcess(@RequestBody List<Job> jobList) {
		Map<String, Object> map = new HashMap<>();
		try {
		service.saveBtn(jobList);
		map.put("status",0);
		}catch (Exception e) {
			map.put("status", -1);
			map.put("msg", e.getMessage());
			e.printStackTrace();
			return map;
		}
		return map;
	}


	@PutMapping("/job/changeJob/{oldJobId}")
	@ResponseBody
	public Object changeJob(@PathVariable String oldJobId,@RequestBody List<Employee> employees) {
		Map<String, Object> map = new HashMap<>();
		try {
		service.changeJobEmp(oldJobId,employees);
		map.put("status",0);
		}catch (Exception e) {
			map.put("status", -1);
			map.put("msg", e.getMessage());
			e.printStackTrace();
			return map;
		}
		return map;
	}
}
