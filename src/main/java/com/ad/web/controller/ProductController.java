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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/ad")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @RequestMapping(value = "/product-list/{id}", method = RequestMethod.GET)
    public String product(@PathVariable(value = "id") Integer id, Model model) {
        List<Product> list = productDao.findByParentId(id);
        Preconditions.checkState(!list.isEmpty(), String.format("未找到id为[%s]的产品.", id));
        model.addAttribute("products", list);
        return "product";
    }

    @RequestMapping(value = "/product-list/detail", method = RequestMethod.POST)
    public String detail(@RequestParam("id") Integer id, Model model) {
        List<Product> list = productDao.findByParentId(id);
        Preconditions.checkState(!list.isEmpty(), String.format("未找到id为[%s]的产品.", id));
        model.addAttribute("list", list);
        model.addAttribute("parent", productDao.findOneById(list.get(0).getParentId()));
        return "tpl/detail";
    }
}
