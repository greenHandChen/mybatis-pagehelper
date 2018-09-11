package com.ceh.mybatis.mybatis.interceptor.page.mybatis;

/**
 * Created by enHui.Chen on 2018/6/28.
 */
public class PageHelper {
    private int size;
    private int page;
    private int total;

    public PageHelper() {
        this.size = 5;
        this.page = 0;
    }

    public PageHelper(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
