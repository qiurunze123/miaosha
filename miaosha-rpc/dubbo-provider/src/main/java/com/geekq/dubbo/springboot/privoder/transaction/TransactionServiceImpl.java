package com.geekq.dubbo.springboot.privoder.transaction;

import com.alibaba.dubbo.config.annotation.Service;
import com.geekq.dubbo.springboot.ServiceAPI;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = ServiceAPI.class)
public class TransactionServiceImpl implements ServiceAPI {

    @Override
    public String sendMessage(String message) {
        System.out.println("this is sendMessage try message=" + message);
        if (message.equals("123")) {
            throw new NullPointerException();
        }

        return "quickstart-provider-message=" + message;
    }

    @Override
    public boolean isTrueSeats(String seats) {
        if (seats.equals("1,2,3")) {
            throw new IllegalArgumentException();
        } else {
            return true;
        }
    }

    public boolean isNotSold(String seats) {
        if (seats.equals("4,5")) {
            throw new IllegalArgumentException();
        } else {
            return true;
        }
    }

    /**
     * 千万千万注意幂等性问题
     */
    public String saveOrder(String fieldId, String seats, String seatsNum) {
        System.out.println("创建一个待支付状态的订单");
        return "";
    }

    public String confirmSaveOrder(String fieldId, String seats, String seatsNum) {
        System.out.println("将订单修改为支付中");
        return "";
    }

    public String cancelSaveOrder(String fieldId, String seats, String seatsNum) {
        System.out.println("将订单修改为已关闭");
        return "";
    }

    public String confirmSendMessage(String message) {
        System.out.println("this is confirmSendMessage message=" + message);
        return "quickstart-provider-message=" + message;
    }

    public String cancelSendMessage(String message) {
        System.out.println("this is cancelSendMessage message=" + message);
        return "quickstart-provider-message=" + message;
    }

    public boolean confirmIsTrueSeats(String seats) {
        System.out.println("this is confirmIsTrueSeats");
        return true;
    }

    public boolean cancelIsTrueSeats(String seats) {
        System.out.println("this is cancelIsTrueSeats");
        return true;
    }

    public boolean confirmIsNotSold(String seats) {
        System.out.println("this is confirmIsNotSold");
        return true;
    }

    public boolean cancelIsNotSold(String seats) {
        System.out.println("this is cancelIsNotSold");
        return true;
    }

}
