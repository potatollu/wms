package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
@Setter
@Getter
public class SaleAccount extends BaseDomain{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vdate;

    private BigDecimal number;

    private BigDecimal costPrice;

    private BigDecimal costAmount;

    private BigDecimal salePrice;

    private BigDecimal saleAmount;

    private Product product;

    private Employee saleman;

    private Client client;

}