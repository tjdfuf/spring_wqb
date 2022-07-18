package com.project.web.board.repository;

import com.project.web.board.domain.Board;

import java.util.List;

public interface BoardRepository {

    // 만들 때 SQL을 생각하며 넣어줄 값과 리턴타입을 생각하며 만들기

    // 게시글 쓰기 기능
    boolean save(Board board);

    // 게시글 전체 조회
    List<Board> findAll();

    // 게시글 상세 조회
    Board findOne(Long boardNo);

    // 게시글 삭제
    boolean remove(Long boardNo);

    // 게시글 수정
    boolean modify(Board board);

    // 총 게시물 수 조회
    int getTotalCount();


}
