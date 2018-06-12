package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.query.OrderChartQueryObject;
import cn.wolfcode.wms.query.OrderSaleQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import javafx.scene.shape.QuadCurve;

import java.util.List;
import java.util.Map;

public interface ChartMapper {
    /**
     * 查询订货报表
     * @param 高级查询的条件
     * @return 返回页面需要的数据
     */
    List<Map<String,Object>> selectOrderChart(OrderChartQueryObject qo);

    List<Map<String,Object>> selectSaleChart(OrderSaleQueryObject qo);
}
