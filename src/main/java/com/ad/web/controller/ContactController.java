package com.ad.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/ad")
@Controller
public class ContactController {

    @RequestMapping(method = RequestMethod.GET, value = "/contact")
    public String contact() {
        return "contact";
    }
}
