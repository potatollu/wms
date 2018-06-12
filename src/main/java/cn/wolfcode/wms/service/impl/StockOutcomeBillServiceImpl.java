package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.domain.SaleAccount;
import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.domain.StockOutcomeBillItem;
import cn.wolfcode.wms.exception.LogicException;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.mapper.SaleAccountMapper;
import cn.wolfcode.wms.mapper.StockOutcomeBillItemMapper;
import cn.wolfcode.wms.mapper.StockOutcomeBillMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IProductStockService;
import cn.wolfcode.wms.service.IStockOutcomeBillService;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class StockOutcomeBillServiceImpl implements IStockOutcomeBillService{
    @Autowired
    private StockOutcomeBillMapper stockOutcomeBillMapper;
    @Autowired
    private StockOutcomeBillItemMapper stockOutcomeBillItemMapper;
    @Autowired
    private IProductStockService productStockService;
    @Autowired
    private SaleAccountMapper saleAccountMapper;

    public void saveOrUpdate(StockOutcomeBill entity) {
        //传入业务时间和录入人
        entity.setInputUser(UserContext.getCurrentuser());//录入人
        entity.setInputTime(new Date());
        //初始化
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmout = BigDecimal.ZERO;
        List<StockOutcomeBillItem> items = entity.getItems();
        for (StockOutcomeBillItem item : items) {
            //计算总数量
            totalNumber = totalNumber.add(item.getNumber());
            //总价钱,总数*成本价
            totalAmout = totalAmout.add(item.getNumber().multiply(item.getSalePrice()));
        }
        //保存单据信息
        entity.setTotalNumber(totalNumber);
        entity.setTotalAmount(totalAmout);
        if (entity.getId() != null) {
            stockOutcomeBillMapper.updateByPrimaryKey(entity);
            //先删除在保存
            stockOutcomeBillItemMapper.deleteByBillId(entity.getId());
        }else {
            stockOutcomeBillMapper.insert(entity);
        }
            //保存明细信息,保存或更新都要执行
            for (StockOutcomeBillItem item : items) {
                item.setAmount(item.getNumber().multiply(item.getSalePrice()).setScale(2,BigDecimal.ROUND_HALF_UP));
                item.setBillId(entity.getId());
                stockOutcomeBillItemMapper.insert(item);
            }
    }

    public void delete(Long id) {
        if (id != null) {
            stockOutcomeBillMapper.deleteByPrimaryKey(id);
            //删除订单是同时删除明细
            stockOutcomeBillItemMapper.deleteByBillId(id);
        }
    }

    public StockOutcomeBill get(Long id) {
        return stockOutcomeBillMapper.selectByPrimaryKey(id);
    }

    public List<StockOutcomeBill> list() {
        return stockOutcomeBillMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer rows = stockOutcomeBillMapper.query4Count(qo);
        System.out.println(rows);
        if (rows == 0){
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = stockOutcomeBillMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,data);
    }

    public void audit(Long id) {
        //查询出审核之前的单据
        StockOutcomeBill stockOutcomeBill = stockOutcomeBillMapper.selectByPrimaryKey(id);
        if (stockOutcomeBill.getStatus()==StockOutcomeBill.STATUS_NOMAL){
            stockOutcomeBill.setStatus(StockOutcomeBill.STATUS_AUDIT);
            stockOutcomeBill.setAuditor(UserContext.getCurrentuser());
            stockOutcomeBill.setAuditTime(new Date());
            stockOutcomeBillMapper.audit(stockOutcomeBill);
        }
        //审核之后改变库存量
        //根据商品id和仓库id查新仓库中是否有这个商品
        productStockService.stockOutcomeBill(stockOutcomeBill);
    }
}
