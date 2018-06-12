package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeQueryObject extends QueryObject{
    private String keyword;
    private Long deptId;

    public String getKeyword() {
        return empty2Null(keyword);
    }
}
