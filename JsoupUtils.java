package com.king.lostisland.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.king.lostisland.annontation.Pick;
import com.king.lostisland.constant.Attrs;
import com.king.lostisland.utils.klog.KLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 杭州瑟福科技有限公司
 * 作者: 朱凯文
 * 时间: 2018/1/6
 * 描述:
 */
public class JsoupUtils {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public <T> T fromHTML(String html, Class<T> clazz) {
        T t = null;
        try {
            Document rootDocument = Jsoup.parse(html);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Pick pickClazz = clazz.getDeclaredAnnotation(Pick.class);
                t = clazz.getDeclaredConstructor().newInstance();
                String clazzAttr = pickClazz.attr();
                String clazzValue = pickClazz.value();
                Element rootNode = getRootNode(rootDocument, clazzValue);
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    dealFieldType(field, rootNode, t);
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException
                | InstantiationException | IllegalAccessException |
                ClassNotFoundException e) {
            e.printStackTrace();
        }
        return t;
    }

    private String setMethodName(Field field) {
        String SET_HEADER = "set";
        String methodName = field.getName();
        if (!TextUtils.isEmpty(methodName)) {
            String firstChar = methodName.substring(0, 1);
            methodName = methodName.replaceFirst(firstChar, firstChar.toUpperCase());
        }
        return SET_HEADER + methodName;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Field dealFieldType(Field field, Element rootNode, Object t) throws IllegalAccessException
            , InstantiationException, ClassNotFoundException {
        field.setAccessible(true);
        Pick pickField = field.getDeclaredAnnotation(Pick.class);
        if (pickField == null) return null;
        String fieldValue = pickField.value();
        String fieldAttr = pickField.attr();
        Class<?> type = field.getType();
        if (type == String.class) {
            String nodeValue = getStringNode(rootNode, fieldAttr, fieldValue);
            field.set(t, nodeValue);
        } else if (type == List.class) {
            Elements elements = getListNode(rootNode, fieldValue);
            field.set(t, new ArrayList<>());
            List<Object> fieldList = (List<Object>) field.get(t);
            for (Element ele : elements) {
                Type genericType = field.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    Type[] args = ((ParameterizedType) genericType).getActualTypeArguments();
                    Class<?> aClass = Class.forName(((Class) args[0]).getName());
                    Object object = aClass.newInstance();
                    Field[] childFields = aClass.getDeclaredFields();
                    for (Field childField : childFields) {
                        dealFieldType(childField, ele, object);
                    }
                    fieldList.add(object);
                }
            }
            field.set(t, fieldList);
        }
        return field;
    }

    private Elements getListNode(Element rootNode, String fieldValue) {
        return rootNode.select(fieldValue);
    }

    /**
     * 获取返回值为String的节点
     */
    private String getStringNode(Element rootNode, String fieldAttr, String fieldValue) {
        if (fieldValue.contains(":first")) {
            fieldValue = fieldValue.replace(":first", "");
            if (Attrs.TEXT.equals(fieldAttr))
                return rootNode.select(fieldValue).first().text();
            return rootNode.select(fieldValue).first().attr(fieldAttr);
        } else if (fieldValue.contains(":last")) {
            fieldValue = fieldValue.replace(":last", "");
            if (Attrs.TEXT.equals(fieldAttr))
                return rootNode.select(fieldValue).last().text();
            return rootNode.select(fieldValue).last().attr(fieldAttr);
        } else {
            if (Attrs.TEXT.equals(fieldAttr))
                return rootNode.select(fieldValue).text();
            return rootNode.select(fieldValue).attr(fieldAttr);
        }
    }

    /**
     * 获取根节点
     */
    private Element getRootNode(Document rootDocument, String value) {
        return rootDocument.selectFirst(value);
    }
}
