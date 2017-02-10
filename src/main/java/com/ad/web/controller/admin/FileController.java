package com.ad.web.controller.admin;

import com.ad.dao.ProductDao;
import com.ad.dao.ProductDetailDao;
import com.ad.dao.ProductFileDao;
import com.ad.entity.*;
import com.ad.service.UploadService;
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
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final ProductDao productDao;

    @Value("${page.size}")
    private Integer pageSize;

    @Autowired
    private ProductFileDao productFileDao;
    @Autowired
    private UploadService uploadService;

    @Autowired
    public FileController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @RequestMapping(value = "/admin/file/toUpload", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("category", productDao.findByParentId(1));
        return "admin/upload";
    }


    @RequestMapping(value = "/admin/file/upload", method = RequestMethod.POST)
    public String uploadFile(ProductFileParam param, RedirectAttributes redirectAttributes) {
        try {
            ProductFile productFile = new ProductFile();
            productFile.setName(param.getName());
            productFile.setVideoLink(param.getVideoLink());
            productFile.setVideoDesc(param.getVideoDesc());
            String fileName = param.getPdfFile().getOriginalFilename();
            productFile.setPdfName(fileName);
            productFile.setPdfURL("/products/file/" + fileName);
            productFile.setPostfix(fileName.substring(fileName.lastIndexOf(".")+1).toUpperCase());
            productFile.setSize(getSize(param.getPdfFile().getSize()));
            uploadService.uploadFile(param.getPdfFile());
            productFileDao.save(productFile);
            redirectAttributes.addFlashAttribute("message", "上传产品成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("message", String.format("上传产品失败[%s]", e.getMessage()));
        }
        return "redirect:/admin/index";
    }

    private String getSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        //%.2f 即是保留两位小数的浮点数，后面跟上对应单位就可以了，不得不说java很方便
        if (size >= gb) {
            return String.format("%.2f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            //如果大于100MB就不用保留小数位啦
            return String.format(f > 100 ? "%.0f MB" : "%.2f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            //如果大于100kB就不用保留小数位了
            return String.format(f > 100 ? "%.0f KB" : "%.2f KB", f);
        } else{
            return String.format("%d B", size);
        }
}

    @RequestMapping(value = "/admin/file/zlist", method = RequestMethod.GET)
    public String zlist(Model model, @RequestParam(value = "page",
            required = false,
            defaultValue = "1") Integer page) {
        Page<ProductFile> productFiles = productFileDao.findAll(new PageRequest(page - 1, pageSize));
        model.addAttribute("page", new PaginationHelper<ProductFile>(productFiles, "/admin/file/zlist"));
        return "admin/file_list";
    }

    @RequestMapping(value = "/admin/file/delete", method = RequestMethod.GET)
    public String deleteProduct(@RequestParam("id") Integer id,RedirectAttributes redirectAttributes) {
        ProductFile productFile = productFileDao.findOneById(id);
        if(productFile != null){
            productFileDao.delete(productFile);
        }
        return "redirect:/admin/index";
    }
}
