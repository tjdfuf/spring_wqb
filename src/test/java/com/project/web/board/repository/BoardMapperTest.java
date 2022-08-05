package com.project.web.board.repository;

import com.project.web.board.domain.Board;
import com.project.web.common.paging.Page;
import com.project.web.common.search.Search;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardMapperTest {

    @Autowired
    BoardMapper mapper;

//    @Test
//    @DisplayName("제목으로 검색된 목록을 조회해야 한다.")
//    void searchByTitleTest() {
//        Search search = new Search(new Page(1, 10), "tc", "123");
//        mapper.findAll2(search).forEach(System.out::println);
//    }

    @Test
    @DisplayName("특정 게시물에 첨부된 파일 경로들을 조회해야 한다.")
    void findFileNamesTest() {
        // given
        Long bno = 322L;

        // when
        List<String> fileNames = mapper.findFileNames(bno);

        // then
        fileNames.forEach(System.out::println);
        assertEquals(2, fileNames.size());

    }

}