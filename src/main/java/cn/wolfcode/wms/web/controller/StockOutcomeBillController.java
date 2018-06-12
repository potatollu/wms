package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.exception.LogicException;
import cn.wolfcode.wms.query.StockOutcomeBillQueryObject;
import cn.wolfcode.wms.service.IClientService;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.service.IStockOutcomeBillService;
import cn.wolfcode.wms.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("stockOutcomeBill")
public class StockOutcomeBillController {
    @Autowired
    private IStockOutcomeBillService stockOutcomeBillService;
    @Autowired
    private IDepotService depotService;
    @Autowired
    private IClientService clientService;

    @RequiredPermission("订单出库单列表")
    @RequestMapping("query")
    public String query(Model model, @ModelAttribute("qo")StockOutcomeBillQueryObject qo){
        model.addAttribute("depots",depotService.list());
        model.addAttribute("clients",clientService.list());
        model.addAttribute("result",stockOutcomeBillService.query(qo));
        return "stockOutcomeBill/list";
    }

    @RequiredPermission("订单出库单删除")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id){
        if (id != null) {
            stockOutcomeBillService.delete(id);
        }
        return new JSONResult();
    }
    @RequiredPermission("订单出库单编辑")
    @RequestMapping("input")
    public String input(Model model,Long id){
        model.addAttribute("depots",depotService.list());
        model.addAttribute("clients",clientService.list());
        if (id != null) {
            model.addAttribute("entity",stockOutcomeBillService.get(id));
        }
        return "stockOutcomeBill/input";
    }
    @RequiredPermission("订单出库单保存/跟新")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StockOutcomeBill entity){
        stockOutcomeBillService.saveOrUpdate(entity);
        return new JSONResult();
    }
    @RequestMapping("audit")
    @ResponseBody
    public Object audit(Long id){
        JSONResult result = new JSONResult();
        try {
            if (id != null){
                stockOutcomeBillService.audit(id);
            }
        }catch (LogicException e){
            e.printStackTrace();
            result.mark(e.getMessage());
        }
        return result;
    }
    @RequestMapping("show")
    public Object show(Long id,Model model){
        if (id != null){
            model.addAttribute("entity",stockOutcomeBillService.get(id));
        }
        return "stockOutcomeBill/show";
    }
}
