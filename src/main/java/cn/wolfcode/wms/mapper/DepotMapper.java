package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Depot;
import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface DepotMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Depot entity);

    Depot selectByPrimaryKey(Long id);

    List<Depot> selectAll();

    int updateByPrimaryKey(Depot entity);

    int query4Count(QueryObject qo);

    List<Employee> query4List(QueryObject qo);
}