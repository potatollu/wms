package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class StockOutcomeBillItem extends BaseDomain{
    private BigDecimal salePrice;

    private BigDecimal number;//采购入库单明细数量

    private BigDecimal amount;//采购入库单明细总金额

    private String remark;

    private Product product;//采购明细入库单对应的商品

    private Long billId;//对应的采购入库单对象
}
