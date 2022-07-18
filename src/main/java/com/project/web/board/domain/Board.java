package com.project.web.board.domain;

import lombok.*;

import java.util.Date;

@Setter @Getter @ToString
@EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor
public class Board {

    private Long boardNo;
    private String writer;
    private String title;
    private String content;
    private Long viewCnt;
    private Date regDate;

}
