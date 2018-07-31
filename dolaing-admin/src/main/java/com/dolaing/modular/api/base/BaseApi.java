package com.dolaing.modular.api.base;


import com.dolaing.core.base.tips.SuccessTip;
import com.dolaing.core.support.HttpKit;
import com.dolaing.modular.api.enums.ResultEnum;

import javax.servlet.http.HttpSession;

public class BaseApi {

    protected Result render(Object object) {
        return new Result(object, ResultEnum.SUCCESS);
    }

    protected Result render(Object object, IResult resultEnum) {
        return new Result(object, resultEnum);
    }

    protected static SuccessTip SUCCESS_TIP = new SuccessTip();

    protected String getPara(String name) {
        return HttpKit.getRequest().getParameter(name);
    }

    protected String getHeader(String name) {
        return HttpKit.getRequest().getHeader(name);
    }

    protected HttpSession getSession() {
        return HttpKit.getRequest().getSession();
    }

}
