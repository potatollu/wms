package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.exception.SecurityException;
import cn.wolfcode.wms.mapper.EmployeeMapper;
import cn.wolfcode.wms.mapper.PermissionMapper;
import cn.wolfcode.wms.query.EmployeeQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IEmployeeService;
import cn.wolfcode.wms.util.MD5;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService{
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    public void saveOrUpdate(Employee entity,Long[] ids) {
        //System.err.println(entity.getId());
        if (entity.getId() != null) {
            employeeMapper.deleteRelation(entity.getId());
            employeeMapper.updateByPrimaryKey(entity);
        }else {
            employeeMapper.insert(entity);
        }

        if (ids != null){
            for (Long roleId : ids) {
                employeeMapper.insertRelation(entity.getId(),roleId);
            }
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            employeeMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public Employee get(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Employee> list() {
        return employeeMapper.selectAll();
    }

    @Override
    public PageResult query(EmployeeQueryObject qo) {
        Integer rows = employeeMapper.query4Count(qo);
        System.out.println(rows);
        if (rows == 0){
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = employeeMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,data);
    }

    @Override
    public void login(String username, String password) {
        Employee emp = employeeMapper.selectEmplyeeByInfo(username, MD5.encoder(password));
        if (emp == null){
            throw new RuntimeException("账号或密码错误");
        }
        UserContext.setCurrentUser(emp);

        List<String> exps = permissionMapper.selectExpressionByEMpId(emp.getId());
        UserContext.setExpression(exps);
    }

    public void batchDelete(Long[] ids) {
        employeeMapper.batchDelete(ids);
    }
}
