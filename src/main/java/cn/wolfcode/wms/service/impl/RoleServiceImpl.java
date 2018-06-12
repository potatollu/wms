package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.mapper.RoleMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IRoleService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService{
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public void saveOrUpdate(Role entity, Long[] menuIds, Long[] ids) {
         if (entity.getId() != null) {
                    roleMapper.deleteRelation(entity.getId());
                    roleMapper.deleteMenuRelation(entity.getId());
                    roleMapper.updateByPrimaryKey(entity);
                }else {
                    roleMapper.insert(entity);
                }
        if (ids != null){
            for (Long permissionId : ids) {
                roleMapper.insertRelation(entity.getId(),permissionId);
            }
        }

        if (menuIds != null){
            for (Long id : menuIds) {
                roleMapper.insertMenuRelation(entity.getId(),id);
            }
        }

    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            roleMapper.deleteRelation(id);
            roleMapper.deleteMenuRelation(id);
            roleMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public Role get(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Role> list() {
        return roleMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer rows = roleMapper.query4Count(qo);
        System.out.println(rows);
        if (rows == 0){
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = roleMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,data);
    }
}
