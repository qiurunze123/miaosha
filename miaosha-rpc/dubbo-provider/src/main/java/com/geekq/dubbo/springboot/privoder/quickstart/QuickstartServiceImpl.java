package com.geekq.dubbo.springboot.privoder.quickstart;


import com.geekq.dubbo.springboot.ServiceAPI;

//@Component
//@Service(interfaceClass = ServiceAPI.class)
public class QuickstartServiceImpl implements ServiceAPI {

    @Override
    public String sendMessage(String message) {
        return "quickstart-provider-message=" + message;
    }

    @Override
    public boolean isTrueSeats(String seats) {
        return false;
    }

    @Override
    public boolean isNotSold(String seats) {
        return false;
    }

    @Override
    public String saveOrder(String fieldId, String seats, String seatsNum) {
        return null;
    }
}
