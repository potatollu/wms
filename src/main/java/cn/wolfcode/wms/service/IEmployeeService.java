package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.query.EmployeeQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;

public interface IEmployeeService {
    void saveOrUpdate(Employee entity,Long[] ids);
    void delete(Long id);
    Employee get(Long id);
    List<Employee> list();

    PageResult query(EmployeeQueryObject qo);

    void login(String username, String password);

    void batchDelete(Long[] ids);
}
