package com.project.web.reply.repository;

import com.project.web.common.paging.Page;
import com.project.web.reply.domain.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {

    //댓글 입력
    boolean save(Reply reply);

    //댓글 수정
    boolean modify(Reply reply);

    //댓글 삭제
    boolean remove(Long replyNo);

    // 댓글 전체 삭제
    boolean removeAll(Long boardNo);
    //댓글 개별 조회
    Reply findOne(Long replyNo);

    //댓글 목록 조회 - 여러개 보낼때는 @param으로 이름 정해서 보내주기
    // page는 xml에서 접근할때 page.필드명 이런식으로 접근함
    List<Reply> findAll(@Param("boardNo") Long boardNo
            , @Param("page") Page page);

    // 댓글 수 조회
    int getReplyCount(Long boardNo);


}