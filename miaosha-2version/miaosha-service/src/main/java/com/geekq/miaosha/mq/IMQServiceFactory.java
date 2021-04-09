package com.geekq.miaosha.mq;
/*
*
* */
public interface IMQServiceFactory {

    IMQService create(String type);
}
