package com.admin.jobmanage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.admin.jobmanage.dao.JobDAO;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;
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
	/**(저장버튼 관련)
	 * 직무를 추가하기 전에 
	 * 모든 직무를 검색후 모든 직무 삭제한다. 그 다음 새로운 직무들을 추가한다 
	 * @throws AddException
	 * @param List<Job>
	 */
	public void saveBtn(List<Job> jobList) throws RemoveException, AddException{
		dao.selectAndDeleteAndInsert(jobList);
	}
	/**(x버튼 관련)
	 * 해당 직무를 가지고 있는 사원들의 이름을 가지고온다. 
	 * @param jobId
	 * @return 사용자의 이름 리스트
	 * @exception FindException
	 */
	public List<Employee> findJobEmp (String jobId) throws FindException{
		return dao.selectEmpName(jobId);
	}
	/**(x모달 관련)
	 * 받아온 사원의 직무를 변경한다. 
	 * @param oldJobId 변경전 직무,newJobId 변경후 직무,name 변경할 사원 
	 * @exception RemoveException
	 */
	public void changeJobEmp (String oldJobId,List<Employee> employees) throws ModifyException{
		dao.updateJobEep(oldJobId,employees);
	}


}
