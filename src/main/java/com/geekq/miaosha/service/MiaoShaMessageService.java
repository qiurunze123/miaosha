package com.geekq.miaosha.service;

import com.geekq.miaosha.dao.MiaoShaMessageDao;
import com.geekq.miaosha.domain.MiaoShaMessageInfo;
import com.geekq.miaosha.domain.MiaoShaMessageUser;
import com.geekq.miaosha.rabbitmq.MiaoshaMessage;
import com.geekq.miaosha.utils.DateTimeUtils;
import com.geekq.miaosha.vo.MiaoShaMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MiaoShaMessageService {

    @Autowired
    private MiaoShaMessageDao messageDao;

    public List<MiaoShaMessageUser> getMessage( String userId ){

        List<MiaoShaMessageUser> messageUserList  = messageDao.listMiaoShaMessageUser(userId);

        return  messageUserList;
    }


    @Transactional(rollbackFor = Exception.class)
    public void insertMs(MiaoShaMessageVo miaoShaMessageVo){
        MiaoShaMessageUser mu = new MiaoShaMessageUser() ;
        mu.setUserId(miaoShaMessageVo.getUserId());
        mu.setMessageId(miaoShaMessageVo.getMessageId());
        messageDao.insertMiaoShaMessageUser(mu);
        MiaoShaMessageInfo miaoshaMessage = new MiaoShaMessageInfo();
        miaoshaMessage.setContent(miaoShaMessageVo.getContent());
//        miaoshaMessage.setCreateTime(new Date());
        miaoshaMessage.setStatus(miaoShaMessageVo.getStatus());
        miaoshaMessage.setMessageType(miaoShaMessageVo.getMessageType());
        miaoshaMessage.setSendType(miaoShaMessageVo.getSendType());
        miaoshaMessage.setMessageId(miaoShaMessageVo.getMessageId());
        miaoshaMessage.setCreateTime(new Date());
        messageDao.insertMiaoShaMessage(miaoshaMessage);
    }
}
