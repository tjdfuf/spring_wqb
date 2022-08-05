package com.project.web.common.paging;

import com.project.web.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PageMakerTest {

    @Autowired
    BoardRepository repository;

//    @Test
//    void pageInfoTest() {
//
//        int totalCount = repository.getTotalCount();
//        PageMaker pm = new PageMaker(new Page(31, 10), totalCount);
//
//        System.out.println(pm);
//
////        assertEquals(40, pm.getEndPage());
////        assertEquals(31, pm.getBeginPage());
//    }

}