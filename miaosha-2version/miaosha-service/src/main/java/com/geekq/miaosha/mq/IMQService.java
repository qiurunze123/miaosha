package com.geekq.miaosha.mq;

import com.alibaba.fastjson.JSONObject;

public interface IMQService {
    String send(String paramsJson);
    String receive(String paramsJson);

}
