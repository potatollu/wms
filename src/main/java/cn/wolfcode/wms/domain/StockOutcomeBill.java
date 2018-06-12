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
public class StockOutcomeBill extends BaseDomain{
    public static final int STATUS_NOMAL = 0;
    public static final int STATUS_AUDIT = 1;

    private String sn;//采购入库单编号
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vdate;//采购入库单业务时间

    private int status;//审核状态

    private BigDecimal totalAmount;//采购入库单总金额

    private BigDecimal totalNumber;//采购入库单总数量

    private Date auditTime;//审核时间

    private Date inputTime;//录入时间

    private Employee inputUser;//录入人

    private Employee auditor;//审核人

    private Depot depot;//采购入库单仓库

    private Client client;
    //采购订单明细
    private List<StockOutcomeBillItem> items = new ArrayList<>();
}
