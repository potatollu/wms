package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.SystemMenuQueryObject;
import cn.wolfcode.wms.service.ISystemMenuService;
import cn.wolfcode.wms.util.JSONResult;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("systemMenu")
public class SystemMenuController {
    @Autowired
    private ISystemMenuService systemMenuService;

    @RequiredPermission("菜单列表")
    @RequestMapping("query")
    public String query(Model model, @ModelAttribute("qo")SystemMenuQueryObject qo){
        if (qo.getParentId() != null){
            //查找当前菜单的父菜单集合
            SystemMenu menu = systemMenuService.get(qo.getParentId());
            model.addAttribute("parents",systemMenuService.getParentMenus(menu));
        }
        model.addAttribute("list",systemMenuService.list(qo));
        return "systemMenu/list";
    }
    @RequiredPermission("菜单删除")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id){
        if (id != null) {
            systemMenuService.delete(id);
        }
        return new JSONResult();
    }
    @RequiredPermission("菜单编辑")
    @RequestMapping("input")
    public String input(Model model,Long parentId,Long id){

       if(parentId != null){
           model.addAttribute("parent",systemMenuService.get(parentId));
       }
        if (id != null) {
            model.addAttribute("entity",systemMenuService.get(id));
        }
        return "systemMenu/input";
    }
    @RequiredPermission("菜单保存/跟新")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(SystemMenu entity){
        systemMenuService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequestMapping("loadMenu")
    @ResponseBody
    public List<Map<String,Object>> loadMenu(String parentSn){
        List<Map<String,Object>> menus = systemMenuService.selectByParetnSn(parentSn);
        return menus;
    }

}
