package cn.wolfcode.wms.query;

import cn.wolfcode.wms.domain.BaseBillDomain;
import cn.wolfcode.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class OrderBillQueryObject extends BaseBillQueryObject{

    private Long supplierId= -1L;
}
