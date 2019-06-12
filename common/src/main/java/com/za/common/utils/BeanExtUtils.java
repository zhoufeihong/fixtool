package com.za.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class BeanExtUtils extends BeanUtils {

    /**
     * 浅复制生成对象
     *
     * @param source      源对象
     * @param targetClass 目标对象类型
     * @param <T>
     * @return 返回目标对象实例
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        T t = instantiateClass(targetClass);
        copyProperties(source, t);
        return t;
    }

    /**
     * 浅复制生成对象集合
     *
     * @param sourceList  源对象集合
     * @param targetClass 目标对象类型
     * @param <T>
     * @return 目标对象集合实例
     */
    public static <I, T> List<T> copyPropertiesOfList(List<I> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        List<T> resultList = new ArrayList<>(sourceList.size());
        addResult(sourceList, targetClass, resultList);
        return resultList;
    }

    /**
     * 浅复制生成对象集合
     *
     * @param sourceList  源对象集合
     * @param targetClass 目标对象类型
     * @param <T>
     * @return 目标对象集合实例
     */
    public static <I, T> Set<T> copyPropertiesOfList(Set<I> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptySet();
        }
        Set<T> resultSet = new LinkedHashSet<>(sourceList.size());
        addResult(sourceList, targetClass, resultSet);
        return resultSet;
    }

    private static <T> void addResult(Collection<?> sourceList, Class<T> targetClass, Collection<T> resultCollection) {
        for (Object o : sourceList) {
            T t = instantiateClass(targetClass);
            copyProperties(o, t);
            resultCollection.add(t);
        }
    }

}
