package com.project.web.common.search;

import com.project.web.common.paging.Page;
import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Search {

    private Page page;
    private String type; // 검색 조건
    private String keyWord; // 검색 키워드

}
