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
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.*;
import java.util.List;

@Controller
@RequestMapping
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductDao productDao;

    @Value("${page.size}")
    private Integer pageSize;

    @Autowired
    private ProductDetailDao productDetailDao;
    @Autowired
    private UploadService uploadService;

    @Autowired
    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @RequestMapping(value = "/ad/product-list/{id}", method = RequestMethod.GET)
    public String product(@PathVariable(value = "id") Integer id, Model model) {
        List<Product> list = productDao.findByParentId(id);
        Preconditions.checkState(!list.isEmpty(), String.format("未找到id为[%s]的产品.", id));
        model.addAttribute("products", list);
        return "product";
    }

    @RequestMapping(value = "/ad/product-list/detail", method = RequestMethod.POST)
    public String detail(@RequestParam("id") Integer id, Model model) {
        List<Product> list = productDao.findByParentId(id);
        Preconditions.checkState(!list.isEmpty(), String.format("未找到id为[%s]的产品.", id));
        model.addAttribute("list", list);
        model.addAttribute("parent", productDao.findOneById(list.get(0).getParentId()));
        return "tpl/detail";
    }

    @RequestMapping(value = "/admin/product/list", method = RequestMethod.GET)
    public String list(Model model, @RequestParam(value = "page",
            required = false,
            defaultValue = "1") Integer page) {
        Page<ProductDetail> productDetail = productDetailDao.findAll(new PageRequest(page - 1, pageSize));
        model.addAttribute("page", new PaginationHelper<ProductDetail>(productDetail, "/admin/product/list"));
        return "admin/product_list";
    }

    @RequestMapping(value = "/admin/product/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("category", productDao.findByLevel(2));
        return "admin/new";
    }

    @RequestMapping(value = "/admin/product/update/{detailId}", method = RequestMethod.GET)
    public String update(@PathVariable("detailId") Integer detailId, Model model) {
        model.addAttribute("detail", productDetailDao.findOne(detailId));
        model.addAttribute("category", productDao.findByParentId(1));
        return "admin/update";
    }

    @RequestMapping(value = "/admin/product/update/{detailId}", method = RequestMethod.POST)
    public String updateProduct(@PathVariable("detailId") Integer detailId,
                                PageProductParam param, RedirectAttributes redirectAttributes) {
        ProductDetail productDetail = productDetailDao.findOne(detailId);
        productDetail.getProduct().setName(param.getName());
        productDetail.setMaterial(param.getMaterial());
        productDetail.setModel(param.getModel());
        productDetail.setRemark(param.getRemark());
        productDetail.setSize(param.getSize());
        productDetail.getProduct().setParentId(param.getParentId());
        if (!param.getDetail().getOriginalFilename().equals("") &&
                !("/products/" + param.getDetail().getOriginalFilename()).equals(productDetail.getDetailImg())) {
            productDetail.setDetailImg("/products/" + param.getDetail().getOriginalFilename());
            uploadService.upload(param.getDetail());
        }
        if (!param.getImg().getOriginalFilename().equals("") &&
                !("/products/" + param.getImg().getOriginalFilename()).equals(productDetail.getProduct().getImgUrl())) {
            productDetail.getProduct().setImgUrl("/produts/" + param.getImg().getOriginalFilename());
            uploadService.upload(param.getImg());
        }
        productDetailDao.save(productDetail);
        redirectAttributes.addFlashAttribute("message", "修改成功");
        return "redirect:/admin/index";
    }

    @RequestMapping(value = "/admin/product/add", method = RequestMethod.POST)
    public String addProduct(PageProductParam param, RedirectAttributes redirectAttributes) {

        try {
            Product product = new Product();
            ProductDetail detail = new ProductDetail();
            product.setLevel(3);
            product.setName(param.getName());
            product.setParentId(param.getParentId());
            product.setImgUrl("/products/" + param.getImg().getOriginalFilename());
            uploadService.upload(param.getImg());
            detail.setMaterial(param.getMaterial());
            detail.setModel(param.getModel());
            detail.setRemark(param.getRemark());
            detail.setSize(param.getSize());
//            detail.setDetailImg("/products/" + param.getDetail().getOriginalFilename());
//            uploadService.upload(param.getDetail());
            MultipartFile[] detailFiles = param.getDetailFiles();
            String fileUrl ="";
            for(int i=0;i<detailFiles.length;i++){
                MultipartFile detailFile = detailFiles[i];
                if(i == (detailFiles.length-1)){
                    fileUrl += "/products/" + detailFile.getOriginalFilename();
                }else {
                    fileUrl += "/products/" + detailFile.getOriginalFilename() + ",";
                }
                uploadService.upload(detailFile);
            }
            detail.setDetailImg(fileUrl);
            detail.setProduct(product);
            product.setDetail(detail);
            productDao.save(product);
            redirectAttributes.addFlashAttribute("message", "上传产品成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("message", String.format("上传产品失败[%s]", e.getMessage()));
        }
        return "redirect:/admin/index";
    }

    @RequestMapping(value = "/ad/product-list/detail/{id}", method = RequestMethod.GET)
    public String getDetail(@PathVariable("id") Integer id, Model model) {
        ProductDetail detail = productDetailDao.findOneByProductId(id);
        Preconditions.checkNotNull(detail, "未找到商品详情");
        model.addAttribute("detail", detail);
        return "product_detail";
    }

    @RequestMapping(value = "/admin/product/delete", method = RequestMethod.GET)
    public String deleteProduct(@RequestParam("id") Integer id,
                                PageProductParam param, RedirectAttributes redirectAttributes) {
        ProductDetail detail = productDetailDao.findOneByProductId(id);
        if(detail != null){
            productDetailDao.delete(detail);
        }
        Product product = productDao.findOneById(id);
        if(product != null){
            productDao.delete(product);
        }
        return "redirect:/admin/index";
    }

    /**
     * 产品分类列表
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/admin/product/level/list", method = RequestMethod.GET)
    public String levelList(Model model, @RequestParam(value = "page",
            required = false,
            defaultValue = "1") Integer page) {
        Sort sort = new Sort(Sort.Direction.ASC, "level");
        Page<Product> products = productDao.findAll(new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Path<String> level = root.get("level");
                /**
                 * 连接查询条件, 不定参数，可以连接0..N个查询条件
                 */
                criteriaQuery.where(cb.lt(level.as(Integer.class), 3)); //这里可以设置任意条查询条件
                return null;
            }
        },new PageRequest(page - 1, pageSize,sort));

        model.addAttribute("page", new PaginationHelper<Product>(products, "/admin/product/level/list"));
        return "admin/product_level";
    }

    /**
     * 增加产品分类
     * @param model
     * @return
     */
    @RequestMapping(value = "/admin/product/level/add", method = RequestMethod.GET)
    public String addLevel(Model model) {
        model.addAttribute("category", productDao.findByLevel(1));
        return "admin/new_level";
    }

    @RequestMapping(value = "/admin/product/level/add", method = RequestMethod.POST)
    public String addProductLevel(PageProductParam param, RedirectAttributes redirectAttributes) {

        try {
            Product product = new Product();
            Integer level = param.getLevel();
            product.setLevel(level);
            product.setName(param.getName());
            if(level == 2){
                product.setParentId(param.getParentId());
            }else {
                product.setParentId(0);
            }
            productDao.save(product);
            redirectAttributes.addFlashAttribute("message", "新增分类成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("message", String.format("新增分类失败[%s]", e.getMessage()));
        }
        return "redirect:/admin/index";
    }

    @RequestMapping(value = "/admin/product/level/update/{id}", method = RequestMethod.GET)
    public String updateLevel(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("productLevel", productDao.findOneById(id));
        model.addAttribute("category", productDao.findByLevel(1));
        return "admin/update_level";
    }

    @RequestMapping(value = "/admin/product/level/update/{id}", method = RequestMethod.POST)
    public String updateProductLevel(@PathVariable("id") Integer id,
                                     PageProductParam param, RedirectAttributes redirectAttributes) {

        try {
            Product product = productDao.findOneById(id);
            if(product != null){
                Integer level = param.getLevel();
                product.setLevel(level);
                product.setName(param.getName());
                if(level == 2){
                    product.setParentId(param.getParentId());
                }else {
                    product.setParentId(0);
                }
                productDao.save(product);
            }
            redirectAttributes.addFlashAttribute("message", "修改分类成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("message", String.format("修改分类失败[%s]", e.getMessage()));
        }
        return "redirect:/admin/index";
    }

    @RequestMapping(value = "/admin/product/level/delete", method = RequestMethod.GET)
    public String deleteProductLevel(@RequestParam("id") Integer id,
                                PageProductParam param, RedirectAttributes redirectAttributes) {
        List<Product> list = productDao.findByParentId(id);
        if(list == null){
            Product product = productDao.findOneById(id);
            if(product != null){
                productDao.delete(product);
                redirectAttributes.addFlashAttribute("message", "删除成功");
            }
        }else {
            redirectAttributes.addFlashAttribute("message", "此分类下子类,请先删除子类");
        }
        return "admin/index";
    }

}
