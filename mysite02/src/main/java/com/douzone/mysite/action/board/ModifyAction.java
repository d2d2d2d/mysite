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

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String bNo = request.getParameter("no");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		
		BoardVo BoardVo = new BoardVo();
		Long no = Long.parseLong(bNo);
		BoardVo.setNo(no);
		BoardVo.setTitle(title);
		BoardVo.setContents(contents);
		
		new BoardRepository().update(BoardVo);
		
		WebUtil.redirect(request.getContextPath() + "/board?a=view&no="+no, request, response);
	}

}