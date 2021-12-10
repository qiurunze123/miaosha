package com.geekq.admin.query;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class QueryObject {

    private Integer currentPage = 1;
    private Integer pageSize = 10;

    public int getStart() {
        return (currentPage - 1) * pageSize;
    }

}
