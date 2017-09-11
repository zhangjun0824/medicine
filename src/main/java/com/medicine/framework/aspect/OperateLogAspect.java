package com.medicine.framework.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.medicine.framework.anno.OperateAnnotation;
import com.medicine.framework.entity.log.OperateLog;
import com.medicine.framework.entity.user.User;
import com.medicine.framework.service.log.OperateLogService;
import com.medicine.framework.util.GlobalProperties;
import com.medicine.framework.util.log.HttpReqUtil;


@Aspect
@Component
public class OperateLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(OperateLogAspect.class);

    @Autowired
    private OperateLogService sysLogService;

    //定义切入点
    @Pointcut("execution(public * com.medicine.*.controller..*(..))")
    public void doAfterReturningPointcut() {
    }

    @Pointcut("execution(public * com.medicine.*.controller..*(..))")
    public void doExceptionPointcut() {
    }

    //标注该方法体为后置通知，当目标方法执行成功后执行该方法体
    @AfterReturning(pointcut = "doAfterReturningPointcut()")
    public void addLogSuccess(JoinPoint joinPoint) {
        try {
            saveSysLogInfo(joinPoint, null, "suc");
        } catch (Exception e) {
            //记录本地异常日志  
            e.printStackTrace();
            logger.error("==后置通知异常==" + e.getMessage());
            logger.error("异常信息:{}", e.getMessage());
        }
    }


    ////标注该方法体为异常通知，当目标方法出现异常时，执行该方法体
    @AfterThrowing(pointcut = "doExceptionPointcut()", throwing = "e")  //注意异常名称要一致
    public void addLogFailure(JoinPoint joinPoint, RuntimeException e) {
        try {
            saveSysLogInfo(joinPoint, e, "fai");
        } catch (Exception ex) {
            //记录本地异常日志  
            e.printStackTrace();
            logger.error("==异常通知  --->异常==" + ex.getMessage());
            logger.error("异常通知  --->异常消息:{}", ex.getMessage());
        }
    }


    //@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void saveSysLogInfo(JoinPoint joinPoint, Throwable e, String type) throws Exception {

        try {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            Object obj = (Object) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User user = new User();

            if (obj instanceof User) {
                user = (User) obj;
            }

            OperateLog systemLog = new OperateLog();
            String ip = HttpReqUtil.getIpAddress(request);
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            String paramContent = paramContent(joinPoint.getArgs(), methodName);
            String logDesc = getControllerMethodDescription(joinPoint);
            Date date = new Date();
            systemLog.setLogDate(date);
            systemLog.setUserId(user.getId());
            systemLog.setUserName(user.getUsername());
            systemLog.setIp(ip);

            if (type.equals("suc")) {
                systemLog.setType(GlobalProperties.getGlobalProperty("sys.suc.mes"));
                systemLog.setInfo("");
            } else if (type.equals("fai")) {
                systemLog.setType(GlobalProperties.getGlobalProperty("sys.fai.mes"));
                systemLog.setInfo(e.getMessage());
            }

            systemLog.setEquipment(HttpReqUtil.getFromMobileRule(request));
            systemLog.setLogClassName(className);
            systemLog.setLogMethod(methodName);
            systemLog.setLogParam(paramContent);
            systemLog.setLogDesc(logDesc);

            sysLogService.saveSysLog(systemLog);
        } catch (DataAccessException ee) {
            ee.printStackTrace();
            if (logger.isWarnEnabled()) {
                logger.info("无法更新用户操作系统信息至数据库");
            }
        } catch (Exception e2) {
            if (logger.isWarnEnabled()) {
                logger.info("无法更新用户操作系统信息至数据库");
            }
        }
    }


    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
//        Object[] arguments = joinPoint.getArgs();  
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                //  Class[] clazzs = method.getParameterTypes();
                if (null != method.getAnnotation(OperateAnnotation.class)) {
                    description = method.getAnnotation(OperateAnnotation.class).desc();
                    break;
                }

            }
        }
        return description;
    }


    /**
     * 使用Java反射来获取被拦截方法的参数值， 将参数值拼接为操作内容
     */
    public String paramContent(Object[] args, String mName) throws Exception {
        if (args == null) {
            return null;
        }
        StringBuffer rs = new StringBuffer();
        rs.append(mName);
        String className = null;
        int index = 1;
        // 遍历参数对象
        for (Object info : args) {
            // 获取对象类型
            if (info != null) {
                className = info.getClass() != null ? info.getClass().getName() : null;
                if (className != null) {
                    className = className.substring(className.lastIndexOf(".") + 1);
                    rs.append("[参数" + index + "，类型：" + className + "，值：");
                }
                // 获取对象的所有方法
                Method[] methods = info.getClass().getDeclaredMethods();
                // 遍历方法，判断get方法
                for (Method method : methods) {
                    String methodName = method.getName();
                    // 判断是不是get方法
                    if (methodName.indexOf("get") == -1) {// 不是get方法
                        continue;// 不处理
                    }
                    Object rsValue = null;
                    try {
                        // 调用get方法，获取返回值
                        rsValue = method.invoke(info);
                        if (rsValue == null) {// 没有返回值
                            continue;
                        }

                    } catch (Exception e) {
                        continue;
                    }
                    // 将值加入内容中
                    rs.append("(" + methodName + " : " + rsValue + ")");
                }
                rs.append("]");
                index++;
            }
        }
        return rs.toString();
    }


}
