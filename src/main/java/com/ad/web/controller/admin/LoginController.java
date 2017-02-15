package com.ad.web.controller.admin;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @RequestMapping("/login")
    public String login(@RequestParam(required = false, defaultValue = "0") int error, Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            return "redirect:/admin/index";
        }
        if (error == 1) {
            model.addAttribute("message", "用户名或密码错误");
        }
        return "admin/login";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        SecurityContextHolder.clearContext();
        return "admin/login";
    }
}
