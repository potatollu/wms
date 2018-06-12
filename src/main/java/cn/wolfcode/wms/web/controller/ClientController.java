package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.domain.Client;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IClientService;
import cn.wolfcode.wms.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("client")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @RequiredPermission("客户列表")
    @RequestMapping("query")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo){
        System.out.println(qo.getPageSize());
        model.addAttribute("result",clientService.query(qo));
        return "client/list";
    }
    @RequiredPermission("客户删除")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id){
        if (id != null) {
            clientService.delete(id);
        }
        return new JSONResult();
    }
    @RequiredPermission("客户编辑")
    @RequestMapping("input")
    public String input(Model model,Long id){
        if (id != null) {
            model.addAttribute("entity",clientService.get(id));
        }
        return "client/input";
    }
    @RequiredPermission("客户保存/跟新")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Client entity){
        clientService.saveOrUpdate(entity);
        return new JSONResult();
    }

}
