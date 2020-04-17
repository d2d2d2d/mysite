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

	public List<GuestbookVo> getMessageList(Long startNo) {
		return guestbookRepository.findAll(startNo);
	}

	public boolean writeMessage(GuestbookVo vo) {
		int count = guestbookRepository.insert(vo);
		return count == 1;
	}

	public boolean deleteMessage(Long no, String password) {
		return 1 == guestbookRepository.delete( new GuestbookVo(no, password) );
	}

}