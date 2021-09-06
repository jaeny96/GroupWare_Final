package com.admin.jobmanage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;
import com.group.mypage.service.EmployeeLeaveService;

import oracle.jdbc.logging.annotations.Log;

@Repository("JobDAO")
public class JobDAOOracle implements JobDAO {

	@Autowired
	private SqlSessionFactory sessionFactory;
	
	/**
	 * 직무를 조회한다
	 * @return 전체 직무 
	 * @exception FindException
	 */
	@Override
	public List<Job> selectJobAll() throws FindException{
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			List<Job> jobList = session.selectList("com.group.employee.DepartmentMapper.selectJob");			
			return jobList;
		} catch (Exception e) {
			throw new FindException(e.getMessage());	
		} finally {
			if(session!=null)
			session.close();
		}
	}
	/**(저장버튼 관련) 변경값 x
	 * 직무를 추가하기 전에 
	 * 모든 직무를 검색후 모든 직무 삭제한다. 그 다음 새로운 직무들을 추가한다 
	 * @throws AddException
	 * @param List<Job>
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Transactional(rollbackFor = com.group.exception.AddException.class)
	public void selectAndDeleteAndInsert(List<Map<String, String>>newJobs) throws AddException{
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			List<Job>oldJobs = selectJobAll(session);
			System.out.println("--oldjobs"+oldJobs);
			int deletecnt = deleteJobAll(session, oldJobs);	
			System.out.println("delete 처리건수 " + deletecnt);
			
			int insertcnt = insertJobAll(session, newJobs);
			System.out.println("insert 처리건수 " + insertcnt);
		
		}catch(Exception e) {
			throw new AddException (e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	/**(저장버튼 관련)변경값 o
	 * 직무를 추가하기 전에 
	 * 모든 직무를 검색후 모든 직무 삭제한다. 그 다음 새로운 직무들을 추가하고, 변경할 사원들을 변경한다. 
	 * @throws AddException
	 * @param List<Job>
	 */
	@Transactional(rollbackFor = {com.group.exception.AddException.class, com.group.exception.ModifyException.class})
	public void selectAndDeleteAndInsert(String oldJobId,List<Map<String,String>> newJobs,
			List<Map<String, String>> employeeList )throws ModifyException{
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			List<Job>oldJobs = selectJobAll(session);
			System.out.println("--oldjobs"+oldJobs);
			int deletecnt = deleteJobAll(session, oldJobs);	
			System.out.println("delete 처리건수 " + deletecnt);
			
			int insertcnt = insertJobAll(session, newJobs);
			System.out.println("insert 처리건수 " + insertcnt);
			
			int updatecnt = updateJobAll(session, oldJobId,employeeList);
			System.out.println("update 처리건수 " + insertcnt);
		
		}catch(Exception e) {
			throw new ModifyException (e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	private List<Job> selectJobAll(SqlSession session) {
		List<Job> jobList = session.selectList("com.group.employee.DepartmentMapper.selectJob");			
		return jobList;
	}
	
	private int  deleteJobAll(SqlSession session, List<Job> oldJobs) {
		int rowcntSum = 0;
		for(Job j:oldJobs) {
			try {
			  int rowcnt = session.delete("com.group.employee.DepartmentMapper.deleteJobOne",j);
				System.out.println("delete success jobid=" + j.getJobId());
			  rowcntSum += rowcnt;
			}catch(Exception e) { 
				System.out.println("delete fail jobid=" + j.getJobId());
			}
		}
		return rowcntSum;
	}
		

	private int insertJobAll(SqlSession session, List<Map<String,String>>newJobs) throws AddException{
		int rowcntSum = 0;
		for(Map<String,String>jmap: newJobs) {
			System.out.println(jmap.get("jobId"));
			Job j = new Job(jmap.get("jobId"), jmap.get("jobTitle"));//값셋팅 
			try {
				int rowcnt = session.insert("com.group.employee.DepartmentMapper.insertJobOne",  j );
				System.out.println("insert success jobid=" + j.getJobId());
				rowcntSum += rowcnt;
			}catch(Exception e) {
				System.out.println("insert fail jobid=" + j.getJobId());
			}
		}
		return rowcntSum;
	}
	
	private int updateJobAll(SqlSession session,String oldJobId,List<Map<String, String>> employees) throws ModifyException{
		int rowcntSum = 0;
		for(Map<String,String> emap: employees) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("oldJobId",oldJobId);
			map.put("newJobId",emap.get("jobId"));
			map.put("employeeId",emap.get("employeeId"));
			try {
				int rowcnt = session.update("com.group.employee.DepartmentMapper.updateJob",map);		
				System.out.println("update success jobid=" +map.get("employeeId"));			
				rowcntSum += rowcnt;
			}catch(Exception e) {
				System.out.println("update fail jobid=" +map.get("employeeId"));
			}
		}
		return rowcntSum;
	}

	/**(x버튼 관련)
	 * 해당 직무를 가지고 있는 사원들의 이름을 가지고온다. 
	 * @param jobId
	 * @return 사용자의 이름 리스트
	 * @exception FindException
	 */
	@Override
	public List<Employee> selectEmpName(String jobId) throws FindException{
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			List<Employee> jobList = session.selectList("com.group.employee.DepartmentMapper.selectEmpName",jobId);			
			return jobList;
		} catch (Exception e) {
			throw new FindException(e.getMessage());	
		} finally {
			if(session!=null)
			session.close();
		}
	}
	



}
