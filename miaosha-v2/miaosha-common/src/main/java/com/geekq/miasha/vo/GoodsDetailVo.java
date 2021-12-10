package com.geekq.miasha.vo;

import com.geekq.api.entity.GoodsVoOrder;
import com.geekq.miasha.entity.MiaoshaUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailVo {
    private int miaoshaStatus = 0;
    private int remainSeconds = 0;
    private GoodsVoOrder goods;
    private MiaoshaUser user;

}
