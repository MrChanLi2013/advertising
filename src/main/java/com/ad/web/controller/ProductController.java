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

    private final ProductDao productDao;

    @Autowired
    private ProductDetailDao productDetailDao;

    @Autowired
    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

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

    @RequestMapping(value = "/product-list/detail/{id}", method = RequestMethod.GET)
    public String getDetail(@PathVariable("id") Integer id, Model model) {
        ProductDetail detail = productDetailDao.findOneByProductId(id);
        Preconditions.checkNotNull(detail,"未找到商品详情");
        model.addAttribute("detail", detail);
        return "product_detail";
    }

    @RequestMapping(value = "/product-list/shopping", method = RequestMethod.GET)
    public String shoppingList() {
        return "";
    }

    @RequestMapping(value = "/product-list/shopping", method = RequestMethod.POST)
    public String shopping() {
        return "";
    }
}
