package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.query.QueryObject;
import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission entity);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission entity);

    List<?> query4List(QueryObject qo);

    Integer query4Count(QueryObject qo);

    List<String> selectAllExpression();

    Role selectPermissionByRoleId();

    List<String> selectExpressionByEMpId(Long employeeId);
}