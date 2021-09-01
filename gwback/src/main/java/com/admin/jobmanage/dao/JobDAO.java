package com.admin.jobmanage.dao;

import java.util.List;

import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.RemoveException;


public interface JobDAO {
	
	/**
	 * 직무를 조회한다
	 * @return 전체 직무 
	 * @exception FindException
	 */
	public List<Job> selectJobAll() throws FindException;
	
	/**(저장버튼 관련)
	 * 직무를 추가하기 전에 
	 * 모든 직무를 검색후 모든 직무 삭제한다. 그 다음 새로운 직무들을 추가한다 
	 * @throws AddException
	 * @param List<Job>
	 */
	public void selectAndDeleteAndInsert(List<Job>jobs) throws AddException;

	/**(x버튼 관련)
	 * 해당 직무를 가지고 있는 사원들의 이름을 가지고온다. 
	 * @param jobId
	 * @return 사용자의 이름 리스트
	 * @exception FindException
	 */
	public List<Employee> selectEmpName(String jobId) throws FindException, FindException ;
	
	/**(x모달 관련)
	 * 받아온 사원의 직무를 변경한다. 
	 * @param oldJobId 변경전 직무,newJobId 변경후 직무,name 변경할 사원 
	 * @exception RemoveException
	 */
	public void updateJobEep(String oldJobId,String newJobId, String name) throws RemoveException;

}
