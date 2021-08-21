package com.group.approval.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.approval.dao.ConfirmDocsDAO;
import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.exception.FindException;
import com.group.exception.SearchException;

@Service
public class ConfirmDocsService {
	@Autowired
   private ConfirmDocsDAO dao;

   
   /**
    * (전체/승인/대기/반려)사용자는 확인/미확인 문서를 선택해서 볼 수 있다. 
    * @param id 로그인한 사용자 id
    * @param status 문서 상태값
    * @param  check 문서 확인/미확인 값 
    * @return 사용자가 선택한 결과값 목록
    * @throws FindException
    */   
	public List<Document> findCheckDocs(String id,String check) throws FindException{
		return dao.selectByCheckAll(id,check);
	}
	public List<Document> findCheckDocs(String id,String check,String status) throws FindException{
      return dao.selectByCheckStatus(id,check,status);
   }

   /**
    * 사용자는 문서를 선택하면,해당 문서에서 자신이 승인해야하는 부분을 확인할 수 있다.
    * @param id 로그인한 사용자 id
    * @param docsNo 클릭한 문서 번호 
    * @throws FindException
    */
   public List<Approval> findDocsMyCheck(String id, String docsNo) throws FindException{
      return dao.selectByMyClick(id, docsNo);
   };

   
   /**
    * 해당 문서의 상세 내용정보를 확인할 수 있다. (내용+결재선)
    * @param docsNo 선택한 문서번호
    * @return 문서 상세 내용 + 결재선 정보
    * @throws FindExceptionb
    */
   public Document findDocsDetail(String docsNo) throws FindException{      
      return dao.selectByDocsDetail(docsNo);
   }
   
   
   /**
    * 해당 문서의 코멘트를 확인할 수 있다. 
    * @param docsNo 선택한 문서번호
    * @return 코멘트 내용
    * @throws FindException
    */
    public List<Approval> findByComments(String docNo) throws FindException{
       return dao.selectByComments(docNo);
    }
   

   /**
    * (전체/대기/반려/승인)문서에 대해 제목/내용으로 검색할 수 있다.
    * @param id 로그인한 사용자 id
    * @param searchType title/content구분값 
    * @param search 검색시 입력값
    * @param status 사이드바 전체 / 대기 / 승인 / 반려 값 
    * @return 사용자가 입력한 검색어에 일치하는 목록 
    * @throws SearchException 
    */   
    
   public List<Document> findMySearch(String id,String searchType,String search,String status) throws SearchException{
	   return dao.selectBySearchStatus(id, searchType, search, status);   
   }
   public List<Document> findMySearch(String id,String searchType,String search) throws SearchException{
	   return dao.selectBySearchAll(id, searchType, search);   
   }
//   
//   /**6.
//    * 문서에 대해 내용으로 검색할 수 있다.
//    * @param id 로그인한 사용자 id
//    * @param content 내용 검색시 입력값
//    * @param 사이드바 전체 / 대기 / 승인 / 반려 값
//    * @return 사용자가 입력한 검색어에 일치하는 목록 
//    * @throws FindException
//    * @throws SearchException 
//    */ 
//   public List<Document> findMySearchContent(String id,String content,String status) throws FindException, SearchException{
//      List<Document> lists=null;
//      if(status.equals("")) {
//         lists=dao.selectBySearchContent(id, content);
//      }else if(status.equals("대기")){
//         lists=dao.selectBySearchContentWait(id, content);
//      }else if(status.equals("승인")){
//         lists=dao.selectBySearchContentOk(id, content);
//      }else if(status.equals("반려")) {
//         lists=dao.selectBySearchContentNo(id, content);
//      }
//      return lists;
//   }
//   
 

}