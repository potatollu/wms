package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.util.PermissionUtil;
import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.mapper.PermissionMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IPermissionService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService{
    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private ApplicationContext ctx;

    @Override
    public void saveOrUpdate(Permission entity) {
        //System.err.println(entity.getId());
        if (entity.getId() != null) {
            permissionMapper.updateByPrimaryKey(entity);
        }else {
            permissionMapper.insert(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            permissionMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public Permission get(Long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Permission> list() {
        return permissionMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer rows = permissionMapper.query4Count(qo);
        System.out.println(rows);
        if (rows == 0){
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = permissionMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,data);
    }

    @Override
    public void reload() {
        List<String> allExp = permissionMapper.selectAllExpression();
        Collection<Object> ctrls = ctx.getBeansWithAnnotation(Controller.class).values();
        for (Object ctrl : ctrls) {
            Method[] ms = ctrl.getClass().getDeclaredMethods();
            for (Method m : ms) {
                String exp = PermissionUtil.buildExpression(m);
                RequiredPermission ann = m.getAnnotation(RequiredPermission.class);
                if (ann != null){
                    if (!(allExp.contains(exp))) {
                        Permission p = new Permission();
                        p.setName(ann.value());
                        p.setExpression(exp);
                        permissionMapper.insert(p);
                    }
                }
            }
        }
    }
}
