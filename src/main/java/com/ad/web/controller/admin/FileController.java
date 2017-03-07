package com.ad.web.controller.admin;

import com.ad.dao.ProductDao;
import com.ad.dao.ProductFileDao;
import com.ad.entity.ProductFile;
import com.ad.entity.ProductFileParam;
import com.ad.entity.WebStatus;
import com.ad.service.UploadService;
import com.ad.util.PaginationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.*;

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
        //model.addAttribute("category", productDao.findByParentId(1));
        return "admin/upload";
    }


    @RequestMapping(value = "/admin/file/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(ProductFileParam param) {
        try {
            ProductFile productFile = new ProductFile();
            productFile.setName(param.getName());
            //productFile.setVideoLink(param.getVideoLink());
            //productFile.setVideoDesc(param.getVideoDesc());
            String fileName = param.getPdfFile().getOriginalFilename();
            productFile.setPdfName(fileName);
            productFile.setPdfURL("/products/files/" + fileName);
            productFile.setPostfix(fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase());
            productFile.setSize(getSize(param.getPdfFile().getSize()));
            uploadService.uploadFile(param.getPdfFile());
            productFileDao.save(productFile);
            return WebStatus.success.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return WebStatus.failed.toString();
        }
    }

    @RequestMapping(value = "/admin/file/zlist", method = RequestMethod.GET)
    public String zlist(Model model, @RequestParam(value = "page",
            required = false,
            defaultValue = "1") Integer page) {
        //Page<ProductFile> productFiles = productFileDao.findAll(new PageRequest(page - 1, pageSize));
        /**修改**/
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<ProductFile> productFiles = productFileDao.findAll(new Specification<ProductFile>() {
            @Override
            public Predicate toPredicate(Root<ProductFile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Path<String> pdfUrl = root.get("pdfURL");
                /**
                 * 连接查询条件, 不定参数，可以连接0..N个查询条件
                 */
                criteriaQuery.where(cb.isNotNull(pdfUrl.as(String.class))); //这里可以设置任意条查询条件
                return null;
            }
        }, new PageRequest(page - 1, pageSize, sort));
        /**结束**/
        model.addAttribute("page", new PaginationHelper<ProductFile>(productFiles, "/admin/file/zlist"));
        return "admin/file_list";
    }

    @RequestMapping(value = "/admin/file/toUpload1", method = RequestMethod.GET)
    public String add1(Model model) {
        //model.addAttribute("category", productDao.findByParentId(1));
        return "admin/upload1";
    }


    @RequestMapping(value = "/admin/file/upload1", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile1(ProductFileParam param) {
        try {
            ProductFile productFile = new ProductFile();
            //productFile.setName(param.getName());
            productFile.setVideoLink(param.getVideoLink());
            productFile.setVideoDesc(param.getVideoDesc());
//            String fileName = param.getPdfFile().getOriginalFilename();
//            productFile.setPdfName(fileName);
//            productFile.setPdfURL("/products/files/" + fileName);
//            productFile.setPostfix(fileName.substring(fileName.lastIndexOf(".")+1).toUpperCase());
//            productFile.setSize(getSize(param.getPdfFile().getSize()));
//            uploadService.uploadFile(param.getPdfFile());
            productFileDao.save(productFile);
            return WebStatus.success.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return WebStatus.failed.toString();
        }
    }


    @RequestMapping(value = "/admin/file/zlist1", method = RequestMethod.GET)
    public String zlist1(Model model, @RequestParam(value = "page",
            required = false,
            defaultValue = "1") Integer page) {
        //Page<ProductFile> productFiles = productFileDao.findAll(new PageRequest(page - 1, pageSize));
        /**修改**/
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<ProductFile> productFiles = productFileDao.findAll(new Specification<ProductFile>() {
            @Override
            public Predicate toPredicate(Root<ProductFile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Path<String> videoLink = root.get("videoLink");
                /**
                 * 连接查询条件, 不定参数，可以连接0..N个查询条件
                 */
                criteriaQuery.where(cb.isNotNull(videoLink.as(String.class))); //这里可以设置任意条查询条件
                return null;
            }
        }, new PageRequest(page - 1, pageSize, sort));
        /**结束**/
        model.addAttribute("page", new PaginationHelper<ProductFile>(productFiles, "/admin/file/zlist1"));
        return "admin/file_list1";
    }

    @RequestMapping(value = "/admin/file/delete", method = RequestMethod.GET)
    @ResponseBody
    public String deleteProduct(@RequestParam("id") Integer id) {
        ProductFile productFile = productFileDao.findOneById(id);
        if (productFile != null) {
            productFileDao.delete(productFile);
        }
        return WebStatus.success.toString();
    }

    @RequestMapping(value = "/admin/file/delete1", method = RequestMethod.GET)
    @ResponseBody
    public String deleteProduct1(@RequestParam("id") Integer id) {
        ProductFile productFile = productFileDao.findOneById(id);
        if (productFile != null) {
            productFileDao.delete(productFile);
        }
        return WebStatus.success.toString();
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
        } else {
            return String.format("%d B", size);
        }
    }
}
