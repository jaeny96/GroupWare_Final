package com.group.mypage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.employee.dto.Leave;
import com.group.employee.dto.Position;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.mypage.dto.EmployeeLeave;
import com.group.sql.MyConnection;

public class EmployeeLeaveDAOOracle implements EmployeeLeaveDAO {
	public EmployeeLeave selectById(String id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}

		String selectSQL = "SELECT *\r\n" + "FROM employee e \r\n"
				+ "JOIN department d ON e.department_id = d.department_id\r\n"
				+ "JOIN position p ON e.position_id = p.position_id\r\n" + "JOIN job j ON e.job_id = j.job_id\r\n"
				+ "JOIN leave l ON l.employee_id = e.employee_id \r\n" + "WHERE e.employee_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployeeLeave empleave = null;
		try {
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				empleave = new EmployeeLeave();

				Employee emp = new Employee();
				emp.setEmployeeId(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				
				Department d = new Department();
				d.setDepartment_id(rs.getString("department_id"));
				d.setDepartment_title(rs.getString("department_title"));
				emp.setDepartment(d);
				
				Position p = new Position();
				p.setPosition_id(rs.getInt("position_id"));
				p.setPosition_title(rs.getString("position_title"));
				emp.setPosition(p);
				
				Job j = new Job();
				j.setJob_id(rs.getString("job_id"));
				j.setJob_title(rs.getString("job_title"));
				emp.setJob(j);
				
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setEmail(rs.getString("email"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setenabled(rs.getInt("enabled"));
				emp.setPassword(rs.getString("password"));

				Leave leave = new Leave();
				leave.setGrant_days(rs.getInt("grant_days"));
				leave.setRemain_days(rs.getInt("remain_days"));
				leave.setUse_days(rs.getInt("grant_days") - rs.getInt("remain_days"));

				empleave.setEmployee(emp);
				empleave.setLeave(leave);
			} else {
				throw new FindException("정보를 찾을 수 없습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		return empleave;
	}

	public void update(Employee emp) throws ModifyException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}

		String str = "";

		if (emp.getPhoneNumber() != null) {
			str += " phone_number='" + emp.getPhoneNumber() + "',";
		}

		if (emp.getPassword() != null) {
			str += " password='" + emp.getPassword() + "',";
		}

		String updateSQL = "UPDATE employee SET" + str.substring(0, str.length() - 1) + " WHERE employee_id=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(updateSQL);
			pstmt.setString(1, emp.getEmployeeId());
			int rowcnt = pstmt.executeUpdate();
			if (rowcnt == 1) {
				System.out.println(emp.getEmployeeId() + "의 정보가 변경되었습니다.");
			} else {
				System.out.println(rowcnt);
				throw new ModifyException("정보를 변경할 수  없습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}
	}

}
