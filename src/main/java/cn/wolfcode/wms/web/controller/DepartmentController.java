package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepartmentService;
import cn.wolfcode.wms.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("department")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    @RequiredPermission("部门列表")
    @RequestMapping("query")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo){
        System.out.println(qo.getPageSize());
        model.addAttribute("result",departmentService.query(qo));
        return "department/list";
    }
    @RequiredPermission("部门删除")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id){
        if (id != null) {
            departmentService.delete(id);
        }
        return new JSONResult();
    }
    @RequiredPermission("部门编辑")
    @RequestMapping("input")
    public String input(Model model,Long id){
        if (id != null) {
            model.addAttribute("entity",departmentService.get(id));
        }
        return "department/input";
    }
    @RequiredPermission("部门保存/跟新")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Department entity){
        departmentService.saveOrUpdate(entity);
        return new JSONResult();
    }

}
