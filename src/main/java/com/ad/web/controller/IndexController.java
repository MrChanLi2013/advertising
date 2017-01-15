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

    @RequestMapping("/map")
    public String maz(Model model) {
        model.addAttribute("test", "test1");
        //return "redirect:http://api.map.baidu.com/marker?location=30.547218,104.052424&title=公司地址&content=逸都国际&output=html";
        return "map";
    }
}
