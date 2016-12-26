package com.ad.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ad")
public class IndexController {

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("test", "test1");
        return "index";
    }
}
