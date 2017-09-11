package com.medicine.framework.base;

import java.io.Serializable;
import java.util.Date;


/**
 * 基础实体
 *
 */
public class BaseEntity implements Serializable {


    private String id;

    private String name;

    private String code;

    private String createUserId;

    private Date createDate;

    private String editUserId;

    private Date editDate;

    private Boolean deleteFlag;

    private String memo;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getEditUserId() {
        return editUserId;
    }

    public void setEditUserId(String editUserId) {
        this.editUserId = editUserId;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	public Boolean getDeleteFlag() {
        if (deleteFlag == null) {
            deleteFlag = false;
        }
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

}
