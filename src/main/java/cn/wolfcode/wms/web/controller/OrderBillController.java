package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.query.OrderBillQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IOrderBillService;
import cn.wolfcode.wms.service.IProductService;
import cn.wolfcode.wms.service.ISupplierService;
import cn.wolfcode.wms.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("orderBill")
public class OrderBillController {
    @Autowired
    private IOrderBillService orderBillService;
    @Autowired
    private ISupplierService supplierService;

    @RequiredPermission("商品订单列表")
    @RequestMapping("query")
    public String query(Model model, @ModelAttribute("qo")OrderBillQueryObject qo){
        model.addAttribute("suppliers",supplierService.list());
        model.addAttribute("result",orderBillService.query(qo));
        return "orderbill/list";
    }

    @RequiredPermission("商品订单删除")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id){
        if (id != null) {
            orderBillService.delete(id);
        }
        return new JSONResult();
    }
    @RequiredPermission("商品订单编辑")
    @RequestMapping("input")
    public String input(Model model,Long id){
        model.addAttribute("suppliers",supplierService.list());
        if (id != null) {
            model.addAttribute("entity",orderBillService.get(id));
        }
        return "orderbill/input";
    }
    @RequiredPermission("商品订单保存/跟新")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(OrderBill entity){
        orderBillService.saveOrUpdate(entity);
        return new JSONResult();
    }
    @RequestMapping("audit")
    @ResponseBody
    public Object audit(Long id){
        if (id != null){
            orderBillService.audit(id);
        }
        return new JSONResult();
    }
    @RequestMapping("show")
    public Object show(Long id,Model model){
        if (id != null){
            model.addAttribute("entity",orderBillService.get(id));
        }
        return "orderbill/show";
    }
}
