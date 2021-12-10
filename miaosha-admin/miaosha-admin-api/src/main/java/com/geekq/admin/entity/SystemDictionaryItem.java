package com.geekq.admin.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典明细
 *
 * @author Stef
 */
@Getter
@Setter
@Alias("SystemDictionaryItem")
public class SystemDictionaryItem extends BaseDomain {

    private static final long serialVersionUID = 4520006109163647891L;
    private Long parentId; // 系统目录
    private String title; // 名称
    private String tvalue; // 值
    private Integer sequence; // 序列
    private String intro; // 说明

    public String getJsonString() {
        Map<String, Object> m = new HashMap<>();
        m.put("id", getId());
        m.put("parentId", parentId);
        m.put("title", title);
        m.put("tvalue", tvalue);
        m.put("sequence", sequence);
        m.put("intro", intro);
        return JSONObject.toJSONString(m);
    }
}
