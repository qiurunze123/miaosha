package com.geekq.api.utils;


import java.io.Serializable;

public class ResultGeekQOrder<T> extends AbstractResultOrder implements Serializable {
    private static final long serialVersionUID = 867933019328199779L;
    private T data;
    private Integer count;

    protected ResultGeekQOrder() {
    }

    protected ResultGeekQOrder(ResultStatusOrder status, String message) {
        super(status, message);
    }

    protected ResultGeekQOrder(ResultStatusOrder status) {
        super(status);
    }

    public static <T> ResultGeekQOrder<T> build() {
        return new ResultGeekQOrder(ResultStatusOrder.SUCCESS, (String) null);
    }

    public static <T> ResultGeekQOrder<T> build(String message) {
        return new ResultGeekQOrder(ResultStatusOrder.SUCCESS, message);
    }

    public static <T> ResultGeekQOrder<T> error(ResultStatusOrder status) {
        return new ResultGeekQOrder<T>(status);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void success(T value) {
        this.success();
        this.data = value;
        this.count = 0;
    }

}
