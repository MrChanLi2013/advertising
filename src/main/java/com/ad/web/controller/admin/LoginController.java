package com.ad.web.controller.admin;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @RequestMapping("/login")
    public String login() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            return "redirect:/admin/index";
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
