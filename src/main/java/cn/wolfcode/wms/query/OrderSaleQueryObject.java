package cn.wolfcode.wms.query;

import cn.wolfcode.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Setter
@Getter
public class OrderSaleQueryObject extends QueryObject {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private String keyword;

    private Long clientId = -1L;

    private Long brandId = -1L;

    private String groupType = "e.name";

    //使用一个Map集合存放分组的类型
    public static Map<String, String> groupTypeMap = new LinkedHashMap<>();

    static {
        groupTypeMap.put("e.name", "销售员");
        groupTypeMap.put("p.name", "商品名称");
        groupTypeMap.put("p.brandName", "商品品牌");
        groupTypeMap.put("c.name", "客户");
        groupTypeMap.put("DATE_FORMAT(sale.vdate,'%Y-%m')", "日期(月)");
        groupTypeMap.put("DATE_FORMAT(sale.vdate,'%Y-%m-%d')", "日期(天)");
    }

    public Date getEndDate() {
        return endDate != null ? DateUtil.getEndDate(endDate) : null;
    }

    public String getGroupType() {
        return empty2Null(groupType);
    }

    public String getKeyword() {
        return empty2Null(keyword);
    }
}
