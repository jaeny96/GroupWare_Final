package com.group.approval.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.ApprovalStatus;
import com.group.approval.dto.Document;
import com.group.approval.dto.Reference;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.employee.dto.Position;
import com.group.employee.dto.Department;
import com.group.sql.MyConnection;

@Repository("docsWriteDAO")
public class DocsWriteDAOOracle implements DocsWriteDAO {
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	// 1. 문서기안
	public void draftDoc(SqlSession session, Document d) throws AddException {
		try {
			session.insert("com.group.approval.ApprovalWriteMapper.insertDraft", d);

		} catch (Exception e) {
			throw new AddException(e.getMessage());
		}
	}

	@Override
	public void draftAp(SqlSession session, List<Approval> approvals) throws AddException {
		String apType = "";
		try {
			for(Approval ap : approvals) {
				ApprovalStatus aps = new ApprovalStatus();
				if (ap.getApStep() == 0) {
					apType = "승인";
				} else {
					apType = "대기";
				}
				aps.setApType(apType);
				ap.setApStatus(aps);
				session.insert("com.group.approval.ApprovalWriteMapper.insertDraftAp", ap);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}
	}

	@Override
	public void draftAg(SqlSession session, Agreement ag) throws AddException {
		try {
			session.insert("com.group.approval.ApprovalWriteMapper.insertDraftAg", ag);
		} catch (Exception e) {
			throw new AddException(e.getMessage());
		}
	}

	@Override
	public void draftRe(SqlSession session, Reference re) throws AddException {
		try {
			session.insert("com.group.approval.ApprovalWriteMapper.insertDraftRe", re);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(rollbackFor = AddException.class)
	public void draft(Document d) throws AddException {
		SqlSession session = null;
		try {
			// session 객체가 jdbc의 Connection과 같은 역할을 해줌
			session = sqlSessionFactory.openSession();
			draftDoc(session, d);
			draftAp(session, d.getApprovals());
			if (d.getAgreement() != null) {
				draftAg(session, d.getAgreement());
			}
			if (d.getReference() != null) {
				draftRe(session, d.getReference());
			}
		} catch (Exception e) {
			session.rollback(); // 롤백
			throw new AddException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	// 2-1. 사원이름을 검색해 결재선에 넣을 사원을 조회한다
	public List<Employee> searchByName(String deptName) throws FindException {
		// DB연결
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}

		String searchByNameSQL = "SELECT e.name, e.employee_id, d.department_id, d.department_title \r\n"
				+ "FROM department d\r\n" + "JOIN employee e ON d.department_id = e.department_id\r\n"
				+ "JOIN position p ON e.position_id=p.position_id JOIN job j ON e.job_id=j.job_id\r\n"
				+ "WHERE d.department_title=? AND enabled=1\r\n" + "ORDER BY p.position_id, employee_id";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Employee> list = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(searchByNameSQL);
			pstmt.setString(1, deptName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Employee em = new Employee();
//				String name = rs.getString("name");
				em.setEmployeeId(rs.getString("employee_id"));
				em.setName(rs.getString("name"));
				Department dept = new Department();
				dept.setDepartmentId(rs.getString("department_id"));
				dept.setDepartmentTitle(rs.getString("department_title"));
				em.setDepartment(dept);
				list.add(em);
			}
			if (list.size() == 0) {
				throw new FindException("해당 사원이 없습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			// DB연결해제
			MyConnection.close(con, pstmt, rs);
		}
		return list; // return 구문은 try블럭 뒤에 놓든, finally 뒤에 놓든 결과는 같다.
	}
//
//	// 2-2. 부서이름을 검색해 결재선에 넣을 사원이 속한 조직을 조회한다
//	public List<Department> searchByDep(String word) throws FindException {
//		// DB연결
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		}
//		String searchByDepSQL = "SELECT department_title\r\n" + "FROM department \r\n"
//				+ "where department_title like ?";
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List<Department> list = new ArrayList<>();
//		try {
//			pstmt = con.prepareStatement(searchByDepSQL);
//			pstmt.setString(1, "%" + word + "%");
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Department dep = new Department();
//				String department_title = rs.getString("department_title");
//				dep.setDepartment_title(department_title);
//
//				list.add(dep);
//			}
//			if (list.size() == 0) {
//				throw new FindException("해당 부서가 없습니다");
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

	// 3. 전체 사원의 이름, 부서 정보 갖고오기
	@Override
	public List<Employee> searchApLineStaff() throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectNameDepSQL = "SELECT e.name, department_title\r\n" + "FROM department d\r\n"
				+ "JOIN employee e ON d.department_id = e.department_id\r\n"
				+ "JOIN position p ON e.position_id=p.position_id JOIN job j ON e.job_id=j.job_id\r\n"
				+ "WHERE enabled=1\r\n"
				+ "ORDER BY DECODE(d.department_id,'CEO',1),d.department_title, p.position_id, employee_id";
		System.out.println(selectNameDepSQL);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Employee> empList = new ArrayList<Employee>();
		try {
			pstmt = con.prepareStatement(selectNameDepSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Employee emp = new Employee();
				emp.setEmployeeId(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				Department d = new Department();
				d.setDepartmentId(rs.getString("department_id"));
				d.setDepartmentTitle(rs.getString("department_title"));
				emp.setDepartment(d);
				Position p = new Position();
				p.setPosition_title(rs.getString("position_title"));
				emp.setPosition(p);
				Job j = new Job();
				j.setJob_title(rs.getString("job_title"));
				emp.setJob(j);
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setEmail(rs.getString("email"));

				empList.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		return empList;
	}

	@Override
	public int chkMaxNum(String document_type) throws FindException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			int maxNum = session.selectOne("com.group.approval.ApprovalWriteMapper.checkMaxNum", document_type);
			return maxNum;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		}
//		String selectChkMaxNumSQL = "SELECT MAX(SUBSTR(document_no,-4)) FROM document WHERE SUBSTR(document_no,4,2)=?";
////		System.out.println(selectChkMaxNumSQL);
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		int maxNum = 0;
//		try {
//			pstmt = con.prepareStatement(selectChkMaxNumSQL);
//			pstmt.setString(1, document_type);
//			rs = pstmt.executeQuery();
//			if (rs.next()) {
//				maxNum = rs.getInt(1);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//		return maxNum;
	}
}