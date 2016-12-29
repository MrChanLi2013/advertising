package com.ad.web.controller;

import com.ad.dao.ProductDao;
import com.ad.entity.Product;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/ad")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @RequestMapping(value = "/product-list/{level}", method = RequestMethod.GET)
    public String product(@PathVariable(value = "level") Integer level, Model model) {
        List<Product> list = productDao.findByLevel(level);
        Preconditions.checkState(list.isEmpty(), String.format("未找到level为[%s]的产品.", level));
        model.addAttribute("products", list);
        return "product";
    }
}
