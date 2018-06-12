package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface DepartmentMapper {
    void deleteByPrimaryKey(Long id);

    void insert(Department entity);

    Department selectByPrimaryKey(Long id);

    List<Department> selectAll();

    void updateByPrimaryKey(Department entity);

    int query4Count(QueryObject qo);

    List<Employee> query4List(QueryObject qo);
}