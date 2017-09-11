package com.medicine.framework.dao.sys;

import java.util.List;

import com.medicine.framework.entity.sys.Attachment;
import com.medicine.framework.entity.sys.AttachmentContent;

/**
 * 附件
 *
 */
public interface AttachmentMapper {


	void addAttachment(Attachment attachment);
	
	void addAttachmentContent(AttachmentContent ac);

	Attachment selectAttachmentById(String id);

	AttachmentContent selectAttachmentContentById(String id);

	List<AttachmentContent> selectAttachmentContentByAttachmentId(String attachmentId);

	void delAttachmentByContent(String[] ids);

	void delAttachmentByAttachment(String attachmentId);

}
