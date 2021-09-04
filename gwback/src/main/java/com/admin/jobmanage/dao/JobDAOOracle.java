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
	/**(저장버튼 관련)
	 * 직무를 추가하기 전에 
	 * 모든 직무를 검색후 모든 직무 삭제한다. 그 다음 새로운 직무들을 추가한다 
	 * @throws AddException
	 * @param List<Job>
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Transactional(rollbackFor = com.group.exception.AddException.class)
	public void selectAndDeleteAndInsert(List<Job>newJobs) throws AddException{

		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			List<Job>oldJobs = selectJobAll(session);
			log.error("--oldjobs--" + oldJobs);
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

	private List<Job> selectJobAll(SqlSession session) {
		List<Job> jobList = session.selectList("com.group.employee.DepartmentMapper.selectJob");			
		return jobList;
	}
	
	private int  deleteJobAll(SqlSession session, List<Job> oldJobs) {
		int rowcntSum = 0;
		for(Job j:oldJobs) {
			try {
			  int rowcnt = session.delete("com.group.employee.DepartmentMapper.deleteJobOne",j);
			  rowcntSum += rowcnt;
			}catch(Exception e) { 
				System.out.println("delete fail job id=" + j.getJobId());
			}
		}
		return rowcntSum;
	}
		
	private int insertJobAll(SqlSession session, List<Job>newJobs) throws AddException{
		int rowcntSum = 0;
		for(Job j: newJobs) {
			try {
				int rowcnt = session.insert("com.group.employee.DepartmentMapper.insertJobOne", j);
				rowcntSum += rowcnt;
			}catch(Exception e) {
				System.out.println("insert fail jobid=" + j.getJobId());
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
	
	/**(x모달 관련)
	 * 받아온 사원의 직무를 변경한다. 
	 * @param oldJobId 변경전 직무,newJobId 변경후 직무,employeeId 변경할 사원아이디 
	 * @exception RemoveException
	 */
	@Override
	@Transactional(rollbackFor = com.group.exception.ModifyException.class)
	public void updateJobEep(String oldJobId,List<Employee> employees) throws ModifyException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			for(Employee e: employees) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("oldJobId",oldJobId);
				map.put("newJobId",e.getJob().getJobId());
				map.put("employeeId",e.getEmployeeId());
				session.update("com.group.employee.DepartmentMapper.updateJob",map);
				System.out.println(map.get("oldJobId")+map.get("newJobId")+map.get("employeeId"));
			}
		} catch (Exception e) {
			throw new ModifyException(e.getMessage());	
		} finally {
			if(session!=null)
			session.close();
		}
		
	}





}
