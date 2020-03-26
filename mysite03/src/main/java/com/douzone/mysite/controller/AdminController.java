package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.AdminService;
import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.security.Auth;

@Auth("ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private AdminService adminService;

	@RequestMapping("")
	public String main() {
		return "admin/main";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}

	@RequestMapping(value="/main/update", method=RequestMethod.POST)
	public String update(
			@RequestParam(value="title", required=true, defaultValue="") String title,
			@RequestParam(value="welcomeMessage", required=true, defaultValue="") String welcomeMessage,
			@RequestParam(value="description", required=true, defaultValue="") String description,
			@RequestParam(value="file1") MultipartFile multipartFile,
			Model model){

		String url = fileUploadService.restore(multipartFile);
		System.out.println(title);
		
		SiteVo vo = new SiteVo();
		
		vo.setTitle(title);
		vo.setWelcomeMessage(welcomeMessage);
		vo.setProfileURL(url);
		vo.setDescription(description);
		
		adminService.update(vo);
		model.addAttribute("vo", vo);
		return "redirect:/main/index";
	}
}
