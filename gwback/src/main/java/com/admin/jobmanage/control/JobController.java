package com.admin.jobmanage.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.admin.jobmanage.service.JobService;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.exception.AddException;
import com.group.exception.FindException;

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
		} catch (FindException e) {
			e.printStackTrace();
		}
		return job;
	}

	/**(저장버튼 관련)
	 * 직무를 추가하기 전에 
	 * 모든 직무를 검색후 모든 직무 삭제한다. 그 다음 새로운 직무들을 추가한다 
	 * @throws AddException
	 * @param List<Job>
	 */
	@PutMapping(value={"/job/save","/job/save/{oldJobId}"})
	public Map<String, Object> saveProcess(@RequestBody Map<String, Object>requestMap,
			@PathVariable (name="oldJobId") Optional<String> oldJobId) {
		Map<String, Object> map = new HashMap<>();
		
		List<Map<String, String>> jobList = (List)requestMap.get("jobList");
		List<Map<String, String>> employeeList = (List)requestMap.get("employeeList");
	
		try {

			if(employeeList.size()==0) {//변경값 존재안할시 
//				System.out.println("변경값 존재x");
//				System.out.println("jobList ; "+requestMap.get("jobList"));
				service.saveBtn(jobList);

			}else {//변경값 존재 
//				System.out.println("jobList ; "+requestMap.get("jobList"));
//				System.out.println("employeeList ;" +requestMap.get("employeeList"));
//				System.out.println("oldJobId ;" + oldJobId);
				service.changeJobEmp(oldJobId.get(), jobList,employeeList);
			}

	
		map.put("status",0);
		return map;
		}catch (Exception e) {
			map.put("status", -1);
			map.put("msg", e.getMessage());
			e.printStackTrace();
			return map;
		}
		
	}

}
