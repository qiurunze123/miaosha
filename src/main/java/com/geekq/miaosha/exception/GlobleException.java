package com.geekq.miaosha.exception;

import com.geekq.miaosha.result.CodeMsg;

public class GlobleException extends RuntimeException {


    private CodeMsg cm ;
    public GlobleException(CodeMsg cm){
        super();
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
