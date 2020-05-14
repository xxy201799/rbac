package com.xxy.common.core.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SpringUtil {
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
        Class myCalss = src.getClass();
        Field[] fs = myCalss.getDeclaredFields();// 获取PrivateClass所有属性
        for (int i = 0; i < fs.length; i++) {
            fs[i].setAccessible(true);// 将目标属性设置为可以访问
        }
        System.out.println(fs.length);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy,MM,dd,HH,mm");
        Arrays.stream(fs)
                .forEach(srcField -> {
                            try {
                                Field targetField = target.getClass().getDeclaredField(srcField.getName());
                                targetField.setAccessible(true);
                                //对没有赋值的type进行额外的操作
                                if (targetField.get(target) == null || targetField.get(target).equals("")) {
//                                     对时间的转换处理
                                    if (srcField.getName().toLowerCase().contains("time")) {
                                        Object obj = srcField.get(src);
                                        if (obj != "" && obj != null && srcField.getGenericType().toString().equals("class java.time.LocalDateTime")) {
                                            LocalDateTime localDateTime = (LocalDateTime) obj;
                                            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                                            targetField.set(target, dateFormat.format(date));
                                        }
                                    }
                                    if (targetField.getGenericType().toString().equals("class java.time.LocalDateTime")) {
                                        Object obj = srcField.get(src);
                                        System.out.println(obj);
                                        if (obj != "" && obj != null) {
                                            String time = (String) obj;
                                            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                            LocalDateTime localDateTime = LocalDateTime.parse(time, df);
                                            targetField.set(target, localDateTime);
                                        }
                                        //对string类型的处理
                                    }
                                    if (targetField.getGenericType().toString().equals("class java.lang.String")) {
//                                    targetFieldClass.isInstance(String.class) 这个方法不好使
                                        // field.get(object o ) 这个方法是直接拿值 如果没有会出现空指针异常  所以 fieldget-> instance 或者 class==String.calss的思路都是行不通的
                                        Object obj = srcField.get(src);
                                        targetField.set(target, String.valueOf(obj));
                                    }
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (NoSuchFieldException e) {
                            }
                        }
                );
    }


    public static ArrayList copyArrays(List originList, Object targetObject) {
        Class targetClass = targetObject.getClass();
        ArrayList targetList = new ArrayList();
        originList.forEach(element -> {
            try {
                Object target = targetClass.getConstructor().newInstance();
                SpringUtil.copyPropertiesIgnoreNull(element, target);
                targetList.add(target);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return targetList;
    }

    public static String yyyyMMdd() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String yyyyMMdd(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String yyyyMM() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String yyyyMM(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String yyyy() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Calendar calendar = Calendar.getInstance();
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String yyyy(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }
}



