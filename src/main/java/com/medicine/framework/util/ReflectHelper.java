package com.medicine.framework.util;

import java.lang.reflect.Field;

import org.apache.commons.lang3.ArrayUtils;


public class ReflectHelper {

    public static Field getFieldByFieldName(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }

    /**
     * Obj fieldName.
     *
     * @param obj
     * @param fieldName
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getValueByFieldName(Object obj, String fieldName)
            throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field field = getFieldByFieldName(obj, fieldName);
        Object value = null;
        if (field != null) {
            if (field.isAccessible()) {
                value = field.get(obj);
            } else {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }
        return value;
    }

    /**
     * obj fieldName
     *
     * @param obj
     * @param fieldName
     * @param value
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setValueByFieldName(Object obj, String fieldName, Object value) throws SecurityException, NoSuchFieldException,IllegalArgumentException, IllegalAccessException {
        //Field field = obj.getClass().getDeclaredField(fieldName);  
        Field field = getDeclaredField(obj, fieldName);
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }

    /**
     * 取得class包含父类的Field
     *
     * @param object
     * @return
     */
    public static Field[] getFieldByObject(Class object) {
        Field[] fields;
        Field[] objectFields = object.getDeclaredFields();
        Field[] superFieldsFields;

        //是否存在父类
        if (object != null && object.getSuperclass() != null && !object.getSuperclass().getName().equals("java.lang.Object")) {
            Class superObject = object.getSuperclass();
            //取得父类的Field
            superFieldsFields = getFieldByObject(superObject);
            if (superFieldsFields != null) {
                //合并父类Field
                fields = ArrayUtils.addAll(objectFields, superFieldsFields);
            } else {
                fields = objectFields;
            }
        } else {
            fields = objectFields;
        }

        return fields;
    }

    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;

        Class<?> clazz = object.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了  

            }
        }
        return null;
    }
}
