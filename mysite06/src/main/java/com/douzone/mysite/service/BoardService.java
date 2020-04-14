package com.douzone.mysite.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

//	public List<BoardVo> getList() {
//		return boardRepository.getList();
//	}
	
	public List<BoardVo> getList(int pg, String kwd) {
		int block = 5; //한페이지에 보여줄  범위 << [1] [2] [3] [4] [5] >>
		if(pg == 0){ //list.jsp?pg=2
			pg = 1;
		}

		int rowSize = 5; //한페이지에 보여줄 글의 수
		int total = boardRepository.getTotalBoard(); //총 게시물 수
		total = total / rowSize +1;	//5=rowsize

		if(pg < 1) {
			pg = 1;
		}
		else if(total < pg) {
			pg -= 1;
		}

		int fromPage = ((pg - 1) / block * block) + 1; //보여줄 페이지의 시작
		int toPage = ((pg - 1) / block * block) + block; //보여줄 페이지의 끝
		if(total < toPage){
			toPage = total;
		}
		int from = (pg * rowSize) - (rowSize-1) -1; //(1*5)-(5-1)=5-4=1 //from
		return boardRepository.paging(from, rowSize);
		
	}
	
	public boolean insert(BoardVo vo) {
		int count = boardRepository.insert(vo);
		return count == 1;
	}

	public BoardVo view(Long no) {
		BoardVo vo = boardRepository.findView(no);
		boardRepository.hitUpdate(vo);
		return vo;
	}

	public BoardVo findView(Long no) {
		BoardVo vo = boardRepository.findView(no);
		return vo;
	}
	
	public boolean modify(BoardVo vo) {
		int count = boardRepository.update(vo);
		return count == 1;
	}

	public boolean delete(Long no) {
		int count = boardRepository.delete(no);
		return count == 1;
	}

	public BoardVo reply(Long no) {
		BoardVo vo = boardRepository.findView(no);
		BoardVo vo2 = boardRepository.findView(no);
		vo.setTitle(vo2.getTitle());
		return vo;
	}

	public boolean reply(BoardVo vo) {
		int count = boardRepository.replyUpdate(vo);
		count += boardRepository.replyInsert(vo);
		return count == 2;
	}	
	
}