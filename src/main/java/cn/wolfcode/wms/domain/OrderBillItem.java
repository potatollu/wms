package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class OrderBillItem extends BaseDomain{
    private BigDecimal costPrice;//订单明细的成本价

    private BigDecimal number;//订单明细的订货数量

    private BigDecimal amount;//订单明细的总金额

    private String remark;//订单明细说明

    private Product product;//订单明细对应的货品

    private Long billId;//订单明细对应的订单对象

}
