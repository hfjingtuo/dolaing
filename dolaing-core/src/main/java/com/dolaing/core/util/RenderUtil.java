package com.dolaing.core.util;

import com.alibaba.fastjson.JSON;
import com.dolaing.core.exception.DolaingException;
import com.dolaing.core.exception.DolaingExceptionEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 渲染工具类
 *
 * @author zx
 * @date 2018-08-25 14:13
 */
public class RenderUtil {

    /**
     * 渲染json对象
     */
    public static void renderJson(HttpServletResponse response, Object jsonObject) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(jsonObject));
        } catch (IOException e) {
            throw new DolaingException(DolaingExceptionEnum.WRITE_ERROR);
        }
    }
}
