package com.group.board.service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.board.dao.BoardDAO;
import com.group.board.dto.Board;
import com.group.board.dto.PageBean;
import com.group.exception.FindException;
@Service
public class PageBeanService {
	@Autowired
	private BoardDAO dao;
	
	/**
	 * 총 페이지수 조회
	 * @return
	 */
	public int selectTotalPage() {
		int totalPage=0;
		int temp=PageBean.CNT_PER_PAGE;
		try { 
			List<Board> bdList = dao.selectAll();
			if((bdList.size()%temp)!=0) {
				totalPage=(bdList.size()/temp)+1;
			}else {
				totalPage=(bdList.size()/temp);
			}
			
		} catch (FindException e) {
			e.printStackTrace();
		}
		
		return totalPage;
	}
	/**
	 * 페이지를 4개씩 묶음
	 * @param PageGroup
	 * @return
	 */
	public List<Integer> selectPageGroup(int PageGroup) {
		int totalPage=0;
		int totalGroupNum=0;
		int temp=PageBean.CNT_PER_PAGE;
		int group=PageBean.CNT_PER_PAGE_GROUP;
		List<Integer> list = new ArrayList<Integer>();
		int index=0;
		try { 
			List<Board> bdList = dao.selectAll();
			if((bdList.size()%temp)!=0) {
				totalPage=(bdList.size()/temp)+1;
			}else {
				totalPage=(bdList.size()/temp);
			}
			
			if((totalPage%group)!=0) {
				totalGroupNum=(totalPage/group)+1;	
			}else {
				totalGroupNum=(totalPage/group);
			}
			
			if(PageGroup==totalGroupNum) {
				if((totalPage%group)!=0) {
					index=(totalPage%group);
				}else {
					index=4;					
				}
			}else {
				index=4;
			}

			for(int i=0; i<index; i++) {
				list.add(((PageGroup-1)*group)+i+1);
			}

		} catch (FindException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
