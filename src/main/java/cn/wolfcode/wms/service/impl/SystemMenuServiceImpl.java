package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.mapper.SystemMenuMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.SystemMenuQueryObject;
import cn.wolfcode.wms.service.ISystemMenuService;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class SystemMenuServiceImpl implements ISystemMenuService{
    @Autowired
    private SystemMenuMapper systemMenuMapper;

    public void saveOrUpdate(SystemMenu entity) {
        if (entity.getId() != null) {
            systemMenuMapper.updateByPrimaryKey(entity);
        }else {
            systemMenuMapper.insert(entity);
        }
    }

    public void delete(Long id) {
        if (id != null) {
            systemMenuMapper.deleteByPrimaryKey(id);
        }
    }

    public SystemMenu get(Long id) {
        return systemMenuMapper.selectByPrimaryKey(id);
    }

    public List<SystemMenu> list(SystemMenuQueryObject qo) {
        return systemMenuMapper.selectAll(qo);
    }
    //根据当前子菜单获取它对应的父类菜单
    public List<SystemMenu> getParentMenus(SystemMenu menu) {
        List<SystemMenu> parants = new ArrayList<>();
        parants.add(menu);
        while (menu.getParent() != null){//如果不是顶级菜单
            menu = menu.getParent();//获取当前菜单的上一级菜单
            parants.add(menu);//添加进集合中
        }
        Collections.reverse(parants);
        return parants;
    }

    public List<SystemMenu> getChiledMenus() {
        return systemMenuMapper.selectChildMenus();
    }

    public List<Map<String, Object>> selectByParetnSn(String parentSn) {
        Employee currentUser = UserContext.getCurrentuser();
        if (!currentUser.isAdmin()){
            return systemMenuMapper.selectMenuByEmployeeId(parentSn,currentUser.getId());
        }
        return systemMenuMapper.selectByParentSn(parentSn);
    }

}
