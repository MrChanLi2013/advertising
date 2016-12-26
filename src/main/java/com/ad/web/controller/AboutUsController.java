package com.ad.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ad")
public class AboutUsController {

    @RequestMapping(method = RequestMethod.GET, value = "/wir")
    public String about() {
        return "wir";
    }
}
