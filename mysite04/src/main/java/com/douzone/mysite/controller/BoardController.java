package com.douzone.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping({"", "/list"})
	public String index(
			@RequestParam(value="pg", required=true, defaultValue="1") Integer page,
			@RequestParam(value ="kwd", required =true, defaultValue = "") String keyword,
			Model model) {
		List<BoardVo> list = boardService.getList(page, keyword);
		model.addAttribute("list", list);
		return "board/list";
	}

	@RequestMapping({"/view/{no}"})
	public String view(@PathVariable("no") Long no, Model model) {
		BoardVo vo = boardService.view(no);
		model.addAttribute("vo", vo);
		return "board/view";
	}

	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String insert(@AuthUser UserVo authUser, BoardVo vo) {
		vo.setUserNo(authUser.getNo());
		boardService.insert(vo);
		return "redirect:/board/list";
	}
	
	@Auth
	@RequestMapping({"/modify/{no}"})
	public String modify(@AuthUser UserVo authUser, @PathVariable("no") Long no, Model model) {
		BoardVo vo = boardService.findView(no);
		model.addAttribute("vo", vo);
		return "board/modify";
	}
	@Auth
	@RequestMapping(value={"/modify/{no}"}, method=RequestMethod.POST)
	public String modify(@ModelAttribute BoardVo vo) {
		boardService.modify(vo);
		return "redirect:/board/view/{no}";
	}

	@Auth
	@RequestMapping({"/delete/{no}"})
	public String delete(@PathVariable("no") Long no) {
		boardService.delete(no);
		return "redirect:/board/list";
	}

	@Auth
	@RequestMapping({"/reply/{no}"})
	public String reply(@PathVariable("no") Long no, Model model) {
		BoardVo vo = boardService.reply(no);
		model.addAttribute("vo", vo);
		return "board/reply";
	}
	@RequestMapping(value={"/reply"}, method=RequestMethod.POST)
	public String reply(HttpServletRequest request, BoardVo vo) {
		String no = request.getParameter("no");
		String gNo = request.getParameter("gNo");
		String oNo = request.getParameter("oNo");
		String dept = request.getParameter("dept");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		Long userNo = ((UserVo)request.getSession().getAttribute("authUser")).getNo();
		
		vo = new BoardVo();
		
		vo.setNo(Long.parseLong(no));
		vo.setgNo(Integer.parseInt(gNo));
		vo.setoNo(Integer.parseInt(oNo));
		vo.setDept(Integer.parseInt(dept));
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUserNo(userNo);
		boardService.reply(vo);
		return "redirect:/board/list";
	}
	
//	@RequestMapping({"/search"})
//	public String search(HttpSession session, Model model) {
//		String kwd = (String) session.getAttribute("kwd");
//		List<BoardVo> list = boardService.search(kwd, from, rowSize);
//		model.addAttribute("list", list);
//		return "board/list";
//	}

}