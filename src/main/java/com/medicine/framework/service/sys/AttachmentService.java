package com.medicine.framework.service.sys;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.medicine.framework.base.BaseService;
import com.medicine.framework.dao.sys.AttachmentMapper;
import com.medicine.framework.entity.sys.Attachment;
import com.medicine.framework.entity.sys.AttachmentContent;
import com.medicine.framework.util.SysUtil;

/**
 * 附件
 */
@Service
public class AttachmentService extends BaseService {

    @Autowired
    private AttachmentMapper attachmentMapper;


    public void addAttachmentContent(AttachmentContent ac) {
        attachmentMapper.addAttachmentContent(ac);
    }

	public Attachment addAttachment(MultipartFile[] fileList,Attachment attachment) {
		Attachment at = super.addAttachment(attachment.getType());
        addAttachmentContent(fileList,at);
        return at;
	}
	
    public void addAttachmentContent(MultipartFile[] fileList,Attachment at) {
        if (fileList.length>0) {
        	for (MultipartFile file : fileList) {
        		String lastName = SysUtil.checkFormat(file.getOriginalFilename());
        		String type =checkFileType(lastName.toLowerCase());
        		String num = getUserId().toString();
        		String url = SysUtil.uploadFile(type, num, file);
        		Date date = new Date();
        		AttachmentContent ac = new AttachmentContent();
        		ac.setAttachmentId(at.getId());
        		ac.setName(file.getOriginalFilename());
        		ac.setType(type);
        		ac.setFormat(SysUtil.checkFormat(file.getOriginalFilename()));
        		ac.setUrl(type + "/" + url + "."+ SysUtil.checkFormat(file.getOriginalFilename()));
        		ac.setUserId(getUserId());
        		ac.setUploadDate(date);
        		ac.setLength(file.getSize());
        		attachmentMapper.addAttachmentContent(ac);
			}
        }
    }

	public Attachment selectAttachmentById(String id) {
		return attachmentMapper.selectAttachmentById(id);
	}

	public AttachmentContent getAttachmentContentById(String id) {
		return attachmentMapper.selectAttachmentContentById(id);
	}

	public List<AttachmentContent> getAttachmentContentByAttachmentId(String attachmentId) {
		return attachmentMapper.selectAttachmentContentByAttachmentId(attachmentId);
	}
	
    private String checkFileType(String fileType) {
        String type = "";
        if ("jpeg".equals(fileType) || "png".equals(fileType)
                || "gif".equals(fileType) || "tiff".equals(fileType)
                || "bmp".equals(fileType)) {
            type = "picture";
        } else if ("wav".equals(fileType) || "avi".equals(fileType)
                || "ram".equals(fileType) || "rm".equals(fileType)
                || "asf".equals(fileType) || "mov".equals(fileType)
                || "mp4".equals(fileType)) {
            type = "video";
        } else if ("dwg".equals(fileType) || "dxf".equals(fileType)) {
            type = "paper";
        } else if ("b3d".equals(fileType)) {
            type = "model";
        } else {
            type = "others";
        }
        return type;
    }

	public void delAttachment(String attachmentId, String contentIds) {
		if(StringUtils.isNotBlank(contentIds)){
			String ids[]=contentIds.split(",");
			attachmentMapper.delAttachmentByContent(ids);
		}
		if(StringUtils.isNotBlank(attachmentId)){
			attachmentMapper.delAttachmentByAttachment(attachmentId);
		}
	}

}
