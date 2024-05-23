package net.rootkim.core.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/13
 */
public class MapUtil {

    /**
     * 实体类转为Map
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map convertClassToMap(Object obj) throws IllegalAccessException {
        Map<Object, Object> resultMap = new HashMap<>();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            resultMap.put(field.getName(), field.get(obj));
        }
        return resultMap;
    }
}
