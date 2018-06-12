package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.query.EmployeeQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    void deleteByPrimaryKey(Long id);

    void insert(Employee entity);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    void updateByPrimaryKey(Employee entity);

    int query4Count(EmployeeQueryObject qo);
    List<Employee> query4List(EmployeeQueryObject qo);

    void deleteRelation(Long id);

    void insertRelation(@Param("employeeId") Long employeeId,
                        @Param("roleId") Long roleId);

    Employee selectEmplyeeByInfo(@Param("username") String username,
                                 @Param("password") String password);

    void batchDelete(Long[] ids);
}