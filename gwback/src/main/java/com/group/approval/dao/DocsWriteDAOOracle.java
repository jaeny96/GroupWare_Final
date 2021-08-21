package com.group.approval.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.ApprovalStatus;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.dto.Reference;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.employee.dto.Position;
import com.group.employee.dto.Department;
import com.group.sql.MyConnection;

@Repository("DocsWriteDAO")
public class DocsWriteDAOOracle implements DocsWriteDAO {
	
	@Autowired
	private SqlSessionFactory sessionFactory;

	/**
	 * 해당 문서 종류의 최고 숫자를 출력한다
	 * @param documentType 문서 종류
	 * @return 해당 문서 종류의 최고 숫자
	 * @throws FindException
	 */
	@Override
	public int chkMaxNum(String documentType) throws FindException {
		SqlSession session = null;
		
		try {
			session = sessionFactory.openSession();
			int maxNum = session.selectOne("com.group.approval.ApprovalWriteMapper.checkMaxNum", documentType);
			return maxNum;
		}catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	/**
	 * 1-1. 사용자는 작성한 문서를 기안한다
	 * @param d
	 * @throws AddException
	 */
	public void draft(Document d) throws AddException {
		SqlSession session = null;
		try {
		
			session = sessionFactory.openSession();
			System.out.println(d);
			d.getDocumentNo();
			d.getDocumentStatus().getDocumentType();
			d.getEmployeeD().getEmployeeId();
			d.getDocumentTitle();
			d.getDocumentContent();
			int rowcnt = session.insert("com.group.approval.ApprovalWriteMapper.insertDraft", d);
			if(rowcnt==1) {
				System.out.println("문서기안 완료");
			}else {
				System.out.println("문서기안 실패");
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	/**
	 *  문서 기안 시 결재자를 등록한다
	 * @param ap
	 * @throws AddException
	 */
	@Override
	public void draftAp(Approval ap) throws AddException {
		SqlSession session = null;
		
		String apType = "";//전처리 apTypet셋팅
//		if(ap.getApStep()==0) {//비어있지 않으면? 승인
//			apType="승인";
//		}else {
//			ApprovalStatus aps = new ApprovalStatus();
//			aps.setApType("대기");
//			ap.setApStatus(aps);
//			apType=ap.getApStatus().getApType();	
//		}
//		try {
//		if(ap.getApStep()==0) {
//			apType="승인";
//		}else {
//			apType="대기";
//		}
//		}catch(NullPointerException e) {
//			apType="대기";
//		}
		try {
			
			ap.getDocumentNo();
			ap.getEmployee().getEmployeeId();
			ApprovalStatus aps = new ApprovalStatus();
			if(ap.getApStep()==0) {
				apType="승인";
			}else {
				apType="대기";
			}
			aps.setApType(apType);
			ap.setApStatus(aps);
			ap.getApStatus().getApType();
			ap.getApStep();
			System.out.println(ap.getDocumentNo());
			System.out.println(ap.getApStatus().getApType());
			System.out.println(ap.getEmployee().getEmployeeId());
			System.out.println(ap.getApStep());
			
			System.out.println(ap);
			int rowcnt = session.insert("com.group.approval.ApprovalWriteMapper.insertDraftAp", ap);
			if(rowcnt==1) {
				System.out.println("결재 문서기안 완료");
			}else {
				System.out.println("결재 문서기안 실패");
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}

	}

	/**
	 *  문서 기안 시 합의자를 등록한다
	 * @param ag
	 * @throws AddException
	 */
	@Override
	public void draftAg(Agreement ag) throws AddException {
		SqlSession session = null;
		
		try {

			ag.getDocumentNo();
			ag.getEmployee().getEmployeeId();
			
			int rowcnt = session.insert("com.group.approval.ApprovalWriteMapper.insertDraftAg", ag);
			if(rowcnt==1) {
				System.out.println("합의 문서기안 완료");
			}else {
				System.out.println("합의 문서기안 실패");
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	/**
	 *  문서 기안 시 참조자를 등록한다
	 * @param re
	 * @throws AddException
	 */
	@Override
	public void draftRe(Reference re) throws AddException {// DB연결
		SqlSession session = null;
		
		try {
			System.out.println(re.getDocumentNo()+"/"+
					re.getEmployee().getEmployeeId());
			re.getDocumentNo();
			re.getEmployee().getEmployeeId();
			System.out.println(re);
			int rowcnt = session.insert("com.group.approval.ApprovalWriteMapper.insertDraftRe", re);
			if(rowcnt==1) {
				System.out.println("합의 문서기안 완료");
			}else {
				System.out.println("합의 문서기안 실패");
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
//
//	// 2-1. 사원이름을 검색해 결재선에 넣을 사원을 조회한다
//	public List<Employee> searchByName(String deptName) throws FindException {
//		// DB연결
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		}
//
//		String searchByNameSQL = "SELECT e.name, e.employee_id, d.department_id, d.department_title \r\n"
//				+ "FROM department d\r\n" + "JOIN employee e ON d.department_id = e.department_id\r\n"
//				+ "JOIN position p ON e.position_id=p.position_id JOIN job j ON e.job_id=j.job_id\r\n"
//				+ "WHERE d.department_title=? AND enabled=1\r\n" + "ORDER BY p.position_id, employee_id";
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List<Employee> list = new ArrayList<>();
//		try {
//			pstmt = con.prepareStatement(searchByNameSQL);
//			pstmt.setString(1, deptName);
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Employee em = new Employee();
////				String name = rs.getString("name");
//				em.setEmployee_id(rs.getString("employee_id"));
//				em.setName(rs.getString("name"));
//				Department dept = new Department();
//				dept.setDepartment_id(rs.getString("department_id"));
//				dept.setDepartment_title(rs.getString("department_title"));
//				em.setDepartment(dept);
//				list.add(em);
//			}
//			if (list.size() == 0) {
//				throw new FindException("해당 사원이 없습니다");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			// DB연결해제
//			MyConnection.close(con, pstmt, rs);
//		}
//		return list; // return 구문은 try블럭 뒤에 놓든, finally 뒤에 놓든 결과는 같다.
//	}
//	
////
////	// 2-2. 부서이름을 검색해 결재선에 넣을 사원이 속한 조직을 조회한다
////	public List<Department> searchByDep(String word) throws FindException {
////		// DB연결
////		Connection con = null;
////		try {
////			con = MyConnection.getConnection();
////		} catch (SQLException e) {
////			e.printStackTrace();
////			throw new FindException(e.getMessage());
////		}
////		String searchByDepSQL = "SELECT department_title\r\n" + "FROM department \r\n"
////				+ "where department_title like ?";
////		PreparedStatement pstmt = null;
////		ResultSet rs = null;
////		List<Department> list = new ArrayList<>();
////		try {
////			pstmt = con.prepareStatement(searchByDepSQL);
////			pstmt.setString(1, "%" + word + "%");
////			rs = pstmt.executeQuery();
////			while (rs.next()) {
////				Department dep = new Department();
////				String department_title = rs.getString("department_title");
////				dep.setDepartment_title(department_title);
////
////				list.add(dep);
////			}
////			if (list.size() == 0) {
////				throw new FindException("해당 부서가 없습니다");
////			}
////		} catch (SQLException e) {
////			e.printStackTrace();
////			throw new FindException(e.getMessage());
////		} finally {
////			// DB연결해제
////			MyConnection.close(con, pstmt, rs);
////		}
////		return list; // return 구문은 try블럭 뒤에 놓든, finally 뒤에 놓든 결과는 같다.
////	}
//
//	// 3. 전체 사원의 이름, 부서 정보 갖고오기
//	@Override
//	public List<Employee> searchApLineStaff() throws FindException {
////		Connection con = null;
////		try {
////			con = MyConnection.getConnection();
////		} catch (SQLException e) {
////			e.printStackTrace();
////			throw new FindException(e.getMessage());
////		}
////		String selectNameDepSQL = "SELECT e.name, department_title\r\n" + "FROM department d\r\n"
////				+ "JOIN employee e ON d.department_id = e.department_id\r\n"
////				+ "JOIN position p ON e.position_id=p.position_id JOIN job j ON e.job_id=j.job_id\r\n"
////				+ "WHERE enabled=1\r\n"
////				+ "ORDER BY DECODE(d.department_id,'CEO',1),d.department_title, p.position_id, employee_id";
////		System.out.println(selectNameDepSQL);
////		PreparedStatement pstmt = null;
////		ResultSet rs = null;
////		List<Employee> empList = new ArrayList<Employee>();
////		try {
////			pstmt = con.prepareStatement(selectNameDepSQL);
////			rs = pstmt.executeQuery();
////
////			while (rs.next()) {
////				Employee emp = new Employee();
////				emp.setEmployee_id(rs.getString("employee_id"));
////				emp.setName(rs.getString("name"));
////				Department d = new Department();
////				d.setDepartment_id(rs.getString("department_id"));
////				d.setDepartment_title(rs.getString("department_title"));
////				emp.setDepartment(d);
////				Position p = new Position();
////				p.setPosition_title(rs.getString("position_title"));
////				emp.setPosition(p);
////				Job j = new Job();
////				j.setJob_title(rs.getString("job_title"));
////				emp.setJob(j);
////				emp.setPhone_number(rs.getString("phone_number"));
////				emp.setEmail(rs.getString("email"));
////
////				empList.add(emp);
////			}
////		} catch (SQLException e) {
////			e.printStackTrace();
////			throw new FindException(e.getMessage());
////		} finally {
////			MyConnection.close(con, pstmt, rs);
////		}
////		return empList;
//		return null;
//	}
//	


}
