package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.SystemMenuQueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;
import java.util.Map;

public interface ISystemMenuService {
    void saveOrUpdate(SystemMenu entity);
    void delete(Long id);
    SystemMenu get(Long id);
    List<SystemMenu> list(SystemMenuQueryObject qo);

    List<SystemMenu> getParentMenus(SystemMenu menu);

    List<SystemMenu> getChiledMenus();

    List<Map<String,Object>> selectByParetnSn(String parentSn);
}
