package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.query.SystemMenuQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SystemMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemMenu entity);

    SystemMenu selectByPrimaryKey(Long id);

    List<SystemMenu> selectAll(SystemMenuQueryObject qo);

    int updateByPrimaryKey(SystemMenu entity);

    SystemMenu selectByRoleId();

    List<SystemMenu> selectChildMenus();

    List<Map<String,Object>> selectByParentSn(String parentSn);

    List<Map<String,Object>> selectMenuByEmployeeId(@Param("parentSn")String parentSn,
                               @Param("employeeId")Long employeeId);
}