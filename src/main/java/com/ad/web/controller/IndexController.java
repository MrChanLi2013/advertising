package com.ad.web.controller;

import com.ad.dao.ProductDao;
import com.ad.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ad")
public class IndexController {

    private final ProductDao productDao;

    @Autowired
    public IndexController(ProductDao productDao){
        this.productDao = productDao;
    }

    @RequestMapping("/index")
    public String index(Model model) {
        List<Product> productList = productDao.findByLevel(1);
        model.addAttribute("ps", productList);
        return "index";
    }

    @RequestMapping("/indexLevel")
    @ResponseBody
    public List<Product> indexLevel() {
        List<Product> productList = productDao.findByLevel(1);
        return productList;
    }
    @RequestMapping("/map")
    public String maz(Model model) {
        model.addAttribute("test", "test1");
        //return "redirect:http://api.map.baidu.com/marker?location=30.547218,104.052424&title=公司地址&content=逸都国际&output=html";
        return "map";
    }
}
