package com.ad.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/ad")
@Controller
public class NewController {
    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String index() {
        return "news";
    }
}
