package com.dolaing.modular.api.base;


import com.dolaing.modular.api.enums.ResultEnum;

public class BaseApi {


    protected Result render(Object object) {
        return new Result(object,ResultEnum.SUCCESS);
    }
    protected Result render(Object object, IResult resultEnum) {
        return new Result(object,resultEnum);
    }

}
