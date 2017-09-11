package com.medicine.framework.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.medicine.framework.entity.sys.AttachmentContent;
import com.medicine.framework.entity.user.User;
import com.medicine.framework.service.sys.AttachmentService;
import com.medicine.framework.util.State;
import com.medicine.framework.util.page.PageInfo;

/**
 * 基础Controller
 *
 */
public class BaseController extends MultiActionController {

    protected final static String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";

    protected PageInfo pageInfo;

	@Autowired
	private AttachmentService attachmentService;
	
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat format = new SimpleDateFormat(DATA_FORMAT);
        CustomDateEditor dateEditor = new CustomDateEditor(format, true);
        binder.registerCustomEditor(Date.class, dateEditor);
        super.initBinder(request, binder);
        pageInfo = new PageInfo(request);
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
    protected UserDetails getUserDetails() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    protected String getUserName() {
        Object obj = (Object) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj instanceof User) {
            return ((UserDetails) obj).getUsername();
        } else {
            return obj.toString();
        }

    }

    protected String getUserId() {
        Object obj = (Object) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj instanceof User) {
            return ((User) obj).getId();
        } else {
            return null;
        }

    }

    /**
     * 处理验证错误
     *
     * @param result
     * @param state
     * @param bErrors
     * @return
     */
    protected ModelAndView doBindingResult(ModelAndView result, State state, BindingResult bErrors) {
        List<ObjectError> ObjectErrorList = bErrors.getAllErrors();
        StringBuffer message = new StringBuffer();
        for (ObjectError objectError : ObjectErrorList) {
            message.append(objectError.getDefaultMessage());
            message.append("\n");
        }
        state.setCode("0");
        state.setMsg(message.toString());
        result.addObject("state", state);
        return result;
    }

    /**
     * 下载返回错误文件
     *
     * @param message
     * @return
     */
    protected ResponseEntity<byte[]> errorFile(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("Model", "error_404");
        return new ResponseEntity<byte[]>(message.getBytes(),headers, HttpStatus.CREATED);
    }
}
