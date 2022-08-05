package com.project.web.reply.repository;

import com.project.web.common.paging.Page;
import com.project.web.reply.domain.Reply;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyMapperTest {

    @Autowired ReplyMapper replyMapper;

    @Test
    @DisplayName("댓글 1000개를 무작위 게시물에 등록해야 한다.")
    void saveTest() {

        for (int i = 1; i <= 1000; i++) {
            long bno = (long) (Math.random() * 300 + 1);

            Reply reply = new Reply();
            reply.setBoardNo(bno);
            reply.setReplyText("댓글" + i);
            reply.setReplyWriter("메롱이"+ i);

            replyMapper.save(reply);
        }
    }

    @Test
    @DisplayName("댓글이 수정되어야 한다.")
    void modifyTest() {
        long rno = 1000L;

        Reply reply = new Reply();
        reply.setReplyText("수정된 내용");
        reply.setReplyNo(rno);

        boolean modify = replyMapper.modify(reply);

        assertTrue(modify);
    }

    @Test
    @DisplayName("특정 게시물의 댓글목록을 조회해야 한다.")
    void findAllTest() {
        List<Reply> replyList = replyMapper.findAll(1L, new Page());

        replyList.forEach(System.out::println);

        assertEquals(3, replyList.size());

    }

    @Test
    @DisplayName("댓글 200개를 299번 게시물에 등록해야 한다.")
    void saveTest2() {

        for (int i = 1; i <= 200; i++) {
            long bno = 299L;

            Reply reply = new Reply();
            reply.setBoardNo(bno);
            reply.setReplyText("댓글" + i);
            reply.setReplyWriter("메롱이"+ i);

            replyMapper.save(reply);
        }
    }


}