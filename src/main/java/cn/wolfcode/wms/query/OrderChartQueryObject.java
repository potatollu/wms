package cn.wolfcode.wms.query;

import cn.wolfcode.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Setter
@Getter
public class OrderChartQueryObject extends QueryObject {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private String keyword;

    private Long supplierId = -1L;

    private Long brandId = -1L;

    private String groupType = "p.brandName";

    //使用一个Map集合存放分组的类型
    public static Map<String, String> groupTypeMap = new LinkedHashMap<>();

    static {
        groupTypeMap.put("inputUser.name", "订货人");
        groupTypeMap.put("p.brandName", "商品品牌");
        groupTypeMap.put("p.name", "商品名称");
        groupTypeMap.put("s.name", "供应商");
        groupTypeMap.put("DATE_FORMAT(bill.vdate,'%Y-%m-%d')", "日期(天)");
        groupTypeMap.put("DATE_FORMAT(bill.vdate,'%Y-%m')", "日期(月)");
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
