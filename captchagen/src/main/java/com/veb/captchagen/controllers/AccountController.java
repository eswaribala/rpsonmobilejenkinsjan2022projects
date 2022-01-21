package com.veb.captchagen.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.veb.captchagen.models.Account;

@Controller
@RequestMapping("account")
public class AccountController {

	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.put("account", new Account());
		return "account/index";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@ModelAttribute("account") Account account, HttpSession session, HttpServletRequest request,
			ModelMap modelMap) {
		String captcha = session.getAttribute("captcha_security").toString();
		String verifyCaptcha = request.getParameter("captcha");
		if (captcha.equals(verifyCaptcha)) {
			modelMap.put("account", account);
			return "account/success";
		} else {
			modelMap.put("error", "Captcha Invalid");
			return "account/index";
		}
	}

}