package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class StockIncomeBill extends BaseBillDomain{

    private Depot depot;//采购入库单仓库
    //采购订单明细
    private List<StockIncomeBillItem> items = new ArrayList<>();
}
