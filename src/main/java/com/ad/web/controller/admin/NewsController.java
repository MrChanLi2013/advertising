package com.ad.web.controller.admin;

import com.ad.dao.NewsDao;
import com.ad.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class NewsController {
    @Autowired
    private NewsDao newsDao;

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String news() {
        return "admin/add_news";
    }

    @RequestMapping(value = "/news", method = RequestMethod.POST)
    public String add(String title, String content) {
        News news = new News(title, content);
        newsDao.save(news);
        return "admin/news";
    }
}
