package com.ad.web.controller;

import com.ad.dao.ProductDao;
import com.ad.dao.ProductDetailDao;
import com.ad.entity.Product;
import com.ad.entity.ProductDetail;
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
    @Autowired
    private ProductDetailDao productDetailDao;

    @RequestMapping(value = "/product-list/{level}", method = RequestMethod.GET)
    public String product(@PathVariable(value = "level") Integer level, Model model) {
        List<Product> list = productDao.findByLevel(level);
        Preconditions.checkState(!list.isEmpty(), String.format("未找到level为[%s]的产品.", level));
        model.addAttribute("products", list);
        return "product";
    }

    @RequestMapping(value = "/product-list/detail", method = RequestMethod.POST)
    public String detail(@RequestParam("id") Integer id, Model model) {
        ProductDetail detail = productDetailDao.findOneById(id);
        Preconditions.checkNotNull(detail, String.format("未找到产品的详情."));
        model.addAttribute("detail", detail);
        return "tpl/detail";
    }
}
