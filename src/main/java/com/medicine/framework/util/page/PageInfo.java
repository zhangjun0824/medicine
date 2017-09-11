package com.medicine.framework.util.page;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;

import com.medicine.framework.util.GlobalProperties;

public class PageInfo implements Serializable {

    private static final long serialVersionUID = 587754556498974978L;

    //pagesize ，每一页显示多少
    private int showCount = 10;
    //总页数
    private int totalPage;
    //总记录数
    private int totalResult;
    //当前页数
    private int currentPage;
    //当前显示到的ID, 在mysql limit 中就是第一个参数.
    private int currentResult;
    private String sortField;
    private String order;

    public PageInfo() {

    }

    public PageInfo(HttpServletRequest request) {

        if (request.getParameter("rows") != null) {
            showCount = Integer.parseInt(request.getParameter("rows"));
        } else {
            String pagesize = GlobalProperties.getGlobalProperty("pagesize");
            if (pagesize != null && NumberUtils.isNumber(pagesize)) {
                showCount = NumberUtils.toInt(pagesize);
            }
        }

//        String iDisplayStart= request.getParameter("iDisplayStart");
//        if(iDisplayStart!=null && !iDisplayStart.equals("") && NumberUtils.isNumber(iDisplayStart)){
//        	currentResult = Integer.parseInt(iDisplayStart);
//        	currentPage = currentResult/showCount+1;
//        }else{
//        	currentResult =1 ;
//        	currentPage	=1;
//        }

        currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
        if (currentPage <= 0) {
            currentPage = 1;
        }
        currentResult = (currentPage - 1) * showCount;
    }


    public int getShowCount() {
        return showCount;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    /*public int getTotalPage() {
        totalPage = totalResult/showCount+1;
        return totalPage;
    }*/
    public int getTotalPage() { //修改的额先不提交
        totalPage = (totalResult + showCount - 1) / showCount;
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(int currentResult) {
        this.currentResult = currentResult;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "PageInfo [showCount=" + showCount + ", totalPage=" + totalPage
                + ", totalResult=" + totalResult + ", currentPage="
                + currentPage + ", currentResult=" + currentResult
                + ", sortField=" + sortField + ", order=" + order + "]";
    }
}
