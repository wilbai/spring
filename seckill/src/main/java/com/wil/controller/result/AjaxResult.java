package com.wil.controller.result;

/**
 * Ajax请求的返回结果类
 * Created by wil on 2017/11/8.
 */
public class AjaxResult {

    private final static String  STATE_SUCCESS = "success";
    private final static String  STATE_ERROR = "error";

    private String state;
    private String message;
    private Object data;

    public static AjaxResult success() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(STATE_SUCCESS);
        return ajaxResult;
    }

    public static AjaxResult successWithData(Object data) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(STATE_SUCCESS);
        ajaxResult.setData(data);
        return ajaxResult;
    }

    public static AjaxResult error(String message) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(STATE_ERROR);
        ajaxResult.setMessage(message);
        return ajaxResult;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
