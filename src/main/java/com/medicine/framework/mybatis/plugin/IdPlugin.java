package com.medicine.framework.mybatis.plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.medicine.framework.mybatis.annotations.PK;
import com.medicine.framework.util.ReflectHelper;
import com.medicine.framework.util.SnowflakeIdWorker;

@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class IdPlugin implements Interceptor {

    private static String dialect = "";

    public Object intercept(Invocation invocation) throws Throwable {

    	MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object entity = invocation.getArgs()[1];

        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        
        if (sqlCommandType == SqlCommandType.INSERT) {    //执行插入语句

            if (entity instanceof Map) {
                Map mapEntity = (Map) entity;
                Iterator iterator = mapEntity.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    Object entityObject = mapEntity.get(key);                    
                    if (entityObject instanceof ArrayList) {
                        List entityObjectList = (ArrayList) entityObject;
                        for (int i = 0; i < entityObjectList.size(); i++) {
                            annotationPK(entityObjectList.get(i));
                        }
                    } else {
                        annotationPK(entityObject);
                    }
                }

            } else {
                annotationPK(entity);
            }

        }

        return invocation.proceed();
    }


    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        dialect = properties.getProperty("dialect");
        if (dialect == null || dialect.equals("")) {
            try {
                throw new PropertyException("dialect property is not found!");
            } catch (PropertyException e) {
                e.printStackTrace();
            }
        }
    }

    private void annotationPK(Object entity) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        
    	//是否注解需要生成ID
        if (entity.getClass().isAnnotationPresent(PK.class)) 
        {

            String fieldName = "";

            PK entityPk = (PK) entity.getClass().getAnnotation(PK.class);
            
            if (StringUtils.isNotBlank(entityPk.field())) 
            {
                //类注解
                fieldName = entityPk.field();
                
                if (ReflectHelper.getValueByFieldName(entity, fieldName) == null || StringUtils.isBlank(ReflectHelper.getValueByFieldName(entity, fieldName).toString())) {
                	ReflectHelper.setValueByFieldName(entity, fieldName, SnowflakeIdWorker.getInstance().nextId());
                }
            }
        }
    }
}
