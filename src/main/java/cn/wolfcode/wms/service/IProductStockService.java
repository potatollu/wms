package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.*;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;

public interface IProductStockService {
    PageResult query(QueryObject qo);

    void stockIncomeBill(StockIncomeBill stockIncomeBill);

    void stockOutcomeBill(StockOutcomeBill stockOutcomeBill);
}
