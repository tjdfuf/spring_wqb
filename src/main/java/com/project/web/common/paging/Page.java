package com.project.web.common.paging;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString @Getter
@AllArgsConstructor
// 페이지 정보 클래스
public class Page {

    private int pageNum; // 페이지 번호
    private int amount; // 한 페이지당 배치할 게시물 수

    private String sort; // 정렬방법

    // 기본 생성자에 기본값 지정
    public Page() {
        this.pageNum = 1;
        this.amount = 10;
        this.sort = "Latest";
    }

//    public Page(int amount) {
//        this.pageNum = 1;
//        this.amount = amount;
//    }

    public void setPageNum(int pageNum) {
        if (pageNum <= 0 || pageNum > Integer.MAX_VALUE) {
            this.pageNum = 1;
            return;
        }
        this.pageNum = pageNum;
    }

    public void setAmount(int amount) {
        if (amount < 10 || amount > 100) {
            this.amount = 10;
            return;
        }
        this.amount = amount;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
