package entity;

import java.util.List;

/**
 * @author bruce
 * @date 2020/1/12 0012 - 下午 6:48
 */
public class PageResult<T> {
    private Long total; //总记录
    private List<T> rows; //当前页的数据列表

    public PageResult() {
    }

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
