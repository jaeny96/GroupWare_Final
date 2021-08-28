package com.group.calendar.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.group.calendar.dto.Schedule;
import com.group.calendar.dto.ScheduleType;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.DuplicatedException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;

@Repository("scheduleDAO")
public class ScheduleDAOOracle implements ScheduleDAO {
	
	@Autowired
	private SqlSessionFactory sessionFactory;
	
	@Override
	public List<Schedule> skdList(Employee emp) throws FindException {
		SqlSession session = null;
		try {
		session = sessionFactory.openSession();
		List<Schedule> list = session.selectList("com.group.ScheduleMapper.SkdList", emp);
		if(list.size() == 0) {
			throw new FindException("일정이 없습니다.");
		}
		return list;
		}catch(Exception e){
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		
	}
	public List<Schedule> skdPeriodList(Employee emp, String sdate, String edate) throws FindException {
		SqlSession session = null;
		try {
		session = sessionFactory.openSession();
		HashMap<String, String>map = new HashMap<>();
		map.put("emp",emp.getEmployeeId());
		map.put("dpt", emp.getDepartment().getDepartmentId());
		map.put("sdate",sdate);
		map.put("edate",edate);
		List<Schedule> list = session.selectList("com.group.ScheduleMapper.skdPeriodList", map);
		if(list.size() == 0) {
			throw new FindException("일정이 없습니다.");
		}
		return list;	
		}catch(Exception e){
			throw new FindException(e.getMessage());
		}finally {
			session.close();
		}  
		
   
	   }
	public List<Schedule> skdByTeam(String skd_dpt_id) throws FindException {
		
		SqlSession session = null;
		try {
		session = sessionFactory.openSession();
		
		List<Schedule> list = session.selectList("com.group.ScheduleMapper.skdByTeam", skd_dpt_id);
		if(list.size() == 0) {
			throw new FindException("일정이 없습니다.");
		}
		return list;	
		}catch(Exception e){
			throw new FindException(e.getMessage());
		}finally {
			session.close();
		} 

		
	}
	public List<Schedule> skdPersonal(String skd_id) throws FindException {
		SqlSession session = null;
		try {
		session = sessionFactory.openSession();
		
		List<Schedule> list = session.selectList("com.group.ScheduleMapper.skdPersonal", skd_id);
		if(list.size() == 0) {
			throw new FindException("일정이 없습니다.");
		}
		return list;	
		}catch(Exception e){
			throw new FindException(e.getMessage());
		}finally {
			session.close();
		} 

	}
	public List<Schedule> skdByContent(Schedule s) throws FindException {
		SqlSession session = null;
		try {
		session = sessionFactory.openSession();
		
		List<Schedule> list = session.selectList("com.group.ScheduleMapper.skdByContent", s);
		if(list.size() == 0) {
			throw new FindException("일정이 없습니다.");
		}
		return list;	
		}catch(Exception e){
			throw new FindException(e.getMessage());
		}finally {
			session.close();
		} 

	}
	public Schedule skdDetail(int skd_no) throws FindException {
		SqlSession session = null;
		try {
		session = sessionFactory.openSession();
		
		Schedule s = session.selectOne("com.group.ScheduleMapper.skdDetail", skd_no);
		//System.out.println(s+"오라클");
		if(s==null) {
			throw new FindException("일정이 없습니다.");
		}
		return s;	
		}catch(Exception e){
			throw new FindException(e.getMessage());
		}finally {
			session.close();
		} 

		
	}
	
	   public void insert(Schedule s) throws AddException, DuplicatedException {
		   SqlSession session = null;
			try {
			session = sessionFactory.openSession();
			session.insert("com.group.ScheduleMapper.insert", s);
			}catch(Exception e) {
				throw new AddException(e.getMessage());
			}finally {
				if(session != null) session.close();
			}
			
	   }
	   
	   public void update(Schedule s) throws ModifyException {
		   SqlSession session = null;		
			try {
				session = sessionFactory.openSession();
				session.update("com.group.ScheduleMapper.update", s);
			}catch(Exception e) {
				e.printStackTrace();
				throw new ModifyException(e.getMessage());
			}finally {
				if(session != null) {
					session.close();
				}
			}
//	      Connection con = null;
//	      try {
//	            con = MyConnection.getConnection();
//	               con.setAutoCommit(false);
//	         } catch (SQLException e) {
//	            e.printStackTrace();
//	            throw new ModifyException("Connection 연결 오류");
//	         }
	      
	      
//	      String updateSQL = "UPDATE schedule SET ";
//	      String updateSQL1 = " WHERE skd_no = ? AND employee_id = ?";
//	      boolean flag = false;
//	      ScheduleType skd_type = s.getSkdType();
//	      String skd_title = s.getSkdTitle();
//	      String skd_content = s.getSkdContent();
//	      Timestamp skd_start_date = s.getSkdStartDate();
//	      Timestamp skd_end_date = s.getSkdEndDate();
//	      String skd_share = s.getSkdShare();
//	     
//
//	      if(!skd_type.getSkdType().equals("")) {
//	         updateSQL += "skd_type = '"+skd_type+"'" ;
//	         flag = true;
//	      }
//	      
//	      if(!skd_title.equals("")) {
//	         if(flag) {
//	            updateSQL +=",";
//	         }
//	         updateSQL += "skd_title = '"+skd_title+"'";
//	         flag = true;
//	      }
//	      
//	      if(!skd_content.equals("")) {
//	         if(flag) {
//	            updateSQL +=",";
//	         }
//	         updateSQL += "skd_content = '"+skd_content+"'";
//	         flag = true;
//	      }
//	      
//	      //시간 미변경 시 필요 조건 
//	      SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//	      String timeformat = time.format(skd_start_date);
//	      String timeformat2 = time.format(skd_end_date);
//	      
//	      //timestamp 미변경 시 코드 보충 필요... 
//	      if(!timeformat.equals("")) {
//	         if(flag) {
//	            updateSQL +=",";
//	         }
//	         updateSQL += "skd_start_date = '"+skd_start_date+"'";
//	         flag = true;
//	      }
//	      
//	      if(!timeformat2.equals("")) {
//	         if(flag) {
//	            updateSQL +=",";
//	         }
//	         updateSQL += "skd_end_date = '"+skd_end_date +"'";
//	         flag = true;
//	      }
//	      
//	      if(!skd_share.equals("")) {
//	         if(flag) {
//	            updateSQL +=",";
//	         }
//	         updateSQL += "skd_share = '"+skd_share+"'";
//	         flag = true;
//	      }
//	      
//
//	      System.out.println("변경된 내용 " + skd_type + skd_title + skd_content + skd_start_date + skd_end_date + skd_share);
//	      System.out.println(updateSQL+updateSQL1);
//	      
//	      //변경하지 않음 
//	      if(!flag) {
//	         throw new ModifyException("수정할 내용이 없습니다");
//	      }
	      
	      
//	      PreparedStatement pstmt = null;
//	      //?에 들어갈 변수들 
//	      int skd_no = s.getSkdNo();
//	      Employee skd_id = s.getSkdId();
//	      
//	      //end 시간이 start보다 빠르지 않도록 제약 설정
//	      String skd_start_datestr = new SimpleDateFormat("yyyyMMddHHmmss").format(skd_start_date);   
//	      String skd_end_datestr = new SimpleDateFormat("yyyyMMddHHmmss").format(skd_end_date);
//	      
//	         try {
//	            //end시간이 더 빠른 경우 없도록 하는 조건절 
//	            if(Double.parseDouble(skd_end_datestr)-Double.parseDouble(skd_start_datestr)>0) {
//	               
//	          //  pstmt = con.prepareStatement(updateSQL+updateSQL1);
//	            pstmt.setInt(1, skd_no);
//	            pstmt.setString(2, skd_id.getEmployeeId());
//	            
//	            int rowcnt = pstmt.executeUpdate();
//	            if(rowcnt ==1) {
//	               System.out.println("일정이 수정되었습니다");
//	            }else {
//	               System.out.println(updateSQL+updateSQL1);
//	               throw new ModifyException("일정이 수정되지 않았습니다");
//	            }
//	            }else {//end-start if문의 else 
//	               throw new ModifyException("정상적인 시간을 입력하세요");
//	            }//end start if 문의 닫기 괄호 
//	         } catch (SQLException e) {
//	            e.printStackTrace();
//	         } finally {
//	            if(pstmt != null) {
//	               try {
//	                  pstmt.close();
//	               } catch (SQLException e) {
//	                  e.printStackTrace();
//	               }
	            }
//	            if(con != null) {
//	               try {
//	                  con.close();
//	               } catch (SQLException e) {
//	                  e.printStackTrace();
//	               }
//	            }
	            
//	         }
//	      
//	   }
	   @Transactional(rollbackFor = RemoveException.class)
	   public void delete(Schedule s) throws RemoveException {
	      SqlSession session = null;
	      
	      try {
	           session = sessionFactory.openSession();
	           int rowcnt = session.delete("com.group.ScheduleMapper.delete",s);
	           if(rowcnt ==0) {
	        	   throw new RemoveException("일정을 삭제할 수 없습니다.");
	           }
	         } catch (Exception e) {
	        	 throw new RemoveException(e.getMessage());
	         }
	   }

}