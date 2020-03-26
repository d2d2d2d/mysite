package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String no = request.getParameter("no");
		String gNo = request.getParameter("gNo");
		String oNo = request.getParameter("oNo");
		String dept = request.getParameter("dept");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		Long userNo = ((UserVo)request.getSession().getAttribute("authUser")).getNo();
		
		BoardVo BoardVo = new BoardVo();
		
		BoardVo.setNo(Long.parseLong(no));
		BoardVo.setgNo(Integer.parseInt(gNo));
		BoardVo.setoNo(Integer.parseInt(oNo));
		BoardVo.setDept(Integer.parseInt(dept));
		BoardVo.setTitle(title);
		BoardVo.setContents(contents);
		BoardVo.setUserNo(userNo);
		
		new BoardRepository().replyUpdate(BoardVo);
		new BoardRepository().replyInsert(BoardVo);
		
		WebUtil.redirect(request.getContextPath() + "/board?a=list", request, response);
	}

}
