package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Properties;

@Setter
@Getter
public class QueryObject {
    private int currentPage = 1;
    private int pageSize = 5;

    public int getStart(){
        return (this.currentPage - 1) * this.pageSize;
    }

   protected String empty2Null(String s){
        return StringUtils.hasLength(s) ? s : null;
    }
}
