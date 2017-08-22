package com.medicine.framework.base;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicine.framework.dao.sys.AttachmentMapper;
import com.medicine.framework.entity.sys.Attachment;
import com.medicine.framework.entity.user.User;
import com.medicine.framework.util.SnowflakeIdWorker;

/**
 * 基础Service
 *
 */
public class BaseService {
    
    @Autowired
	protected ObjectMapper objectMapper;
    
    @Autowired
    private AttachmentMapper attachmentMapper;

    private SnowflakeIdWorker idWorker = SnowflakeIdWorker.getInstance();

    protected Authentication authentication;


    public SnowflakeIdWorker getIdWorker() {
        return this.idWorker;
    }

    public void setIdWorker(SnowflakeIdWorker idWorker) {
        this.idWorker = idWorker;
    }
	
    protected Attachment addAttachment(String type) {
        User user = (User) getUserDetails();
        Attachment attachment = new Attachment();
        attachment.setType(type);
        Date date = new Date();
        attachment.setCreateUserId(user.getId());
        attachment.setCreateDate(date);
        attachment.setEditUserId(user.getId());
        attachment.setEditDate(date);
        attachmentMapper.addAttachment(attachment);
        return attachment;
    }
    
    /**
     * 获取当前用户
     *
     * @return
     */
    protected UserDetails getUserDetails() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return user;
    }
    
    /**
     * 获取当前用户
     *
     * @return
     */
    protected User getUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    protected String getUserName() {
        Object obj = (Object) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (obj instanceof User) {
            return ((UserDetails) obj).getUsername();
        } else {
            return obj.toString();
        }

    }

    protected String getUserId() {
        if (SecurityContextHolder.getContext()
                .getAuthentication() != null) {
            authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
        }
        if (authentication == null) {
            return null;
        }
        Object obj = (Object) authentication.getPrincipal();

        if (obj instanceof User) {
            return ((User) obj).getId();
        } else {
            return null;
        }

    }

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}


}
