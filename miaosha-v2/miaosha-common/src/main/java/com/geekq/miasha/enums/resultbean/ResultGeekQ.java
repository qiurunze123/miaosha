package com.geekq.miasha.enums.resultbean;


import com.geekq.miasha.enums.enums.ResultStatus;

import java.io.Serializable;

public class ResultGeekQ<T> extends AbstractResult implements Serializable {
    private static final long serialVersionUID = 867933019328199779L;
    private T data;
    private Integer count;

    protected ResultGeekQ() {
    }

    protected ResultGeekQ(ResultStatus status, String message) {
        super(status, message);
    }

    protected ResultGeekQ(ResultStatus status) {
        super(status);
    }

    public static <T> ResultGeekQ<T> build() {
        return new ResultGeekQ(ResultStatus.SUCCESS, (String) null);
    }

    public static <T> ResultGeekQ<T> build(String message) {
        return new ResultGeekQ(ResultStatus.SUCCESS, message);
    }

    public static <T> ResultGeekQ<T> error(ResultStatus status) {
        return new ResultGeekQ<T>(status);
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
