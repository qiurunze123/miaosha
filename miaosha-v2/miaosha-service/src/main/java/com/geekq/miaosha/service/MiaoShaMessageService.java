package com.geekq.miaosha.service;

import org.springframework.stereotype.Service;

@Service
public class MiaoShaMessageService {

//    @Autowired
//    private MiaoShaMessageDao messageDao;
//
//    public List<MiaoShaMessageInfo> getmessageUserList(Long userId , Integer status ){
//        return messageDao.listMiaoShaMessageByUserId(userId,status);
//    }
//
//
//    @Transactional(rollbackFor = Exception.class)
//    public void insertMs(MiaoShaMessageVo miaoShaMessageVo){
//        MiaoShaMessageUser mu = new MiaoShaMessageUser() ;
//        mu.setUserId(miaoShaMessageVo.getUserId());
//        mu.setMessageId(miaoShaMessageVo.getMessageId());
//        messageDao.insertMiaoShaMessageUser(mu);
//        MiaoShaMessageInfo miaoshaMessage = new MiaoShaMessageInfo();
//        miaoshaMessage.setContent(miaoShaMessageVo.getContent());
////        miaoshaMessage.setCreateTime(new Date());
//        miaoshaMessage.setStatus(miaoShaMessageVo.getStatus());
//        miaoshaMessage.setMessageType(miaoShaMessageVo.getMessageType());
//        miaoshaMessage.setSendType(miaoShaMessageVo.getSendType());
//        miaoshaMessage.setMessageId(miaoShaMessageVo.getMessageId());
//        miaoshaMessage.setCreateTime(new Date());
//        miaoshaMessage.setMessageHead(miaoShaMessageVo.getMessageHead());
//        messageDao.insertMiaoShaMessage(miaoshaMessage);
//    }
}
