package com.poet.vo;

/**
 * Created by guzy on 16/7/24.
 */
public class Pagination {

    private int pageNo=1;

    private int pageSize=20;

    private int startRow;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        assert pageNo>0;
        this.pageNo = pageNo;
        setStartRow((pageNo-1)*pageSize);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        setStartRow((pageNo-1)*pageSize);
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }
}
