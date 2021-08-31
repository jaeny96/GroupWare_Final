package com.admin.jobmanage.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.admin.jobmanage.service.JobService;
import com.group.employee.dto.Job;
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
		Map<String, Object> result = new HashMap<>();
		
		try {
			List<Job> job = service.showJob();
			return job;
		} catch (FindException e) {
			result.put("status", -1);
			result.put("msg", e.getMessage());
			e.printStackTrace();
			return result;
		}

	}

}
