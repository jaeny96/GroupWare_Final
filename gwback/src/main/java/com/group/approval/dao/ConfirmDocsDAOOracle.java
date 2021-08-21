package com.group.approval.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.exception.FindException;
import com.group.exception.SearchException;

@Repository("confirmDocsDAO")
public class ConfirmDocsDAOOracle implements ConfirmDocsDAO {

	@Autowired
	private SqlSessionFactory sessionFactory;

	/**
	 * (전체/대기/반려/승인)사용자는 확인/미확인 문서를 선택해서 볼 수 있다.
	 * @return 확인/미확인한 전체 문서 목록
	 * @param id,check,status
	 * @throws FindException
	 */
	@Override
	public List<Document> selectByCheck(String id,String status,String check) throws FindException {
		SqlSession session = null;
		
		try {
			List<Document> list = new ArrayList<>();
			
			session = sessionFactory.openSession();
			HashMap<String, String>map = new HashMap<>();
			map.put("id", id);
			map.put("status", status);
			map.put("check", check);
			System.out.println("map"+map);
			return session.selectList("com.group.approval.ApprovalWriteMapper.selectByCheck", map);
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
	 * 사용자는 문서를 선택하면,해당 문서에서 자신이 승인해야하는 부분을 확인할 수 있다.
	 * 
	 * @param employee_id,document_no
	 * @throws FindException
	 */
	@Override
	public List<Approval> selectByMyClick(String id, String docsNo) throws FindException {
		SqlSession session = null;
		
		try {
			List<Approval> list = new ArrayList<>();
			
			session = sessionFactory.openSession();
			HashMap<String, String>map = new HashMap<>();
			map.put("id", id);
			map.put("docsNo", docsNo);
			
			list=session.selectList("com.group.approval.ApprovalConfirmMapper.selectByMyClick", map);
			System.out.println(list);
			if(list.size()==0) {
				throw new FindException("승인 목록이 없습니다.");
			}

			return list;
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
	 * 사용자는 결재 문서를 선택했을 때, 해당 문서의 상세 내용정보를 확인할 수 있다. (내용+결재선)
	 * 
	 * @param document_no
	 * @throws FindException
	 */
	@Override
	public Document selectByDocsDetail(String docsNo) throws FindException {

		SqlSession session = null;
		
		try {
			Document list ;
			
			session = sessionFactory.openSession();
		
			list=session.selectOne("com.group.approval.ApprovalConfirmMapper.selectByDocsDetail", docsNo);
			System.out.println(list);
			

			return list;
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
	 * 사용자는 결재 문서를 선택했을 때, 해당 문서의 코멘트 정보를 확인할 수 있다.
	 * 
	 * @param document_no
	 * @throws FindException
	 */
	@Override
	public List<Approval> selectByComments(String docsNo) throws FindException {

		SqlSession session = null;
		
		try {
			List<Approval> list = new ArrayList<>();
			
			session = sessionFactory.openSession();
		
			list=session.selectList("com.group.approval.ApprovalConfirmMapper.selectByComments", docsNo);
			System.out.println(list);
			if(list.size()==0) {
				throw new FindException("코멘트 내용이  없습니다.");
			}

			return list;
		}catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
//
//	/**
//	 * (전체)문서에 대해 제목으로 검색할 수 있다.
//	 * 
//	 * @param employee_id
//	 * @param title
//	 * @return 검색 내용
//	 * @throws FindException
//	 * @throws SearchException
//	 */
//	@Override
//	public List<Document> selectBySearchTitle(String employee_id, String title) throws FindException, SearchException {
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("db연동 실패");
//			throw new FindException(e.getMessage());
//		}
//		int check = 0;
//
//		String sql = "";
//		sql += "SELECT state,j.document_no, j.document_title,j.document_content, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type,ap_type\r\n"
//				+ "from employee e join ( \r\n" + "SELECT * FROM (select a.*\r\n"
//				+ "FROM ((SELECT '결재서류' state, d.document_title,d.document_content, d.document_no, draft_date, d.employee_id,d.document_type,a.ap_type\r\n"
//				+ "FROM approval a JOIN document d ON a.document_no=d.document_no \r\n" + "WHERE a.employee_id=?)\r\n"
//				+ "UNION \r\n"
//				+ "(SELECT '결재서류', d.document_title,d.document_content, d.document_no, draft_date, d.employee_id, d.document_type,ag.ap_type\r\n"
//				+ "FROM agreement ag JOIN document d ON ag.document_no=d.document_no \r\n"
//				+ "WHERE ag.employee_id=?)\r\n" + "UNION \r\n"
//				+ "(SELECT '결재서류',d.document_title,d.document_content,d.document_no, draft_date, d.employee_id,d.document_type,r.ap_type\r\n"
//				+ "FROM reference r JOIN document d ON r.document_no=d.document_no \r\n" + "WHERE r.employee_id=?)\r\n"
//				+ "UNION\r\n"
//				+ "(SELECT '기안서류', document_title,d.document_content,document_no, draft_date, employee_id,document_type,'확인'\r\n"
//				+ "FROM document d  \r\n" + "WHERE employee_id=?))a\r\n"
//				+ "JOIN document d ON a.document_no= d.document_no)) j ON e.employee_id = j.employee_id where document_title like ? ORDER BY draft_date ASC";
//
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List list = new ArrayList<>();
//		try {
//			int cnt = 0;
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, employee_id);
//			pstmt.setString(2, employee_id);
//			pstmt.setString(3, employee_id);
//			pstmt.setString(4, employee_id);
//			pstmt.setString(5, "%" + title + "%");
//
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Document d = new Document();
//				Employee emp = new Employee();
//				DocumentType dt = new DocumentType();
//				Approval a = new Approval();
//				ApprovalStatus ap = new ApprovalStatus();
//
//				d.setState(rs.getString("state"));
//				d.setDocument_no(rs.getString("document_no"));
//				d.setDocument_title(rs.getString("document_title"));
//				emp.setEmployee_id(rs.getString("employee_id"));
//				emp.setName(rs.getString("name"));
//				d.setEmployee(emp);
//				d.setDraft_date(rs.getDate("dt"));
//				dt.setDocument_type(rs.getString("document_type"));
//				d.setDocument_type(dt);
//				String s = rs.getString("ap_type");
//				ap.setApStatus_type(rs.getString("ap_type"));
//				a.setAp_type(ap);
//				d.setApproval(a);
//
//				list.add(d);
//
//			}
//
//			if (list.size() == 0) {
//				throw new FindException("검색 목록이 존재하지 않습니다.");
//			}
//
//			return list;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//	}
//
//	/**
//	 * (전체)문서에 대해 내용으로 검색할 수 있다.
//	 * 
//	 * @param employee_id
//	 * @param content
//	 * @return title
//	 * @throws FindException
//	 * @throws SearchException
//	 */
//	@Override
//	public List<Document> selectBySearchContent(String employee_id, String content)
//			throws FindException, SearchException {
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("db연동 실패");
//			throw new FindException(e.getMessage());
//		}
//		int check = 0;
//
//		String sql = "";
//		sql += "SELECT state,j.document_no, j.document_title,j.document_content, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type,ap_type\r\n"
//				+ "from employee e join ( \r\n" + "SELECT * FROM (select a.*\r\n"
//				+ "FROM ((SELECT '결재서류' state, d.document_title,d.document_content, d.document_no, draft_date, d.employee_id,d.document_type,a.ap_type\r\n"
//				+ "FROM approval a JOIN document d ON a.document_no=d.document_no \r\n" + "WHERE a.employee_id=?)\r\n"
//				+ "UNION \r\n"
//				+ "(SELECT '결재서류', d.document_title,d.document_content, d.document_no, draft_date, d.employee_id, d.document_type,ag.ap_type\r\n"
//				+ "FROM agreement ag JOIN document d ON ag.document_no=d.document_no \r\n"
//				+ "WHERE ag.employee_id=?)\r\n" + "UNION \r\n"
//				+ "(SELECT '결재서류',d.document_title,d.document_content,d.document_no, draft_date, d.employee_id,d.document_type,r.ap_type\r\n"
//				+ "FROM reference r JOIN document d ON r.document_no=d.document_no \r\n" + "WHERE r.employee_id=?)\r\n"
//				+ "UNION\r\n"
//				+ "(SELECT '기안서류', document_title,d.document_content,document_no, draft_date, employee_id,document_type,'확인'\r\n"
//				+ "FROM document d  \r\n" + "WHERE employee_id=?))a\r\n"
//				+ "JOIN document d ON a.document_no= d.document_no)) j ON e.employee_id = j.employee_id where document_content like ? ORDER BY draft_date ASC";
//
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List list = new ArrayList<>();
//		try {
//			int cnt = 0;
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, employee_id);
//			pstmt.setString(2, employee_id);
//			pstmt.setString(3, employee_id);
//			pstmt.setString(4, employee_id);
//			pstmt.setString(5, "%" + content + "%");
//
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Document d = new Document();
//				Employee emp = new Employee();
//				DocumentType dt = new DocumentType();
//				Approval a = new Approval();
//				ApprovalStatus ap = new ApprovalStatus();
//
//				d.setState(rs.getString("state"));
//				d.setDocument_no(rs.getString("document_no"));
//				d.setDocument_title(rs.getString("document_title"));
//				emp.setEmployee_id(rs.getString("employee_id"));
//				emp.setName(rs.getString("name"));
//				d.setEmployee(emp);
//				d.setDraft_date(rs.getDate("dt"));
//				dt.setDocument_type(rs.getString("document_type"));
//				d.setDocument_type(dt);
//				String s = rs.getString("ap_type");
//				ap.setApStatus_type(rs.getString("ap_type"));
//				a.setAp_type(ap);
//				d.setApproval(a);
//
//				list.add(d);
//
//			}
//
//			if (list.size() == 0) {
//				throw new FindException("검색 목록이 존재하지 않습니다.");
//			}
//
//			return list;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//	}
//
//	/**
//	 * (대기)문서에 대해 제목으로 검색할 수 있다.
//	 * 
//	 * @param employee_id
//	 * @param title
//	 * @return 검색 내용
//	 * @throws FindException
//	 * @throws SearchException
//	 */
//	@Override
//	public List<Document> selectBySearchTitleWait(String employee_id, String title)
//			throws FindException, SearchException {
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("db연동 실패");
//			throw new FindException(e.getMessage());
//		}
//		int check = 0;
//
//		String sql = "";
//		sql += "SELECT state,j.document_no, j.document_title,j.document_content, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type,ap_type\r\n"
//				+ "from employee e join ( \r\n" + "SELECT * FROM (select a.*\r\n"
//				+ "FROM ((SELECT '결재서류' state, d.document_title,d.document_content, d.document_no, draft_date, d.employee_id,d.document_type,a.ap_type\r\n"
//				+ "FROM approval a JOIN document d ON a.document_no=d.document_no \r\n" + "WHERE a.employee_id=?)\r\n"
//				+ "UNION \r\n"
//				+ "(SELECT '결재서류', d.document_title,d.document_content, d.document_no, draft_date, d.employee_id, d.document_type,ag.ap_type\r\n"
//				+ "FROM agreement ag JOIN document d ON ag.document_no=d.document_no \r\n"
//				+ "WHERE ag.employee_id=?)\r\n" + "UNION \r\n"
//				+ "(SELECT '결재서류',d.document_title,d.document_content,d.document_no, draft_date, d.employee_id,d.document_type,r.ap_type\r\n"
//				+ "FROM reference r JOIN document d ON r.document_no=d.document_no \r\n" + "WHERE r.employee_id=?)\r\n"
//				+ "UNION\r\n"
//				+ "(SELECT '기안서류', document_title,d.document_content,document_no, draft_date, employee_id,document_type,'확인'\r\n"
//				+ "FROM document d  \r\n" + "WHERE employee_id=?))a\r\n"
//				+ "JOIN document d ON a.document_no= d.document_no WHERE document_status='대기')) j ON e.employee_id = j.employee_id where document_title like ? ORDER BY draft_date ASC";
//
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List list = new ArrayList<>();
//		try {
//			int cnt = 0;
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, employee_id);
//			pstmt.setString(2, employee_id);
//			pstmt.setString(3, employee_id);
//			pstmt.setString(4, employee_id);
//			pstmt.setString(5, "%" + title + "%");
//
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Document d = new Document();
//				Employee emp = new Employee();
//				DocumentType dt = new DocumentType();
//				Approval a = new Approval();
//				ApprovalStatus ap = new ApprovalStatus();
//
//				d.setState(rs.getString("state"));
//				d.setDocument_no(rs.getString("document_no"));
//				d.setDocument_title(rs.getString("document_title"));
//				emp.setEmployee_id(rs.getString("employee_id"));
//				emp.setName(rs.getString("name"));
//				d.setEmployee(emp);
//				d.setDraft_date(rs.getDate("dt"));
//				dt.setDocument_type(rs.getString("document_type"));
//				d.setDocument_type(dt);
//				String s = rs.getString("ap_type");
//				ap.setApStatus_type(rs.getString("ap_type"));
//				a.setAp_type(ap);
//				d.setApproval(a);
//
//				list.add(d);
//
//			}
//
//			if (list.size() == 0) {
//				throw new FindException("검색 목록이 존재하지 않습니다.");
//			}
//
//			return list;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//	}
//
//	/**
//	 * (대기)문서에 대해 내용으로 검색할 수 있다.
//	 * 
//	 * @param employee_id
//	 * @param content
//	 * @return title
//	 * @throws FindException
//	 * @throws SearchException
//	 */
//	@Override
//	public List<Document> selectBySearchContentWait(String employee_id, String content)
//			throws FindException, SearchException {
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("db연동 실패");
//			throw new FindException(e.getMessage());
//		}
//		int check = 0;
//
//		String sql = "";
//		sql += "SELECT state,j.document_no, j.document_title,j.document_content, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type,ap_type\r\n"
//				+ "from employee e join ( \r\n" + "SELECT * FROM (select a.*\r\n"
//				+ "FROM ((SELECT '결재서류' state, d.document_title,d.document_content, d.document_no, draft_date, d.employee_id,d.document_type,a.ap_type\r\n"
//				+ "FROM approval a JOIN document d ON a.document_no=d.document_no \r\n" + "WHERE a.employee_id=?)\r\n"
//				+ "UNION \r\n"
//				+ "(SELECT '결재서류', d.document_title,d.document_content, d.document_no, draft_date, d.employee_id, d.document_type,ag.ap_type\r\n"
//				+ "FROM agreement ag JOIN document d ON ag.document_no=d.document_no \r\n"
//				+ "WHERE ag.employee_id=?)\r\n" + "UNION \r\n"
//				+ "(SELECT '결재서류',d.document_title,d.document_content,d.document_no, draft_date, d.employee_id,d.document_type,r.ap_type\r\n"
//				+ "FROM reference r JOIN document d ON r.document_no=d.document_no \r\n" + "WHERE r.employee_id=?)\r\n"
//				+ "UNION\r\n"
//				+ "(SELECT '기안서류', document_title,d.document_content,document_no, draft_date, employee_id,document_type,'확인'\r\n"
//				+ "FROM document d  \r\n" + "WHERE employee_id=?))a\r\n"
//				+ "JOIN document d ON a.document_no= d.document_no WHERE document_status='대기')) j ON e.employee_id = j.employee_id where document_content like ? ORDER BY draft_date ASC";
//
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List list = new ArrayList<>();
//		try {
//			int cnt = 0;
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, employee_id);
//			pstmt.setString(2, employee_id);
//			pstmt.setString(3, employee_id);
//			pstmt.setString(4, employee_id);
//			pstmt.setString(5, "%" + content + "%");
//
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Document d = new Document();
//				Employee emp = new Employee();
//				DocumentType dt = new DocumentType();
//				Approval a = new Approval();
//				ApprovalStatus ap = new ApprovalStatus();
//
//				d.setState(rs.getString("state"));
//				d.setDocument_no(rs.getString("document_no"));
//				d.setDocument_title(rs.getString("document_title"));
//				emp.setEmployee_id(rs.getString("employee_id"));
//				emp.setName(rs.getString("name"));
//				d.setEmployee(emp);
//				d.setDraft_date(rs.getDate("dt"));
//				dt.setDocument_type(rs.getString("document_type"));
//				d.setDocument_type(dt);
//				String s = rs.getString("ap_type");
//				ap.setApStatus_type(rs.getString("ap_type"));
//				a.setAp_type(ap);
//				d.setApproval(a);
//
//				list.add(d);
//
//			}
//
//			if (list.size() == 0) {
//				throw new FindException("검색 목록이 존재하지 않습니다.");
//			}
//
//			return list;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//	}
//
//	/**
//	 * (반려)문서에 대해 제목으로 검색할 수 있다.
//	 * 
//	 * @param employee_id
//	 * @param title
//	 * @return 검색 내용
//	 * @throws FindException
//	 * @throws SearchException
//	 */
//	@Override
//	public List<Document> selectBySearchTitleNo(String employee_id, String title)
//			throws FindException, SearchException {
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("db연동 실패");
//			throw new FindException(e.getMessage());
//		}
//		int check = 0;
//
//		String sql = "";
//		sql += "SELECT state,j.document_no, j.document_title,j.document_content, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type,ap_type\r\n"
//				+ "from employee e join ( \r\n" + "SELECT * FROM (select a.*\r\n"
//				+ "FROM ((SELECT '결재서류' state, d.document_title,d.document_content, d.document_no, draft_date, d.employee_id,d.document_type,a.ap_type\r\n"
//				+ "FROM approval a JOIN document d ON a.document_no=d.document_no \r\n" + "WHERE a.employee_id=?)\r\n"
//				+ "UNION \r\n"
//				+ "(SELECT '결재서류', d.document_title,d.document_content, d.document_no, draft_date, d.employee_id, d.document_type,ag.ap_type\r\n"
//				+ "FROM agreement ag JOIN document d ON ag.document_no=d.document_no \r\n"
//				+ "WHERE ag.employee_id=?)\r\n" + "UNION \r\n"
//				+ "(SELECT '결재서류',d.document_title,d.document_content,d.document_no, draft_date, d.employee_id,d.document_type,r.ap_type\r\n"
//				+ "FROM reference r JOIN document d ON r.document_no=d.document_no \r\n" + "WHERE r.employee_id=?)\r\n"
//				+ "UNION\r\n"
//				+ "(SELECT '기안서류', document_title,d.document_content,document_no, draft_date, employee_id,document_type,'확인'\r\n"
//				+ "FROM document d  \r\n" + "WHERE employee_id=?))a\r\n"
//				+ "JOIN document d ON a.document_no= d.document_no WHERE document_status='반려')) j ON e.employee_id = j.employee_id where document_title like ? ORDER BY draft_date ASC";
//
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List list = new ArrayList<>();
//		try {
//			int cnt = 0;
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, employee_id);
//			pstmt.setString(2, employee_id);
//			pstmt.setString(3, employee_id);
//			pstmt.setString(4, employee_id);
//			pstmt.setString(5, "%" + title + "%");
//
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Document d = new Document();
//				Employee emp = new Employee();
//				DocumentType dt = new DocumentType();
//				Approval a = new Approval();
//				ApprovalStatus ap = new ApprovalStatus();
//
//				d.setState(rs.getString("state"));
//				d.setDocument_no(rs.getString("document_no"));
//				d.setDocument_title(rs.getString("document_title"));
//				emp.setEmployee_id(rs.getString("employee_id"));
//				emp.setName(rs.getString("name"));
//				d.setEmployee(emp);
//				d.setDraft_date(rs.getDate("dt"));
//				dt.setDocument_type(rs.getString("document_type"));
//				d.setDocument_type(dt);
//				String s = rs.getString("ap_type");
//				ap.setApStatus_type(rs.getString("ap_type"));
//				a.setAp_type(ap);
//				d.setApproval(a);
//
//				list.add(d);
//
//			}
//
//			if (list.size() == 0) {
//				throw new FindException("검색 목록이 존재하지 않습니다.");
//			}
//
//			return list;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//	}
//
//	/**
//	 * (반려)문서에 대해 내용으로 검색할 수 있다.
//	 * 
//	 * @param employee_id
//	 * @param content
//	 * @return title
//	 * @throws FindException
//	 * @throws SearchException
//	 */
//	@Override
//	public List<Document> selectBySearchContentNo(String employee_id, String content)
//			throws FindException, SearchException {
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("db연동 실패");
//			throw new FindException(e.getMessage());
//		}
//		int check = 0;
//
//		String sql = "";
//		sql += "SELECT state,j.document_no, j.document_title,j.document_content, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type,ap_type\r\n"
//				+ "from employee e join ( \r\n" + "SELECT * FROM (select a.*\r\n"
//				+ "FROM ((SELECT '결재서류' state, d.document_title,d.document_content, d.document_no, draft_date, d.employee_id,d.document_type,a.ap_type\r\n"
//				+ "FROM approval a JOIN document d ON a.document_no=d.document_no \r\n" + "WHERE a.employee_id=?)\r\n"
//				+ "UNION \r\n"
//				+ "(SELECT '결재서류', d.document_title,d.document_content, d.document_no, draft_date, d.employee_id, d.document_type,ag.ap_type\r\n"
//				+ "FROM agreement ag JOIN document d ON ag.document_no=d.document_no \r\n"
//				+ "WHERE ag.employee_id=?)\r\n" + "UNION \r\n"
//				+ "(SELECT '결재서류',d.document_title,d.document_content,d.document_no, draft_date, d.employee_id,d.document_type,r.ap_type\r\n"
//				+ "FROM reference r JOIN document d ON r.document_no=d.document_no \r\n" + "WHERE r.employee_id=?)\r\n"
//				+ "UNION\r\n"
//				+ "(SELECT '기안서류', document_title,d.document_content,document_no, draft_date, employee_id,document_type,'확인'\r\n"
//				+ "FROM document d  \r\n" + "WHERE employee_id=?))a\r\n"
//				+ "JOIN document d ON a.document_no= d.document_no WHERE document_status='반려')) j ON e.employee_id = j.employee_id where document_content like ? ORDER BY draft_date ASC";
//
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List list = new ArrayList<>();
//		try {
//			int cnt = 0;
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, employee_id);
//			pstmt.setString(2, employee_id);
//			pstmt.setString(3, employee_id);
//			pstmt.setString(4, employee_id);
//			pstmt.setString(5, "%" + content + "%");
//
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Document d = new Document();
//				Employee emp = new Employee();
//				DocumentType dt = new DocumentType();
//				Approval a = new Approval();
//				ApprovalStatus ap = new ApprovalStatus();
//
//				d.setState(rs.getString("state"));
//				d.setDocument_no(rs.getString("document_no"));
//				d.setDocument_title(rs.getString("document_title"));
//				emp.setEmployee_id(rs.getString("employee_id"));
//				emp.setName(rs.getString("name"));
//				d.setEmployee(emp);
//				d.setDraft_date(rs.getDate("dt"));
//				dt.setDocument_type(rs.getString("document_type"));
//				d.setDocument_type(dt);
//				String s = rs.getString("ap_type");
//				ap.setApStatus_type(rs.getString("ap_type"));
//				a.setAp_type(ap);
//				d.setApproval(a);
//
//				list.add(d);
//
//			}
//
//			if (list.size() == 0) {
//				throw new FindException("검색 목록이 존재하지 않습니다.");
//			}
//
//			return list;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//	}
//
//	/**
//	 * (승인)문서에 대해 제목으로 검색할 수 있다.
//	 * 
//	 * @param employee_id
//	 * @param title
//	 * @return 검색 내용
//	 * @throws FindException
//	 * @throws SearchException
//	 */
//	@Override
//	public List<Document> selectBySearchTitleOk(String employee_id, String title)
//			throws FindException, SearchException {
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("db연동 실패");
//			throw new FindException(e.getMessage());
//		}
//		int check = 0;
//
//		String sql = "";
//		sql += "SELECT state,j.document_no, j.document_title,j.document_content, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type,ap_type\r\n"
//				+ "from employee e join ( \r\n" + "SELECT * FROM (select a.*\r\n"
//				+ "FROM ((SELECT '결재서류' state, d.document_title,d.document_content, d.document_no, draft_date, d.employee_id,d.document_type,a.ap_type\r\n"
//				+ "FROM approval a JOIN document d ON a.document_no=d.document_no \r\n" + "WHERE a.employee_id=?)\r\n"
//				+ "UNION \r\n"
//				+ "(SELECT '결재서류', d.document_title,d.document_content, d.document_no, draft_date, d.employee_id, d.document_type,ag.ap_type\r\n"
//				+ "FROM agreement ag JOIN document d ON ag.document_no=d.document_no \r\n"
//				+ "WHERE ag.employee_id=?)\r\n" + "UNION \r\n"
//				+ "(SELECT '결재서류',d.document_title,d.document_content,d.document_no, draft_date, d.employee_id,d.document_type,r.ap_type\r\n"
//				+ "FROM reference r JOIN document d ON r.document_no=d.document_no \r\n" + "WHERE r.employee_id=?)\r\n"
//				+ "UNION\r\n"
//				+ "(SELECT '기안서류', document_title,d.document_content,document_no, draft_date, employee_id,document_type,'확인'\r\n"
//				+ "FROM document d  \r\n" + "WHERE employee_id=?))a\r\n"
//				+ "JOIN document d ON a.document_no= d.document_no WHERE document_status='승인')) j ON e.employee_id = j.employee_id where document_title like ? ORDER BY draft_date ASC";
//
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List list = new ArrayList<>();
//		try {
//			int cnt = 0;
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, employee_id);
//			pstmt.setString(2, employee_id);
//			pstmt.setString(3, employee_id);
//			pstmt.setString(4, employee_id);
//			pstmt.setString(5, "%" + title + "%");
//
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Document d = new Document();
//				Employee emp = new Employee();
//				DocumentType dt = new DocumentType();
//				Approval a = new Approval();
//				ApprovalStatus ap = new ApprovalStatus();
//
//				d.setState(rs.getString("state"));
//				d.setDocument_no(rs.getString("document_no"));
//				d.setDocument_title(rs.getString("document_title"));
//				emp.setEmployee_id(rs.getString("employee_id"));
//				emp.setName(rs.getString("name"));
//				d.setEmployee(emp);
//				d.setDraft_date(rs.getDate("dt"));
//				dt.setDocument_type(rs.getString("document_type"));
//				d.setDocument_type(dt);
//				String s = rs.getString("ap_type");
//				ap.setApStatus_type(rs.getString("ap_type"));
//				a.setAp_type(ap);
//				d.setApproval(a);
//
//				list.add(d);
//
//			}
//
//			if (list.size() == 0) {
//				throw new FindException("검색 목록이 존재하지 않습니다.");
//			}
//
//			return list;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//	}
//
//	/**
//	 * (승인)문서에 대해 내용으로 검색할 수 있다.
//	 * 
//	 * @param employee_id
//	 * @param content
//	 * @return title
//	 * @throws FindException
//	 * @throws SearchException
//	 */
//	@Override
//	public List<Document> selectBySearchContentOk(String employee_id, String content)
//			throws FindException, SearchException {
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("db연동 실패");
//			throw new FindException(e.getMessage());
//		}
//		int check = 0;
//
//		String sql = "";
//		sql += "SELECT state,j.document_no, j.document_title,j.document_content, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type,ap_type\r\n"
//				+ "from employee e join ( \r\n" + "SELECT * FROM (select a.*\r\n"
//				+ "FROM ((SELECT '결재서류' state, d.document_title,d.document_content, d.document_no, draft_date, d.employee_id,d.document_type,a.ap_type\r\n"
//				+ "FROM approval a JOIN document d ON a.document_no=d.document_no \r\n" + "WHERE a.employee_id=?)\r\n"
//				+ "UNION \r\n"
//				+ "(SELECT '결재서류', d.document_title,d.document_content, d.document_no, draft_date, d.employee_id, d.document_type,ag.ap_type\r\n"
//				+ "FROM agreement ag JOIN document d ON ag.document_no=d.document_no \r\n"
//				+ "WHERE ag.employee_id=?)\r\n" + "UNION \r\n"
//				+ "(SELECT '결재서류',d.document_title,d.document_content,d.document_no, draft_date, d.employee_id,d.document_type,r.ap_type\r\n"
//				+ "FROM reference r JOIN document d ON r.document_no=d.document_no \r\n" + "WHERE r.employee_id=?)\r\n"
//				+ "UNION\r\n"
//				+ "(SELECT '기안서류', document_title,d.document_content,document_no, draft_date, employee_id,document_type,'확인'\r\n"
//				+ "FROM document d  \r\n" + "WHERE employee_id=?))a\r\n"
//				+ "JOIN document d ON a.document_no= d.document_no WHERE document_status='승인')) j ON e.employee_id = j.employee_id where document_content like ? ORDER BY draft_date ASC";
//
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List list = new ArrayList<>();
//		try {
//			int cnt = 0;
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, employee_id);
//			pstmt.setString(2, employee_id);
//			pstmt.setString(3, employee_id);
//			pstmt.setString(4, employee_id);
//			pstmt.setString(5, "%" + content + "%");
//
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Document d = new Document();
//				Employee emp = new Employee();
//				DocumentType dt = new DocumentType();
//				Approval a = new Approval();
//				ApprovalStatus ap = new ApprovalStatus();
//
//				d.setState(rs.getString("state"));
//				d.setDocument_no(rs.getString("document_no"));
//				d.setDocument_title(rs.getString("document_title"));
//				emp.setEmployee_id(rs.getString("employee_id"));
//				emp.setName(rs.getString("name"));
//				d.setEmployee(emp);
//				d.setDraft_date(rs.getDate("dt"));
//				dt.setDocument_type(rs.getString("document_type"));
//				d.setDocument_type(dt);
//				String s = rs.getString("ap_type");
//				ap.setApStatus_type(rs.getString("ap_type"));
//				a.setAp_type(ap);
//				d.setApproval(a);
//
//				list.add(d);
//
//			}
//
//			if (list.size() == 0) {
//				throw new FindException("검색 목록이 존재하지 않습니다.");
//			}
//
//			return list;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//	}

	@Override
	public List<Document> selectBySearch(String employee_id, String title) throws FindException, SearchException {
		// TODO Auto-generated method stub
		return null;
	}

}
