package com.muyuan.common.mybatis.id;

import com.muyuan.common.redis.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

@Component
@Order(-10)
@Aspect
@Slf4j
public class IdGeneratorAspect {

    @Before("@annotation(idGenerator)")
    public void setId(JoinPoint point, IdGenerator idGenerator) throws Throwable {
        String idFieldName = idGenerator.filedName();
        final Object[] args = point.getArgs();
        if (args.length != 1) {
            log.error("id generator method must only have one param");
            return;
        }
        Object entity = args[0];
        Method setterMethod;
        Field idField;

        Class target = getClass(entity);
        if (!needSetid(target)) {
            return;
        }

        if (hasIdAnnotation(target)) {
            idFieldName = getIdFieldName(target);
        }
        setterMethod = getIdSetterMethod(target, idFieldName);
        idField = getIdField(target, idFieldName);

        if (idField == null) {
            log.info("id generator not found {} setter method!", idField);
            return;
        }

        if (setterMethod == null) {
            log.info("id generator not found {} setter method!", idFieldName);
            return;
        }
        idField.setAccessible(true);

        if (entity instanceof Collection) {
            for (Object item : (Collection) entity) {
                Object value = idField.get(item);
                if (value == null || value.equals(0)) {
                    setterMethod.invoke(item, IdUtil.createId(target));
                }
            }
        } else if ( entity.getClass().isArray()) {
            assert entity instanceof Object[];
            for (Object item : (Object[]) entity) {
                Object value = idField.get(item);
                if (value == null || value.equals(0)) {
                    setterMethod.invoke(item, IdUtil.createId(target));
                }
            }
        }
        else {
            Object value = idField.get(entity);
            if (value == null || value.equals(0)) {
                setterMethod.invoke(entity, IdUtil.createId(target));
            }
        }

    }

    public Class getClass(Object entity) {
        if (entity instanceof Collection || entity.getClass().isArray()) {
            Object[] entitys;
            if (entity instanceof Collection) {
                entitys = ((Collection) entity).toArray();
            } else {
                entitys = (Object[]) entity;
            }
            if (entitys.length > 0) {
                return null;
            }
             return entitys[0].getClass();
        } else {
            return entity.getClass();
        }
    }

    public boolean needSetid(Class clazz) {
        if (null != clazz && hasIdAnnotation(clazz) && useGeneratedKeys(clazz)) {
            return false;
        }
        return true;
    }

    public boolean hasIdAnnotation(Class clazz) {
        final Id id = AnnotationUtils.findAnnotation(clazz, Id.class);
        if (id != null) {
            return true;
        }
        return false;
    }

    public String getIdFieldName(Class clazz) {
        return AnnotationUtils.findAnnotation(clazz, Id.class).fieldName();
    }

    public boolean useGeneratedKeys(Class clazz) {
        return AnnotationUtils.findAnnotation(clazz, Id.class).useGeneratedKeys();
    }


    public Method getIdSetterMethod(Class target, String idFieldName) {
        Method getter = getIdSetterMethodForClass(target, idFieldName);
        if (null != getter) {
            return getter;
        }
        Class superClass = target.getSuperclass();
        while (Object.class != superClass) {
            getter = getIdSetterMethodForClass(superClass, idFieldName);
            if (null != getter) {
                return getter;
            }
            superClass = superClass.getSuperclass();
        }
        return null;
    }

    public Method getIdSetterMethodForClass(Class target, String idFieldName) {
        final PropertyDescriptor[] beanGetters = ReflectUtils.getBeanGetters(target);
        for (PropertyDescriptor propertyDescriptor : beanGetters) {
            if (propertyDescriptor.getName().equals(idFieldName)) {
                return propertyDescriptor.getWriteMethod();
            }
        }
        return null;
    }


    public Field getIdField(Class target, String idFieldName) {
        Field declaredField = null;
        while (Object.class != target) {
            try {
                declaredField = target.getDeclaredField(idFieldName);
                if (null != declaredField) {
                    return declaredField;
                }
            } catch (NoSuchFieldException e) {
                target = target.getSuperclass();
            }
        }
        return declaredField;
    }

}
