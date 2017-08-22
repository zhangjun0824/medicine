package com.medicine.framework.util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 返回状态
 *
 */
@XmlRootElement
public class State implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -777052801484691955L;


	private String code = "0";


    private String msg = "";


    private String debugmsg;


    private String editDate;

    public State() {

    }


    public State(String code) {
        this.code = code;
    }

    @XmlElement(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlElement(name = "msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @XmlElement(name = "debugmsg")
    public String getDebugmsg() {
        return debugmsg;
    }

    public void setDebugmsg(String debugmsg) {
        this.debugmsg = debugmsg;
    }

    public void clear() {
        code = "";
        msg = "";
        debugmsg = "";
        editDate = "";
    }

    @XmlElement(name = "editDate")
    public String getEditDate() {
        return editDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }
}
