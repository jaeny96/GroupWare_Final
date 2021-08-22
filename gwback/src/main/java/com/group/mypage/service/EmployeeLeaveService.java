package com.group.mypage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.employee.dto.Employee;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.mypage.dao.EmployeeLeaveDAO;
import com.group.mypage.dto.EmployeeLeave;

@Service
public class EmployeeLeaveService {
	
	@Autowired
	private EmployeeLeaveDAO dao;
	@Autowired
	private EmployeeLeaveService service;

	/**
	 * 로그인 한 사원의 상세정보를 마이페이지에서 조회한다
	 * 
	 * @param id 로그인한 사원의 아이디
	 * @return 로그인 한 사원의 상세정보
	 * @throws FindException
	 */
	public EmployeeLeave showDetail(String id) throws FindException {
		//System.out.println("service"+dao.selectById(id));
		return dao.selectById(id);
	}

	/**
	 * 로그인 한 사원이 자신의 정보를 수정한다 (연락처, 비밀번호로 제한)
	 * 
	 * @param emp 로그인한 사원이 변경한 정보를 담은 객체
	 * @throws ModifyException
	 */
	public void modify(Employee emp) throws ModifyException {
		try {
			EmployeeLeave el = showDetail(emp.getEmployeeId());
			if ((emp.getPhoneNumber() != null && !el.getEmployee().getPhoneNumber().equals(emp.getPhoneNumber()))
					|| (emp.getPassword() != null && !el.getEmployee().getPassword().equals(emp.getPassword()))) {
				dao.update(emp);
			} else {
				throw new FindException("변경할 정보가 없습니다.");
			}
		} catch (FindException e) {
			e.printStackTrace();
		}
	}
}
