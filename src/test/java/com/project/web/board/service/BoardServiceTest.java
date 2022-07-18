package com.project.web.board.service;

import com.project.web.board.domain.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardService service;

    @Test
    @DisplayName("게시물 전체 조회 중간처리 결과리스트가 반환되어야 한다.")
    void findAllServiceTest() {
        List<Board> boardList = service.findAllService();
        boardList.forEach(System.out::println);

        assertEquals(300, boardList.size());
        assertEquals("제목300", boardList.get(0).getTitle());
    }
}