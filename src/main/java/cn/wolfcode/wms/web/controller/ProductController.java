package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.domain.Product;
import cn.wolfcode.wms.query.ProductQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.service.IProductService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Mac;
import javax.servlet.ServletContext;

@Controller
@RequestMapping("product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private IBrandService brandService;

    @Autowired
    private ServletContext cxt;

    @RequiredPermission("商品列表")
    @RequestMapping("query")
    public String query(Model model, @ModelAttribute("qo")ProductQueryObject qo){
        System.out.println(qo.getPageSize());
        model.addAttribute("result",productService.query(qo));
        model.addAttribute("brands",brandService.list());
        return "product/list";
    }

    @RequestMapping("selectProductList")
    public String selectProduct(Model model, @ModelAttribute("qo")ProductQueryObject qo){
        model.addAttribute("result",productService.query(qo));
        return "product/selectProductList";
    }

    @RequiredPermission("商品删除")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id,String imagePath){
        if (id != null) {
            productService.delete(id);
           UploadUtil.deleteFile(cxt,imagePath);
        }
        return new JSONResult();
    }
    @RequiredPermission("商品编辑")
    @RequestMapping("input")
    public String input(Model model,Long id){
        model.addAttribute("brands",brandService.list());
        if (id != null) {
            model.addAttribute("entity",productService.get(id));
        }
        return "product/input";
    }
    @RequiredPermission("商品保存/跟新")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Product entity, MultipartFile pic){
        JSONResult result = new JSONResult();
        try {
        //保存上传文件
            if (pic != null && !StringUtils.isEmpty(entity.getImagePath())){
                UploadUtil.deleteFile(cxt,entity.getImagePath());
            }
            if (pic != null){
                String imagePath = UploadUtil.upload(pic, cxt.getRealPath("/upload"));
                entity.setImagePath(imagePath);
            }
            productService.saveOrUpdate(entity);
        }catch (Exception e){
            e.printStackTrace();
            result.mark("保存失败");
        }
        return result;
    }

}
