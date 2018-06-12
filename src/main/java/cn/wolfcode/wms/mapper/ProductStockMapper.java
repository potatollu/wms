package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductStockMapper {

    int insert(ProductStock entity);

    int updateByPrimaryKey(ProductStock entity);

    ProductStock selectByProductIdandDepotId(@Param("productId") Long productId,
                                             @Param("depotId") Long depotId);
    int query4Count(QueryObject qo);

    List<Employee> query4List(QueryObject qo);
}