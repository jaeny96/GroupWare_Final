package com.admin.jobmanage.service;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.jobmanage.dao.JobDAO;
import com.group.employee.dto.Job;
import com.group.exception.FindException;
@Service
public class JobService {
	@Autowired
	private JobDAO dao;
	
	/**
	 * 직무 목록을 조회한다. 
	 * @return 직무목록
	 * @throws FindException
	 */
	public List<Job> showJob() throws FindException{
		return dao.selectJobAll();
	}

}
