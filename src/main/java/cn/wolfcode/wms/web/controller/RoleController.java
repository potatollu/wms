package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IPermissionService;
import cn.wolfcode.wms.service.IRoleService;
import cn.wolfcode.wms.service.ISystemMenuService;
import cn.wolfcode.wms.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @Autowired
    private ISystemMenuService systemMenuService;

    @Autowired
    private IPermissionService permissionService;

    @RequiredPermission("部门列表")
    @RequestMapping("query")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo){
        System.out.println(qo.getPageSize());
        model.addAttribute("result",roleService.query(qo));
        return "role/list";
    }
    @RequiredPermission("部门删除")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id){
        if (id != null) {
            roleService.delete(id);
        }
        return new JSONResult();
    }
    @RequiredPermission("部门编辑")
    @RequestMapping("input")
    public String input(Model model,Long id){
        model.addAttribute("permissions",permissionService.list());
        model.addAttribute("menus",systemMenuService.getChiledMenus());
        if (id != null) {
            model.addAttribute("entity",roleService.get(id));
        }
        return "role/input";
    }
    @RequiredPermission("部门保存/跟新")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Role entity,Long[] ids,Long[] menuIds){
        roleService.saveOrUpdate(entity,ids,menuIds);
        return new JSONResult();
    }

}
