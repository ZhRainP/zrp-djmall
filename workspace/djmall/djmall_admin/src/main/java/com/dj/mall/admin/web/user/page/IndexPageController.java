package com.dj.mall.admin.web.user.page;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class IndexPageController {

	@RequestMapping("/toIndex")
	public String toIndex() {
		return "common/index";
	}

	@RequestMapping("/toTop")
	public String toTop(String token, Model m) {
		return "common/top";
	}

	@RequestMapping("/toLeft")
	public String toLeft() {
		return "common/left";
	}

	@RequestMapping("/toRight")
	public String toRight() {
		return "common/right";
	}

	

}
