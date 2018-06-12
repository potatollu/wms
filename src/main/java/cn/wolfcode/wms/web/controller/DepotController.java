package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.domain.Depot;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("depot")
public class DepotController {
    @Autowired
    private IDepotService depotService;

    @RequiredPermission("仓库列表")
    @RequestMapping("query")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo){
        System.out.println(qo.getPageSize());
        model.addAttribute("result",depotService.query(qo));
        return "depot/list";
    }
    @RequiredPermission("仓库删除")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id){
        if (id != null) {
            depotService.delete(id);
        }
        return new JSONResult();
    }
    @RequiredPermission("仓库编辑")
    @RequestMapping("input")
    public String input(Model model,Long id){
        if (id != null) {
            model.addAttribute("entity",depotService.get(id));
        }
        return "depot/input";
    }
    @RequiredPermission("仓库保存/跟新")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Depot entity){
        depotService.saveOrUpdate(entity);
        return new JSONResult();
    }

}
