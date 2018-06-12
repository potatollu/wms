package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.query.EmployeeQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepartmentService;
import cn.wolfcode.wms.service.IEmployeeService;
import cn.wolfcode.wms.service.IRoleService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IRoleService roleService;

    @RequiredPermission("员工列表")
    @RequestMapping("query")
    public String query(Model model, @ModelAttribute("qo")EmployeeQueryObject qo){
        PageResult result = employeeService.query(qo);
        model.addAttribute("depts",departmentService.list());
        model.addAttribute("result",result);
        return "employee/list";
    }
    @RequiredPermission("员工保存/更新")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Employee entity,Long[] ids){
        employeeService.saveOrUpdate(entity,ids);
        return new JSONResult();
    }

    @RequiredPermission("员工删除")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id){
        if (id != null) {
            employeeService.delete(id);
        }
        return new JSONResult();
    }

    @RequiredPermission("员工编辑")
    @RequestMapping("input")
    public String input(Model model,Long id){
        model.addAttribute("depts",departmentService.list());
        model.addAttribute("roles",roleService.list());
        if (id != null) {
            model.addAttribute("entity",employeeService.get(id));
        }
        return "employee/input";
    }

    @RequestMapping("batchDelete")
    @ResponseBody
    public Object batchDelete(Long[] ids){
        if (ids != null) {
            employeeService.batchDelete(ids);
        }
        return new JSONResult();
    }
}
