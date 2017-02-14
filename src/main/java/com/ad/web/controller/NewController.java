package com.ad.web.controller;

import com.ad.dao.CompanyNewsDao;
import com.ad.dao.NewsDao;
import com.ad.entity.CompanyNews;
import com.ad.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping(value = "/ad")
@Controller
public class NewController {
    @Autowired
    private NewsDao newsDao;
    @Autowired
    private CompanyNewsDao companyNewsDao;


    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String index(Model model, @RequestParam(value = "id", defaultValue = "0", required = false) Integer id) {
        News news;
        if (id == null || id == 0) {
            news = newsDao.findFirstByOrderByCreatedAtDesc();
        } else {
            news = newsDao.findOne(id);
        }
        List<News> newsList = newsDao.findAllByOrderByCreatedAtDesc();
        if (news == null) {
            news = new News("", "");
        }
        model.addAttribute("news", news);
        model.addAttribute("newsList", newsList);
        return "news";
    }

    @RequestMapping(value = "/news/company", method = RequestMethod.GET)
    public String companyNews(Model model, @RequestParam(value = "id", defaultValue = "0", required = false) Integer id) {
        CompanyNews news;
        if (id == null || id == 0) {
            news = companyNewsDao.findFirstByOrderByCreatedAtDesc();
        } else {
            news = companyNewsDao.findOne(id);
        }
        List<CompanyNews> newsList = companyNewsDao.findAllByOrderByCreatedAtDesc();
        if (news == null) {
            news = new CompanyNews("", "");
        }
        model.addAttribute("news", news);
        model.addAttribute("newsList", newsList);
        return "company_news";
    }

}
