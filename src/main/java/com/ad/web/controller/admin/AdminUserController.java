package com.ad.web.controller.admin;

import com.ad.dao.AdminUserDao;
import com.ad.entity.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminUserController {
    @Autowired
    private AdminUserDao adminUserDao;

    @RequestMapping(value = "/user-info", method = RequestMethod.GET)
    public String info(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "admin/user_info";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String info(String username, String newPassword, HttpServletRequest request, RedirectAttributes redirectAttributes) throws ServletException {
        AdminUser user = adminUserDao.findByUserName(username);
        user.setShaPassword(newPassword);
        adminUserDao.save(user);
        redirectAttributes.addFlashAttribute("message", "修改密码成功");
        request.logout();
        SecurityContextHolder.clearContext();
        return "redirect:/admin/login";
    }

    @RequestMapping(value = "/ajax-valide-password", method = RequestMethod.POST)
    @ResponseBody
    public boolean validatePassword(String oldPassword, String username) {
        System.out.println(oldPassword);
        AdminUser user = adminUserDao.findByUserName(username);
        return user.getShaPassword().equals(DigestUtils.md5DigestAsHex(oldPassword.getBytes()));
    }
}
