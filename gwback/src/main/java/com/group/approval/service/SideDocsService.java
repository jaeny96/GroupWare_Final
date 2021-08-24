package com.group.approval.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.approval.dao.SideDocsDAO;
import com.group.approval.dto.Document;
import com.group.exception.FindException;

@Service
public class SideDocsService {

	@Autowired
   private SideDocsDAO dao;
   
   
   /**
    * (전체)좌측 사이드 바를 통해 모든 목록의 각각의 개수를 확인할 수 있다.
    * @param id 로그인한 사용자 id
    * @return 기안올린+결재 해야하는 총합 개수
    * @throws FindException
    * 
    */
   public int findCntAll(String id) throws FindException{
      return dao.selectByCountAll(id);
   }
   
   /**
    * (진행)좌측 사이드 바를 통해 대기 목록의 각각의 개수를 확인할 수 있다.
    * @param id 로그인한 사용자 id
    * @return 기안올린+결재 해야하는 대기인 개수
    * @throws FindException
    * 
    */
   public int findCntWait(String id) throws FindException{
      return dao.selectByCountWait(id);
   }
   /**
    * (승인)좌측 사이드 바를 통해 승인 목록의 각각의 개수를 확인할 수 있다.
    * @param id 로그인한 사용자 id
    * @return 기안올린+결재 해야하는 승인된 개수
    * @throws FindException
    * 
    */
   public int findCntOk(String id) throws FindException{
      return dao.selectByCountOk(id);
   }
   /**
    * (반려)좌측 사이드 바를 통해 반려 목록의 각각의 개수를 확인할 수 있다.
    * @param id 로그인한 사용자 id
    * @return 기안올린+결재 해야하는 반려된 개수
    * @throws FindException
    * 
    */
   public int findCntNo(String id) throws FindException{
      return dao.selectByCountNo(id);
   }
   
   /**
    * (전체)를 누르면 해당 페이지에  자신이 기안을 올린 문서와 결재해야하는 문서를 모두 가지고온다.
    * @param id
    * @return
    * @throws FindException
    */
   public List<Document> findDocs(String id) throws FindException{
      List<Document> lists=null;
      lists=dao.selectByListAll(id);
      System.out.println(lists.toString());
      return lists;
   }
   
   /**
    * (대기/승인/반려)를 누르면 해당 페이지에  자신이 기안을 올린 문서와 결재해야하는 문서를 모두 가지고온다.
    * @param id
    * @param status
    * @return
    * @throws FindException
    */
   public List<Document> findDocsStatus(String id,String status) throws FindException{
      List<Document> lists=null;
      lists=dao.selectByListStatus(id,status);
      System.out.println(lists.toString());
      return lists;
   }

   
}