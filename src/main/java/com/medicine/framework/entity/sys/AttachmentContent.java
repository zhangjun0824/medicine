package com.medicine.framework.entity.sys;

import java.util.Date;

import com.medicine.framework.base.BaseEntity;
import com.medicine.framework.mybatis.annotations.PK;

/**
 * 附件内容
 *
 */
@PK()
public class AttachmentContent extends BaseEntity {


    private String attachmentId;

    // 附件名称
    private String name;

    // 附件存放地址
    private String url;

    // 附件类型
    private String type;
    
    // 附件格式
    private String format;

    // 上传人员
    private String userId;

    // 上传时间
    private Date uploadDate;

    private Long length;

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof AttachmentContent))
        {
            return false;
        }

        AttachmentContent a = (AttachmentContent) obj;

        if(this.getId().equals(a.getId()))
        {
            return true;
        }
        return false;
    }

}
