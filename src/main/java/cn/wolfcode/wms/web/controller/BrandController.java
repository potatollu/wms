package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private IBrandService brandService;

    @RequiredPermission("品牌列表")
    @RequestMapping("query")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo){
        System.out.println(qo.getPageSize());
        model.addAttribute("result",brandService.query(qo));
        return "brand/list";
    }
    @RequiredPermission("品牌删除")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id){
        if (id != null) {
            brandService.delete(id);
        }
        return new JSONResult();
    }
    @RequiredPermission("品牌编辑")
    @RequestMapping("input")
    public String input(Model model,Long id){
        if (id != null) {
            model.addAttribute("entity",brandService.get(id));
        }
        return "brand/input";
    }
    @RequiredPermission("品牌保存/跟新")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Brand entity){
        brandService.saveOrUpdate(entity);
        return new JSONResult();
    }

}
