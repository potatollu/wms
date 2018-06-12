package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.domain.OrderBillItem;
import cn.wolfcode.wms.mapper.OrderBillItemMapper;
import cn.wolfcode.wms.mapper.OrderBillMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IOrderBillService;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderBillServiceImpl implements IOrderBillService{
    @Autowired
    private OrderBillMapper orderBillMapper;
    @Autowired
    private OrderBillItemMapper orderBillItemMapper;
    @Override
    public void saveOrUpdate(OrderBill entity) {
        //传入业务时间和录入人
        entity.setInputUser(UserContext.getCurrentuser());//录入人
        entity.setInputTime(new Date());
        //初始化
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmout = BigDecimal.ZERO;
        List<OrderBillItem> items = entity.getItems();
        for (OrderBillItem item : items) {
            //计算总数量
            totalNumber = totalNumber.add(item.getNumber());
            //总价钱,总数*成本价
            totalAmout = totalAmout.add(item.getNumber().multiply(item.getCostPrice()));
        }
        //保存单据信息
        entity.setTotalNumber(totalNumber);
        entity.setTotalAmount(totalAmout);
        if (entity.getId() != null) {
            orderBillMapper.updateByPrimaryKey(entity);
            //先删除在保存
            orderBillItemMapper.deleteByBillId(entity.getId());
        }else {
            orderBillMapper.insert(entity);
        }
            //保存明细信息,保存或更新都要执行
            for (OrderBillItem item : items) {
                item.setAmount(item.getNumber().multiply(item.getCostPrice()));
                item.setBillId(entity.getId());
                orderBillItemMapper.insert(item);
            }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            orderBillMapper.deleteByPrimaryKey(id);
            //删除订单是同时删除明细
            orderBillItemMapper.deleteByBillId(id);
        }
    }

    @Override
    public OrderBill get(Long id) {
        return orderBillMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OrderBill> list() {
        return orderBillMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer rows = orderBillMapper.query4Count(qo);
        System.out.println(rows);
        if (rows == 0){
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = orderBillMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),rows,data);
    }

    @Override
    public void audit(Long id) {
        //查询出审核之前的单据
        OrderBill orderBill = orderBillMapper.selectByPrimaryKey(id);
        if (orderBill.getStatus()==OrderBill.STATUS_NOMAL){
            orderBill.setStatus(OrderBill.STATUS_AUDIT);
            orderBill.setAuditor(UserContext.getCurrentuser());
            orderBill.setAuditTime(new Date());
            orderBillMapper.audit(orderBill);
        }
    }
}
