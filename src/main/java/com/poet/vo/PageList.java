package com.poet.vo;

import java.util.List;

/**
 * Created by guzy on 16/7/24.
 */
public class PageList<T> {
    private List<T> list;

    private int count;

    private int pages;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
