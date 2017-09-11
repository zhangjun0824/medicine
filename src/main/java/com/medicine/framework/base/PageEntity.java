package com.medicine.framework.base;

/**
 * 分页实体
 *
 */
public class PageEntity {
    /**
     * 总的记录数
     */
    private int totalRecordNum = 0;

    /**
     * 总页数
     */
    private int totalPageNum = 1;

    /**
     * 当前页数
     */
    private int curPageNo = 1;

    /**
     * 每页大小
     * 默认为 20 条记录
     */
    private int pageSize = 20;

    /**
     * 记录偏移数
     */
    private int offset;

    /**
     * 前一页
     */
    private int prePageNo;

    /**
     * 后一页
     */
    private int nextPageNo;

    /**
     * 第一页
     */
    private int firstPageNo;

    /**
     * 最后一页
     */
    private int lastPageNo;

    /**
     * 连接地址
     */
    protected String linkUrl = "";

    /**
     * 分页控制的字符串
     */
    protected String pageController;

    public int getTotalRecordNum() {
        return totalRecordNum;
    }

    public void setTotalRecordNum(int totalRecordNum) {
        this.totalRecordNum = totalRecordNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getCurPageNo() {
        return curPageNo;
    }

    public void setCurPageNo(int curPageNo) {
        this.curPageNo = curPageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPrePageNo() {
        return prePageNo;
    }

    public void setPrePageNo(int prePageNo) {
        this.prePageNo = prePageNo;
    }

    public int getNextPageNo() {
        return nextPageNo;
    }

    public void setNextPageNo(int nextPageNo) {
        this.nextPageNo = nextPageNo;
    }

    public int getFirstPageNo() {
        return firstPageNo;
    }

    public void setFirstPageNo(int firstPageNo) {
        this.firstPageNo = firstPageNo;
    }

    public int getLastPageNo() {
        return lastPageNo;
    }

    public void setLastPageNo(int lastPageNo) {
        this.lastPageNo = lastPageNo;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getPageController() {
        return pageController;
    }

    public void setPageController(String pageController) {
        this.pageController = pageController;
    }

}
