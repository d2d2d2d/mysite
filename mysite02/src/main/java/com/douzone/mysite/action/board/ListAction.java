package com.douzone.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String strPg = request.getParameter("pg");
		int pg = 1; //페이지 , list.jsp로 넘어온 경우 , 초기값 =1
		int block = 5; //한페이지에 보여줄  범위 << [1] [2] [3] [4] [5] >>
		if(strPg != null){ //list.jsp?pg=2
			pg = Integer.parseInt(strPg); //.저장
		}

		int rowSize = 5; //한페이지에 보여줄 글의 수
		int total = new BoardRepository().getTotalBoard(); //총 게시물 수
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

		List<BoardVo> list = new BoardRepository().paging(from, rowSize);	
		
		String kwd = request.getParameter("kwd");
		if(kwd != null) {
			list = new BoardRepository().search(kwd, from, rowSize);
		}

		request.setAttribute("list", list);
		request.setAttribute("pg", pg);
		request.setAttribute("fromPage", fromPage);
		request.setAttribute("toPage", toPage);

		WebUtil.forward("/WEB-INF/views/board/list.jsp", request, response);
	}

}
