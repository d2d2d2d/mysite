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

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		Long userNo = ((UserVo)request.getSession().getAttribute("authUser")).getNo();
		
		BoardVo BoardVo = new BoardVo();
		BoardVo.setTitle(title);
		BoardVo.setContents(contents);
		BoardVo.setUserNo(userNo);
		
		new BoardRepository().insert(BoardVo);
		
		WebUtil.redirect(request.getContextPath() + "/board?a=list", request, response);
	}

}
