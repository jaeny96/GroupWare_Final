package com.group.approval.dao;

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
import com.group.employee.dto.Department;

@Repository("docsWriteDAO")
public class DocsWriteDAOOracle implements DocsWriteDAO {
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	// 1. 문서 번호 최대값 구하기
	@Override
	public int chkMaxNum(String document_type) throws FindException {
		SqlSession session = null;
		int maxNum;
		try {
			session = sqlSessionFactory.openSession();
			maxNum = session.selectOne("com.group.approval.ApprovalWriteMapper.checkMaxNum", document_type);
			return maxNum;
		} catch (Exception e) {
			maxNum=0;
			return maxNum;
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	// 2-1. 문서기안
	@Override
	public void draftDoc(SqlSession session, Document d) throws AddException {
		try {
			session.insert("com.group.approval.ApprovalWriteMapper.insertDraft", d);

		} catch (Exception e) {
			throw new AddException(e.getMessage());
		}
	}

	// 2-2. 결재자 등록
	@Override
	public void draftAp(SqlSession session, Approval ap) throws AddException {
		String apType = "";
		try {
			ApprovalStatus aps = new ApprovalStatus();
			if (ap.getApStep() == 0) {
				apType = "승인";
			} else {
				apType = "대기";
			}
			aps.setApType(apType);
			ap.setApStatus(aps);
			session.insert("com.group.approval.ApprovalWriteMapper.insertDraftAp", ap);

		} catch (Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}
	}

	// 2-3. 합의자 등록
	@Override
	public void draftAg(SqlSession session, Agreement ag) throws AddException {
		try {
			session.insert("com.group.approval.ApprovalWriteMapper.insertDraftAg", ag);
		} catch (Exception e) {
			throw new AddException(e.getMessage());
		}
	}

	// 2-4. 참조자 등록
	@Override
	public void draftRe(SqlSession session, Reference re) throws AddException {
		try {
			session.insert("com.group.approval.ApprovalWriteMapper.insertDraftRe", re);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 2. 기안하기 트랜잭션 처리
	@Override
	@Transactional(rollbackFor = AddException.class)
	public void draft(Document d) throws AddException {
		SqlSession session = null;
		try {
			// session 객체가 jdbc의 Connection과 같은 역할을 해줌
			session = sqlSessionFactory.openSession();
			draftDoc(session, d);
			for (Approval ap : d.getApprovals()) {
				if ("staffOne".equals(ap.getEmployee().getEmployeeId())
						|| "staffTwo".equals(ap.getEmployee().getEmployeeId())
						|| "staffThree".equals(ap.getEmployee().getEmployeeId())) {

				} else {
					draftAp(session, ap);
				}
			}
			if (d.getAgreement() != null) {
				if (!"agreementBoxBtn".equals(d.getAgreement().getEmployee().getEmployeeId())) {

					draftAg(session, d.getAgreement());
				}
			}
			if (d.getReference() != null) {
				if (!"referenceBoxBtn".equals(d.getReference().getEmployee().getEmployeeId())) {
					draftRe(session, d.getReference());
				}
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

	// 3. 전체 부서 목록 조회
	@Override
	public List<Department> selectDept() throws FindException {
		SqlSession session = null;
		try {
			// session 객체가 jdbc의 Connection과 같은 역할을 해줌
			session = sqlSessionFactory.openSession();
			List<Department> deptList = session.selectList("com.group.approval.ApprovalWriteMapper.selectDept");
			return deptList;
		} catch (Exception e) {
			session.rollback(); // 롤백
			throw new FindException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	// 4. 부서별 사원 목록 조회
	@Override
	public List<Employee> selectEmpByDept(String deptId) throws FindException {
		SqlSession session = null;
		try {
			// session 객체가 jdbc의 Connection과 같은 역할을 해줌
			session = sqlSessionFactory.openSession();
			List<Employee> empList = session.selectList("com.group.approval.ApprovalWriteMapper.selectEmpByDept",
					deptId);
			return empList;
		} catch (Exception e) {
			session.rollback(); // 롤백
			throw new FindException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}