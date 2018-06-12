package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
@Setter
@Getter
public class BaseBillDomain extends BaseDomain{

    public static final int STATUS_NOMAL = 0;//未审核
    public static final int STATUS_AUDIT = 1;//已审核

    private String sn;//订单编号
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vdate;//录入时间
    private int status = STATUS_NOMAL;//审核状态
    private BigDecimal totalAmount;//总金额
    private BigDecimal totalNumber;//商品总数量
    private Date auditTime;//审核时间
    private Date inputTime;//录入时间
    private Employee inputUser;//录入人
    private Employee auditor;//审核人
}
