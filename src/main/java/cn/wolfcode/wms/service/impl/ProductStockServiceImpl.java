package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.*;
import cn.wolfcode.wms.exception.LogicException;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.mapper.SaleAccountMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IProductStockService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ProductStockServiceImpl implements IProductStockService{
    @Autowired
    private ProductStockMapper productStockMapper;
    @Autowired
    private SaleAccountMapper saleAccountMapper;
    @Override
    public PageResult query(QueryObject qo) {
        Integer rows = productStockMapper.query4Count(qo);
        System.out.println(rows);
        if (rows == 0){
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = productStockMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,data);
    }

    @Override
    public void stockIncomeBill(StockIncomeBill stockIncomeBill) {
        List<StockIncomeBillItem> items = stockIncomeBill.getItems();
        for (StockIncomeBillItem item : items) {
            ProductStock ps = productStockMapper.selectByProductIdandDepotId(item.getProduct().getId(), stockIncomeBill.getDepot().getId());
            if (ps == null) {
                ps = new ProductStock();
                ps.setPrice(item.getCostPrice());
                ps.setStoreNumber(item.getNumber());
                ps.setAmount(item.getAmount());
                ps.setProduct(item.getProduct());
                ps.setDepot(stockIncomeBill.getDepot());
                productStockMapper.insert(ps);
            } else {
                //不存在就修改库存信息:数量/价格/总额
                ps.setStoreNumber(ps.getStoreNumber().add(item.getNumber()));//商品数量
                //仓库商品单价
                ps.setPrice(ps.getAmount().add(item.getAmount()).divide(ps.getStoreNumber(), 2, BigDecimal.ROUND_HALF_UP));
                //仓库商品总价
                ps.setAmount(ps.getStoreNumber().multiply(ps.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
                //更新数据
                productStockMapper.updateByPrimaryKey(ps);
            }

        }
    }

    @Override
    public void stockOutcomeBill(StockOutcomeBill stockOutcomeBill) {
        List<StockOutcomeBillItem> items = stockOutcomeBill.getItems();
        for (StockOutcomeBillItem item : items) {
            ProductStock ps = productStockMapper.selectByProductIdandDepotId(item.getProduct().getId(), stockOutcomeBill.getDepot().getId());
            if (ps == null){
                throw new LogicException("商品["+item.getProduct().getName()+"]不存在仓库["+stockOutcomeBill.getDepot().getName()+"]中");
            }
            if (ps.getStoreNumber().compareTo(item.getNumber())<0){
                throw new LogicException("商品["+item.getProduct().getName()+"]在仓库["+stockOutcomeBill.getDepot().getName()+"]中"
                        +ps.getProduct().getName()+"数量不足:"+item.getProduct().getName());
            }
            ps.setStoreNumber(ps.getStoreNumber().subtract(item.getNumber()));
            ps.setAmount(ps.getStoreNumber().multiply(ps.getPrice()));
            productStockMapper.updateByPrimaryKey(ps);

            //生成销售帐
            SaleAccount saleAccount = new SaleAccount();
            saleAccount.setSaleman(stockOutcomeBill.getInputUser());
            saleAccount.setClient(stockOutcomeBill.getClient());
            saleAccount.setNumber(item.getNumber());
            saleAccount.setCostPrice(ps.getPrice());
            saleAccount.setProduct(saleAccount.getProduct());
            saleAccount.setSalePrice(item.getSalePrice());
            saleAccount.setCostAmount(saleAccount.getCostPrice().multiply(saleAccount.getNumber()).setScale(2,BigDecimal.ROUND_HALF_UP));
            saleAccount.setSaleAmount(saleAccount.getSalePrice().multiply(saleAccount.getNumber()).setScale(2,BigDecimal.ROUND_HALF_UP));
            saleAccount.setVdate(new Date());

            saleAccountMapper.insert(saleAccount);

        }
    }
}
