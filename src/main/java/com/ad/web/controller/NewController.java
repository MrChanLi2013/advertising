package com.ad.web.controller;

import com.ad.dao.NewsDao;
import com.ad.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RequestMapping(value = "/ad")
@Controller
public class NewController {

    @Autowired
    private NewsDao newsDao;

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String index(Model model) {
        List<News> newsList = newsDao.findAll();
        model.addAttribute("news",newsList);
        return "news";
    }
}
