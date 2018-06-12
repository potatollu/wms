package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Depot;
import cn.wolfcode.wms.mapper.DepotMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepotServiceImpl implements IDepotService{
    @Autowired
    private DepotMapper depotMapper;
    @Override
    public void saveOrUpdate(Depot entity) {
        //System.err.println(entity.getId());
        if (entity.getId() != null) {
            depotMapper.updateByPrimaryKey(entity);
        }else {
            depotMapper.insert(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            depotMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public Depot get(Long id) {
        return depotMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Depot> list() {
        return depotMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer rows = depotMapper.query4Count(qo);
        System.out.println(rows);
        if (rows == 0){
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = depotMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,data);
    }
}
