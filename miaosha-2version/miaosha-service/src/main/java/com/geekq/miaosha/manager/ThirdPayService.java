package com.geekq.miaosha.manager;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.geekq.miaosha.vo.PayOrderVo;


import org.springframework.stereotype.Component;

@Component
public class ThirdPayService {

    public String pay(PayOrderVo payOrderVo){

        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", "123456789");                               // 商户ID
        paramMap.put("mchOrderNo", payOrderVo.getOrderId());     // 商户订单号
        // 支付渠道ID, WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        paramMap.put("channelId", "ALIPAY_WAP");
        paramMap.put("amount", payOrderVo.getAmount());                                  // 支付金额,单位分
        paramMap.put("currency", "cny");                            // 币种, cny-人民币
        paramMap.put("clientIp", "211.94.116.218");                 // 用户地址,微信H5支付时要真实的
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "订单支付");
        paramMap.put("body", "XXPAY支付");
       // paramMap.put("notifyUrl", notifyUrl);                       // 回调URL
        paramMap.put("param1", "");                                 // 扩展参数1
        paramMap.put("param2", "");
        String result=HttpUtil.get("",paramMap);
        return "";
    }

}
