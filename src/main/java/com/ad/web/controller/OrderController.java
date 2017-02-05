package com.ad.web.controller;

import com.ad.dao.ProductDao;
import com.ad.dao.ProductDetailDao;
import com.ad.entity.PageProductParam;
import com.ad.entity.Product;
import com.ad.entity.ProductDetail;
import com.ad.service.UploadService;
import com.ad.util.PaginationHelper;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final ProductDao productDao;

    @Value("${page.size}")
    private Integer pageSize;

    @Autowired
    private ProductDetailDao productDetailDao;
    @Autowired
    private UploadService uploadService;

    @Autowired
    public OrderController(ProductDao productDao) {
        this.productDao = productDao;
    }


    @RequestMapping(value = "/ad/order", method = RequestMethod.POST)
    public String order(PageProductParam param, RedirectAttributes redirectAttributes) {

        try {
            Product product = new Product();
            ProductDetail detail = new ProductDetail();
            product.setLevel(2);
            product.setName(param.getName());
            product.setParentId(param.getParentId());
            product.setImgUrl("/products/" + param.getImg().getOriginalFilename());
            uploadService.upload(param.getImg());
            detail.setMaterial(param.getMaterial());
            detail.setModel(param.getModel());
            detail.setRemark(param.getRemark());
            detail.setSize(param.getSize());
            detail.setDetailImg("/products/" + param.getDetail().getOriginalFilename());
            uploadService.upload(param.getDetail());
            detail.setProduct(product);
            product.setDetail(detail);
            productDao.save(product);
            redirectAttributes.addFlashAttribute("message", "上传产品成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("message", String.format("上传产品失败[%s]", e.getMessage()));
        }
        return "success";
    }
}
