package com.dolaing.pay.client.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dolaing.pay.client.entity.zlian.MarginSmsDTO;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:张立华
 * @Description:
 * @Date：Created in 17:44 2018/5/23
 * @Modified By:
 */
public class HttpUtil {
    private HttpClient httpClient = null;
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);


    public void openConnection() {
        BasicHttpParams params = new BasicHttpParams();
        ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager();
        threadSafeClientConnManager.setMaxTotal(400);
        threadSafeClientConnManager.setDefaultMaxPerRoute(400);
        this.httpClient = new DefaultHttpClient(threadSafeClientConnManager, params);
        this.httpClient.getParams().setParameter("http.connection.timeout", Integer.valueOf(20000));
        this.httpClient.getParams().setParameter("http.socket.timeout", Integer.valueOf(20000));
    }

    public String post(String url, String jsonStr) throws HttpException {
        try {
            HttpPost e = new HttpPost(url);
            e.setHeader("Content-Type", "application/json;charset=UTF-8");
            StringEntity se = new StringEntity(jsonStr,"UTF-8");
            se.setContentType("text/json");
            e.setEntity(se);


            HttpResponse response = this.httpClient.execute(e);
            return 200 == response.getStatusLine().getStatusCode()?EntityUtils.toString(response.getEntity(), "UTF-8"):null;
        } catch (Exception var5) {
            throw new HttpException("连接网络超时，请稍后查询交易记录。");
        }
    }


    public void closeConnection() {
        if(null != this.httpClient) {
            this.httpClient.getConnectionManager().shutdown();
        }

    }

    /**
     * @Author: 张立华
     * @Description:
     * @params: *
     * @return: *
     * @Date: 18:04 2018/5/11
     */
    @SuppressWarnings("unused")
    public static Map<String,String> sendMsg(String url, String jsonStr){
        Map<String,String> result = new HashMap<>();
        try {
            HttpUtil http = new HttpUtil();
            http.openConnection();
            String responseMsg;
            responseMsg = http.post(url, jsonStr);
            //logger.info("调用http请求之后响应的内容：" + responseMsg);
            http.closeConnection();
            if("".equals(responseMsg)||responseMsg == null){
                result.put("code","-1");
                result.put("msg","连接网络超时，请稍后查询交易记录。");
                return result;
            }
            result = (Map)JSON.parseObject(responseMsg);

        } catch (HttpException e) {
            logger.error(e.getMessage()+",URL="+url);
            result.put("code","-1");
            result.put("msg",e.getMessage());
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage()+",URL="+url);
            result.put("code","-1");
            result.put("msg",e.getMessage());
            return result;
        }
        return result;
    }

    public static void main(String[] args) {
        MarginSmsDTO marginSmsDTO = new MarginSmsDTO();
        marginSmsDTO.setUserId("1111111111111111111");
        String marginSmsDTOStr = JSONObject.toJSON(marginSmsDTO).toString();
        String url = "http://localhost:8080/gpm-pay/zlpay/marginSms" ;
        Map map = HttpUtil.sendMsg(url ,marginSmsDTOStr);
        System.out.println(map.get("code"));
        System.out.println(map.get("msg"));
    }

}
