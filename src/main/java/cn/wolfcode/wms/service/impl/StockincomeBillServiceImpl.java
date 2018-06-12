package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.domain.StockIncomeBillItem;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.mapper.StockIncomeBillItemMapper;
import cn.wolfcode.wms.mapper.StockIncomeBillMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IProductService;
import cn.wolfcode.wms.service.IProductStockService;
import cn.wolfcode.wms.service.IStockIncomeBillService;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class StockincomeBillServiceImpl implements IStockIncomeBillService {
    @Autowired
    private StockIncomeBillMapper stockIncomeBillMapper;
    @Autowired
    private StockIncomeBillItemMapper stockIncomeBillItemMapper;
    @Autowired
    private IProductStockService productStockService;

    @Override
    public void saveOrUpdate(StockIncomeBill entity) {
        //传入业务时间和录入人
        entity.setInputUser(UserContext.getCurrentuser());//录入人
        entity.setInputTime(new Date());
        //初始化
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmout = BigDecimal.ZERO;
        List<StockIncomeBillItem> items = entity.getItems();
        for (StockIncomeBillItem item : items) {
            //计算总数量
            totalNumber = totalNumber.add(item.getNumber());
            //总价钱,总数*成本价
            totalAmout = totalAmout.add(item.getNumber().multiply(item.getCostPrice()));
        }
        //保存单据信息
        entity.setTotalNumber(totalNumber);
        entity.setTotalAmount(totalAmout);
        if (entity.getId() != null) {
            stockIncomeBillMapper.updateByPrimaryKey(entity);
            //先删除在保存
            stockIncomeBillItemMapper.deleteByBillId(entity.getId());
        } else {
            stockIncomeBillMapper.insert(entity);
        }
        //保存明细信息,保存或更新都要执行
        for (StockIncomeBillItem item : items) {
            item.setAmount(item.getNumber().multiply(item.getCostPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
            item.setBillId(entity.getId());
            stockIncomeBillItemMapper.insert(item);
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            stockIncomeBillMapper.deleteByPrimaryKey(id);
            //删除订单是同时删除明细
            stockIncomeBillItemMapper.deleteByBillId(id);
        }
    }

    @Override
    public StockIncomeBill get(Long id) {
        return stockIncomeBillMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<StockIncomeBill> list() {
        return stockIncomeBillMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer rows = stockIncomeBillMapper.query4Count(qo);
        System.out.println(rows);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = stockIncomeBillMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, data);
    }

    @Override
    public void audit(Long id) {
        //查询出审核之前的单据
        StockIncomeBill stockIncomeBill = stockIncomeBillMapper.selectByPrimaryKey(id);
        if (stockIncomeBill.getStatus() == StockIncomeBill.STATUS_NOMAL) {
            stockIncomeBill.setStatus(StockIncomeBill.STATUS_AUDIT);
            stockIncomeBill.setAuditor(UserContext.getCurrentuser());
            stockIncomeBill.setAuditTime(new Date());
            stockIncomeBillMapper.audit(stockIncomeBill);
        }
        //审核之后改变库存量
        //判断明细中的商品是否已存在数据库之中
        productStockService.stockIncomeBill(stockIncomeBill);
    }
}
