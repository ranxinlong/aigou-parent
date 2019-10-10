package cn.itsource.basic.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页的工具类
 * @author ranmoumou
 */
public class PageList<T> {
    private Integer total = 0;
    private List<T> rows = new ArrayList<T>();

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public PageList() {
    }

    public PageList(Integer total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
