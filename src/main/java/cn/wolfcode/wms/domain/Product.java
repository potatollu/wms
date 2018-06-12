package cn.wolfcode.wms.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;

@Setter
@Getter
public class Product extends BaseDomain {
    private String name;

    private String sn;

    private BigDecimal costPrice;

    private BigDecimal salePrice;

    private String imagePath;

    private String intro;

    private Long brandId;

    private String brandName;

    public String getSmallImagePath() {
        String smallImage = "";
        if (StringUtils.hasLength(imagePath)) {
            int index = imagePath.indexOf(".");
            smallImage = imagePath.substring(0, index) + "_small" + imagePath.substring(index);
        }
        return smallImage;
    }

    public String getProductInfo(){
        HashMap<Object, Object> info = new HashMap<>();
        info.put("id",getId());
        info.put("name",name);
        info.put("costPrice",costPrice);
        info.put("salePrice",salePrice);
        info.put("brandName",brandName);
        //返回一个Json对象
        return JSON.toJSONString(info);
    }

}