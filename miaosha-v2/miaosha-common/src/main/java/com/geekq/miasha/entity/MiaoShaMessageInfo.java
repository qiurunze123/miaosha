package com.geekq.miasha.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 消息中心主体表
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MiaoShaMessageInfo implements Serializable {

    private Integer id;

    private Long messageId;

    private Long userId;

    private String content;

    private Date createTime;

    private Integer status;

    private Date overTime;

    private Integer messageType;

    private Integer sendType;

    private String goodName;

    private BigDecimal price;

    private String messageHead;

}
