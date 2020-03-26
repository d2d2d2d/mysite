package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class ReplyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String bNo = request.getParameter("no");
		Long no = Long.parseLong(bNo);
		
		BoardVo vo = new BoardRepository().findNo(no);
		BoardVo vo2 =new BoardRepository().findView(no);
		vo.setTitle(vo2.getTitle());	//[re:
		//vo.setNo(no);
		
		request.setAttribute("vo", vo);
		
		WebUtil.forward("/WEB-INF/views/board/replyform.jsp", request, response);
	}

}
