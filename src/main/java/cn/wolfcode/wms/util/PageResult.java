package cn.wolfcode.wms.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Setter
@Getter
@ToString
public class PageResult {
    public static final PageResult EMPTY_PAGE = new PageResult(1,1,0, Collections.EMPTY_LIST);
    private int currentPage;
    private int pageSize;

    private Integer rows;
    private List<?> data;

    private int prevPage;
    private int nextPage;
    private int endPage;

    public PageResult(int currentPage,int pageSize,Integer rows,List<?> data){
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.rows = rows;
        this.data = data;
        if (pageSize >= rows){
            this.prevPage = 1;
            this.nextPage = 1;
            this.endPage = 1;
            return;
        }
        this.endPage = rows % pageSize == 0 ? rows / pageSize : rows /pageSize + 1;
        this.prevPage = currentPage - 1 > 0 ? currentPage - 1 : 1;
        this.nextPage = currentPage  + 1 < endPage ? currentPage + 1 : endPage;
    }
}
