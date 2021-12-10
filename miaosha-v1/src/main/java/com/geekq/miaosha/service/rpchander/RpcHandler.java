package com.geekq.miaosha.service.rpchander;

public interface RpcHandler {

    int RETRY_COUNT = 5;

    boolean handele();

    boolean rollBack();
}
