package com.medicine.framework.entity.sys;

import java.util.List;

import com.medicine.framework.base.BaseEntity;
import com.medicine.framework.mybatis.annotations.PK;


@PK
public class Attachment extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 470232670331828033L;

    //附件类型
    private String type;
    

    private List<AttachmentContent> attachmentContents;


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public List<AttachmentContent> getAttachmentContents() {
		return attachmentContents;
	}


	public void setAttachmentContents(List<AttachmentContent> attachmentContents) {
		this.attachmentContents = attachmentContents;
	}

}
