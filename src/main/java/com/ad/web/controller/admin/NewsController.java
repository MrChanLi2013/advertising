package com.ad.web.controller.admin;

import com.ad.dao.CompanyNewsDao;
import com.ad.dao.NewsDao;
import com.ad.entity.CompanyNews;
import com.ad.entity.News;
import com.ad.util.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class NewsController {
    @Autowired
    private NewsDao newsDao;
    @Autowired
    private CompanyNewsDao companyNewsDao;
    @Value("${page.size}")
    private Integer pageSize;

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String news() {
        return "admin/add_news";
    }

    @RequestMapping(value = "/company-news", method = RequestMethod.GET)
    public String companyNews() {
        return "admin/add_company_news";
    }

    @RequestMapping(value = "/company-news", method = RequestMethod.POST)
    @ResponseBody
    public String addCompanyNews(String title, String content) {
        try {
            CompanyNews news = new CompanyNews(title, content);
            companyNewsDao.save(news);
            return "success";
        } catch (Exception e) {
            return "failed";
        }
    }

    @RequestMapping(value = "/news", method = RequestMethod.POST)
    @ResponseBody
    public String add(String title, String content) {
        try {
            News news = new News(title, content);
            newsDao.save(news);
            return "success";
        } catch (Exception e) {
            return "failed";
        }
    }

    @RequestMapping(value = "/company-news-list", method = RequestMethod.GET)
    public String companyNewsList(Model model, @RequestParam(value = "page",
            required = false,
            defaultValue = "1") Integer page) {
        Page<CompanyNews> news = companyNewsDao.findAll(new PageRequest(page - 1, pageSize));
        model.addAttribute("page", new PaginationHelper<CompanyNews>(news, "/admin/company-news-list"));
        return "admin/company_news";
    }

    @RequestMapping(value = "/news-list", method = RequestMethod.GET)
    public String newsList(Model model, @RequestParam(value = "page",
            required = false,
            defaultValue = "1") Integer page) {
        Page<News> news = newsDao.findAll(new PageRequest(page - 1, pageSize));
        model.addAttribute("page", new PaginationHelper<News>(news, "/admin/news-list"));
        return "admin/news";
    }

    @RequestMapping(value = "/news/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@PathVariable Integer id) {
        newsDao.delete(id);
        return "success";
    }

    @RequestMapping(value = "/company-news/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCompanyNes(@PathVariable Integer id) {
        companyNewsDao.delete(id);
        return "success";
    }
}
