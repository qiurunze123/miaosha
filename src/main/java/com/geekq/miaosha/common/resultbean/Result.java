package com.geekq.miaosha.common.resultbean;

import com.geekq.miaosha.common.enums.ResultStatus;

import java.io.Serializable;

public class Result<T> extends AbstractResult implements Serializable {
    private static final long serialVersionUID = 867933019328199779L;
    private T value;
    private Integer count;

    protected Result(ResultStatus status, String message) {
        super(status, message);
    }

    public static <T> Result<T> build() {
        return new Result(ResultStatus.SUCCESS, (String)null);
    }

    public static <T> Result<T> build(String message) {
        return new Result(ResultStatus.SUCCESS, message);
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void success(T value) {
        this.success();
        this.value = value;
        this.count = 0;
    }
}
