package com.ad.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/ad")
@Controller
public class DownloadContoller {

    @RequestMapping(value = "download", method = RequestMethod.GET)
    public String download() {
        return "download";
    }
}
