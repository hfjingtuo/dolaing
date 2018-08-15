package com.dolaing.modular.api.base;


import com.dolaing.core.base.tips.SuccessTip;
import com.dolaing.core.support.HttpKit;
import com.dolaing.modular.api.enums.ResultEnum;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class BaseApi {

    protected Result render(Object object) {
        return new Result(object, ResultEnum.SUCCESS);
    }

    protected Result render(Object object, IResult resultEnum) {
        return new Result(object, resultEnum);
    }

    protected Result render(Object object, String code ,String msg) {
        return new Result(object, code ,msg);
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

    protected Result renderPayResponseResult(Map map) {
        Map result = (Map)map.get("data");
        if(map.get("code").toString().equals("1000")  && result !=null && result.get("respCode").toString().equals("RC00")){
            return render(null);
        }else if(map.get("code").toString().equals("1000") && result != null && result.get("respDesc") !=null){
            return render(null,"1001",result.get("respDesc").toString());
        }else{
            return render(null,map.get("code").toString(),map.get("msg").toString());
        }
    }

}
