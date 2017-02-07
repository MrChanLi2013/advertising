package com.ad.web.controller;

import com.ad.dao.ProductFileDao;
import com.ad.entity.ProductFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RequestMapping("/ad")
@Controller
public class DownloadContoller {
    @Autowired
    private ProductFileDao productFileDao;

    @RequestMapping(value = "download", method = RequestMethod.GET)
    public String download(Model model) {
        List<ProductFile> list = productFileDao.findAll();
        model.addAttribute("productFiles", list);
        return "download";
    }
}
