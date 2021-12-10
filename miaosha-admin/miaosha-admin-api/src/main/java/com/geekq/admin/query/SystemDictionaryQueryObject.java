package com.geekq.admin.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class SystemDictionaryQueryObject extends QueryObject {
    private String keyword;
    private Long parentId;

    public String getKeyword() {
        return StringUtils.hasLength(keyword) ? keyword : null;
    }
}
