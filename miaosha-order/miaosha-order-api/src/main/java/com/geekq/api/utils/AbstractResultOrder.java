package com.geekq.api.utils;


public class AbstractResultOrder {
    private ResultStatusOrder status;
    private int code;
    private String message;

    protected AbstractResultOrder() {
    }

    protected AbstractResultOrder(ResultStatusOrder status, String message) {
        this.code = status.getCode();
        this.status = status;
        this.message = message;
    }

    protected AbstractResultOrder(ResultStatusOrder status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.status = status;
    }

    public static boolean isSuccess(AbstractResultOrder result) {
        return result != null && result.status == ResultStatusOrder.SUCCESS && result.getCode() == ResultStatusOrder.SUCCESS.getCode();
    }

    public AbstractResultOrder withError(ResultStatusOrder status) {
        this.status = status;
        return this;
    }

    public AbstractResultOrder withError(String message) {
        this.status = ResultStatusOrder.SYSTEM_ERROR;
        this.message = message;
        return this;
    }

    public AbstractResultOrder withError(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public AbstractResultOrder success() {
        this.status = ResultStatusOrder.SUCCESS;
        return this;
    }

    public ResultStatusOrder getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message == null ? this.status.getMessage() : this.message;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
