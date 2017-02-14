package com.ad.web.controller.admin;

import com.ad.dao.NewsDao;
import com.ad.entity.News;
import com.ad.entity.PageProductParam;
import com.ad.entity.Product;
import com.ad.entity.ProductDetail;
import com.ad.util.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.*;

@Controller
@RequestMapping("/admin")
public class NewsController {
    @Autowired
    private NewsDao newsDao;
    @Value("${page.size}")
    private Integer pageSize;

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String news() {
        return "admin/add_news";
    }

    @RequestMapping(value = "/news", method = RequestMethod.POST)
    public String add(String title, String content,Model model) {
        News news = new News(title, content);
        newsDao.save(news);
        list(model,1);
        return "admin/news";
    }
    @RequestMapping(value = "/news/list", method = RequestMethod.GET)
    public String list(Model model, @RequestParam(value = "page",
            required = false,
            defaultValue = "1") Integer page) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<News> newses = newsDao.findAll(new PageRequest(page - 1, pageSize,sort));

        model.addAttribute("page", new PaginationHelper<News>(newses, "/admin/news/list"));
        return "admin/news";
    }

    @RequestMapping(value = "/news/delete", method = RequestMethod.GET)
    public String deleteProduct(@RequestParam("id") Integer id,
                                PageProductParam param, RedirectAttributes redirectAttributes) {
        News news = newsDao.findOne(id);
        if(news != null){
            newsDao.delete(news);
        }
        return "redirect:/list";
    }
}
