package com.ad.web.controller;

import com.ad.dao.OrderDao;
import com.ad.entity.Order;
import com.ad.entity.PageProductParam;
import com.ad.util.PaginationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Value("${page.size}")
    private Integer pageSize;
    @Autowired
    private OrderDao orderDao;

    @RequestMapping(value = "/ad/order", method = RequestMethod.POST)
    public String order(PageProductParam param, RedirectAttributes redirectAttributes) {

        try {

        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("message", String.format("上传产品失败[%s]", e.getMessage()));
        }
        return "success";
    }

    @RequestMapping(value = "/admin/order", method = RequestMethod.GET)
    public String list(Model model, @RequestParam(value = "page",
            required = false,
            defaultValue = "1") Integer page) {
        Page<Order> productDetail = orderDao.findAll(new PageRequest(page - 1, pageSize));
        model.addAttribute("page", new PaginationHelper<Order>(productDetail, "/admin/product/list"));
        return "admin/order";
    }
}
