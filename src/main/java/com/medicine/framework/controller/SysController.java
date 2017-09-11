package com.medicine.framework.controller;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.medicine.framework.base.BaseController;
import com.medicine.framework.entity.privilege.Privilege;
import com.medicine.framework.entity.sys.Attachment;
import com.medicine.framework.entity.sys.AttachmentContent;
import com.medicine.framework.entity.user.User;
import com.medicine.framework.service.privilege.PrivilegeService;
import com.medicine.framework.service.sys.AttachmentService;
import com.medicine.framework.service.user.UserService;
import com.medicine.framework.util.CharsetUtil;
import com.medicine.framework.util.GlobalProperties;
import com.medicine.framework.util.State;
import com.medicine.framework.util.SysUtil;
import com.medicine.framework.vo.sys.AttachmentContentVO;

@Controller
@RequestMapping("/sys")
public class SysController extends BaseController {

	@Autowired
	private PrivilegeService privilegeSerivce;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
	private UserService userService;

	@RequestMapping("main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = new ModelAndView("/sys/main");
		User user = (User) getUserDetails();
		result.addObject("user", user);
		return result;
	}

	@RequestMapping("login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = new ModelAndView("/sys/login");
		return result;
	}
	/**
	 * 查询菜单列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("queryMenus")
	public ModelAndView queryMenus(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = new ModelAndView();
		User user = (User) getUserDetails();
		List<Privilege> sysMenus = privilegeSerivce.getUserDefaultMenuByUserId(user);
		result.addObject("menus", sysMenus);
		return result;
	}
	/**
	 * 获取当前用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getCurrentUser")
	public ModelAndView getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = new ModelAndView();
		User user = (User) getUserDetails();
        if(StringUtils.isNotBlank(user.getAttachmentId())){
        	List<AttachmentContent> list=attachmentService.getAttachmentContentByAttachmentId(user.getAttachmentId());
        	user.setCode("download/" + list.get(0).getId());
        }
		result.addObject("user", user);
		return result;
	}
	/**
	 * 上传附件
	 * 
	 * @param request
	 * @param at
	 * @param multipartFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upload")
	public ModelAndView uploadAttachment(HttpServletRequest request, 
			Attachment at,@RequestParam("file") MultipartFile[] fileList) throws Exception {

		ModelAndView result = new ModelAndView();
		State state = new State("1");

		if (at.getId() != null) {
			attachmentService.addAttachmentContent(fileList,at);
		} else {
			at = attachmentService.addAttachment(fileList,at);
		}
		state.setCode("0");
		result.addObject("att", at);
		result.addObject("state", state);
		return result;
	}
    /**
     * 获取附件明细列表
     *
     * @param request
     * @param response
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws JSONException
     */
    @RequestMapping(value = "/attachmentContentList")
    public ModelAndView getAttachmentContentList(HttpServletRequest request,
                                                 HttpServletResponse response, String attachmentId)
            throws IllegalAccessException, InvocationTargetException {
        ModelAndView result = new ModelAndView();
        State state = new State("1");
        List<AttachmentContent> list = attachmentService.getAttachmentContentByAttachmentId(attachmentId);
        List<AttachmentContentVO> avList = new ArrayList<>(); 
        for (AttachmentContent ac : list) {
            AttachmentContentVO av = new AttachmentContentVO();
            User user = userService.getUserById(ac.getUserId());

            av.setId(ac.getId());
            av.setName(ac.getName());
            av.setType(ac.getType());
            av.setFormat(ac.getFormat());
            av.setUserId(ac.getUserId());
            av.setMemo(ac.getMemo());
            av.setUserName(user != null ? user.getUsername() : "");
            av.setDisplayName(user != null ? user.getName() : "");
            av.setUploadDate(ac.getUploadDate());
            av.setUrl("/sys/download/" + ac.getId());
            av.setLength(ac.getLength());

            avList.add(av);
        }
        result.addObject("contentList", avList);
        result.addObject("state", state);
        return result;
    }
    /**
     * 下载附件
     * 
     * @param request
     * @param response
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadAttachment(HttpServletRequest request,
                                                     HttpServletResponse response, @PathVariable String id,
                                                     @RequestParam(value = "type", required = false) String type)
            throws Exception {
        HttpHeaders headers = new HttpHeaders();
        String attachmentPath = GlobalProperties.getGlobalProperty("attachmentPath");
        AttachmentContent attachmentContent = attachmentService.getAttachmentContentById(id);
        if (attachmentContent == null) {
            String message = "附件不存在！";
            errorFile(message);
        }
        String url = attachmentContent.getUrl();
        String attachmentFilePath = SysUtil.encodePath(attachmentPath + "/" + url);
        if (!StringUtils.isEmpty(type) && type.equals("lit")) {
            attachmentFilePath = SysUtil.encodePath(SysUtil.getLitUrl(attachmentFilePath, attachmentPath));
        }
        File file = new File(CharsetUtil.toUTF_8(attachmentFilePath));
        if (!file.exists()) {
            String message = "附件不存在！";
            errorFile(message);
        }
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment",new String(file.getName().getBytes("UTF-8"), "iso-8859-1"));
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
    }
    /**
     * 删除附件
     * @param request
     * @param response
     * @param attachmentId
     * @param contentIds
     * @return
     */
    @RequestMapping(value = "/delAttachment")
    public ModelAndView delAttachment(HttpServletRequest request,HttpServletResponse response, String attachmentId,String contentIds){
        ModelAndView result = new ModelAndView();
        State state = new State("1");
        attachmentService.delAttachment(attachmentId,contentIds);
        result.addObject("state", state);
        return result;
    }
}
