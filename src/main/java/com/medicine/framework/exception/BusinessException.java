package com.medicine.framework.exception;

/**
 * Created by zy on 2017/4/1.
 */
public class BusinessException extends Exception {

    public BusinessException(String message,Boolean prefix)
    {
        super(createFriendlyErrMsg(message,prefix));
    }

    public BusinessException(String message)
    {
        super(createFriendlyErrMsg(message,null));
    }

    public BusinessException(Throwable throwable)
    {
        super(throwable);
    }

    public BusinessException(Throwable throwable, String message)
    {
        super(throwable);
    }

    private static String createFriendlyErrMsg(String contentMsg,Boolean prefix)
    {
        String prefixStr = "";
        String suffixStr = "";
        if(prefix==null){
            prefix=true;
        }
        if(prefix){
            prefixStr = "抱歉，";
            suffixStr = " 请稍后再试或与管理员联系！";
        }

        StringBuffer friendlyErrMsg = new StringBuffer("");

        friendlyErrMsg.append(prefixStr);

        friendlyErrMsg.append(contentMsg);

        friendlyErrMsg.append(suffixStr);

        return friendlyErrMsg.toString();
    }
}
