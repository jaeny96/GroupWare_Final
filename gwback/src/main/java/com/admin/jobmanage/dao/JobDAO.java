package com.admin.jobmanage.dao;

import java.util.List;

import com.group.employee.dto.Job;
import com.group.exception.FindException;


public interface JobDAO {
	/**
	 * 직무를 조회한다
	 * @return 전체 직무 
	 */
	public List<Job> selectJobAll() throws FindException;
}
