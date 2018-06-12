package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.domain.Supplier;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.ISupplierService;
import cn.wolfcode.wms.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("supplier")
public class SupplierController {
    @Autowired
    private ISupplierService supplierService;

    @RequiredPermission("供应商列表")
    @RequestMapping("query")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo){
        System.out.println(qo.getPageSize());
        model.addAttribute("result",supplierService.query(qo));
        return "supplier/list";
    }
    @RequiredPermission("供应商删除")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id){
        if (id != null) {
            supplierService.delete(id);
        }
        return new JSONResult();
    }
    @RequiredPermission("供应商编辑")
    @RequestMapping("input")
    public String input(Model model,Long id){
        if (id != null) {
            model.addAttribute("entity",supplierService.get(id));
        }
        return "supplier/input";
    }
    @RequiredPermission("供应商保存/跟新")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Supplier entity){
        supplierService.saveOrUpdate(entity);
        return new JSONResult();
    }

}
