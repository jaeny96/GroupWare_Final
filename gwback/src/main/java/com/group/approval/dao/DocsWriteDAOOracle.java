package com.group.approval.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.group.exception.SearchException;
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
	 *  사용자는 작성한 문서를 기안한다
	 * @param d
	 * @throws AddException
	 */
	public void draft(Document d) throws AddException {
		SqlSession session = null;
		try {
		
			session = sessionFactory.openSession();
			
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
		String apType="";
		try {
			session = sessionFactory.openSession();
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
			session = sessionFactory.openSession();
		
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
			session = sessionFactory.openSession();
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


	



}
