package com.medicine.framework.vo.sys;

import com.medicine.framework.entity.sys.AttachmentContent;

public class AttachmentContentVO extends  AttachmentContent{


    private String userName;

    private String displayName;

    private int version;


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

  

}
