package com.geekq.miaosha.service;

import com.geekq.miaosha.dao.MiaoShaMessageDao;
import com.geekq.miaosha.domain.MiaoShaMessageUser;
import com.geekq.miaosha.vo.MiaoShaMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiaoShaMessageService {

    @Autowired
    private MiaoShaMessageDao messageDao;

    public List<MiaoShaMessageUser> getMessage( String userId ){

        List<MiaoShaMessageUser> messageUserList  = messageDao.listMiaoShaMessageUser(userId);

        return  messageUserList;
    }


    public void insertMs(MiaoShaMessageVo miaoShaMessageVo){
        MiaoShaMessageUser mu = new MiaoShaMessageUser() ;
        mu.setUserId(miaoShaMessageVo.getUserId());
        mu.setMessageId(miaoShaMessageVo.getMessageId());
        messageDao.insertMiaoShaMessageUser(mu);
    }
}
