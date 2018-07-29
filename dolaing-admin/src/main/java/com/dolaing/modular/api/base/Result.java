package com.dolaing.modular.api.base;

/**
 * @Author:张立华
 * @Description: Http请求返回最外层对象restful
 * @Date：Created in 11:31 2018/4/28
 * @Modified By:
 */
public class Result<T> {
    /**错误编码*/
    private String code ;
    /**错误信息*/
    private String msg ;
    /**返回对象*/
    private T data ;

    public Result(T data ,IResult result) {
        this.data = data;
        this.code = result.getCode();
        this.msg = result.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
