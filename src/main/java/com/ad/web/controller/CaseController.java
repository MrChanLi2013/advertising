package com.ad.web.controller;

import com.ad.dao.CaseDao;
import com.ad.dao.CustomBladeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ad")
public class CaseController {
    @Autowired
    private CaseDao caseDao;
    @Autowired
    private CustomBladeDao customBladeDao;

    @RequestMapping(value = "/case", method = RequestMethod.GET)
    public String caseList(Model model) {
        model.addAttribute("cases", caseDao.findAll());
        return "case";
    }

    @RequestMapping(value = "/case/custom-blade", method = RequestMethod.GET)
    public String customBlade(Model model) {
        model.addAttribute("blades", customBladeDao.findAll());
        return "custom_blade";
    }
}
