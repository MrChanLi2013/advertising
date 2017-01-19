package com.ad.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("AdminIndexController")
@RequestMapping("/ad/admin")
public class IndexController {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "admin/index";
    }
}
