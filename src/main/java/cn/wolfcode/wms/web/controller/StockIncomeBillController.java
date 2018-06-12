package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.query.StockIncomeBillQueryObject;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.service.IStockIncomeBillService;
import cn.wolfcode.wms.service.ISupplierService;
import cn.wolfcode.wms.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("stockIncomeBill")
public class StockIncomeBillController {
    @Autowired
    private IStockIncomeBillService stockIncomeBillService;
    @Autowired
    private IDepotService depotService;

    @RequiredPermission("采购入库单列表")
    @RequestMapping("query")
    public String query(Model model, @ModelAttribute("qo")StockIncomeBillQueryObject qo){
        model.addAttribute("depots",depotService.list());
        model.addAttribute("result",stockIncomeBillService.query(qo));
        return "stockIncomeBill/list";
    }

    @RequiredPermission("采购入库单删除")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id){
        if (id != null) {
            stockIncomeBillService.delete(id);
        }
        return new JSONResult();
    }
    @RequiredPermission("采购入库单编辑")
    @RequestMapping("input")
    public String input(Model model,Long id){
        model.addAttribute("depots",depotService.list());
        if (id != null) {
            model.addAttribute("entity",stockIncomeBillService.get(id));
        }
        return "stockIncomeBill/input";
    }
    @RequiredPermission("采购入库单保存/跟新")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StockIncomeBill entity){
        stockIncomeBillService.saveOrUpdate(entity);
        return new JSONResult();
    }
    @RequestMapping("audit")
    @ResponseBody
    public Object audit(Long id){
        if (id != null){
            stockIncomeBillService.audit(id);
        }
        return new JSONResult();
    }
    @RequestMapping("show")
    public Object show(Long id,Model model){
        if (id != null){
            model.addAttribute("entity",stockIncomeBillService.get(id));
        }
        return "stockIncomeBill/show";
    }
}
