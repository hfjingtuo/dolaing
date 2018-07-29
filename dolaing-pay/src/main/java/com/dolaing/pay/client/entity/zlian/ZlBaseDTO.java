package com.dolaing.pay.client.entity.zlian;

import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * @Author:张立华
 * @Description:
 * @Date：Created in 19:30 2018/5/11
 * @Modified By:
 */
@Data
public abstract class ZlBaseDTO<T>{
    private static final long serialVersionUID = 1L;
    public final  String separator ="|" ;
    @SuppressWarnings("rawtypes")
    protected Class clazz;

    /**
     * @Author: 张立华
     * @Description: 获取泛型实例
     * @params: *
     * @return: *
     * @Date: 15:39 2018/5/14
     */
    @SuppressWarnings("unchecked")
    public ZlBaseDTO() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<T>) type.getActualTypeArguments()[0];
    }

    /**
     * @Author: 张立华
     * @Description: 通过泛型返回正联接口请求参数字符串
     * @params: *
     * @return: *
     * @Date: 15:28 2018/5/14
     */
    public String toDtoString(T data) {
        Field[] fields = this.clazz.getDeclaredFields();
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            String key = f.getName();
            Object value = null;
            if (! "serialVersionUID".equals(key)) {// 忽略序列化版本ID号
                f.setAccessible(true);// 取消Java语言访问检查
                try {
                    value = f.get(data);
                    value = value == null ? "" : value ; //正联接口尽量不要传 null 字符串过去
                    sbf.append(key+"="+value+this.separator);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
}
