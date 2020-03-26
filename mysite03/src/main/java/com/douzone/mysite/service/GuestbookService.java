package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	@Autowired
	private GuestbookRepository guestbookRepository;

	public boolean insert(GuestbookVo vo) {
		int count = guestbookRepository.insert(vo);
		return count == 1;
	}

	public List<GuestbookVo> getList() {
		return guestbookRepository.getList();
	}

	public int delete(long no, String password) {
	//	String Password = guestbookRepository.findPasswordByNo(no);
		return guestbookRepository.delete(no, password);
	}

}