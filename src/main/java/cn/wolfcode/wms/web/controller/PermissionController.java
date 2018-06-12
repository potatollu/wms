package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IPermissionService;
import cn.wolfcode.wms.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("reload")
    @ResponseBody
    public Object reload(){
        permissionService.reload();
        return new JSONResult();
    }
    @RequiredPermission("权限列表")
    @RequestMapping("query")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo){
        System.out.println(qo.getPageSize());
        model.addAttribute("result",permissionService.query(qo));
        return "permission/list";
    }

    @RequiredPermission("权限删除")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id){
        if (id != null) {
            permissionService.delete(id);
        }
        return new JSONResult();
    }

}
