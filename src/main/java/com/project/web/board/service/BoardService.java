package com.project.web.board.service;

import com.project.web.board.domain.Board;
import com.project.web.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository repository;

    // 게시물 등록 요청 중간 처리
    public boolean saveService(Board board) {
        log.info("save service start - {}", board);
        return repository.save(board);
    }

    // 게시물 전체 조회 요청 중간 처리
    public List<Board> findAllService() {
        log.info("findAll service start");
        List<Board> boardList = repository.findAll();

        // 목록 중간 데이터처리
        processConverting(boardList);

        return boardList;
    }

    private void processConverting(List<Board> boardList) {
        for (Board b : boardList) {
            convertDateFormat(b);
            substringTitle(b);
        }
    }

    private void convertDateFormat(Board b) {
        Date date = b.getRegDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd a hh:mm");
        b.setPrettierDate(sdf.format(date));
    }

    private void substringTitle(Board b) {

        // 만약에 글제목이 5글자 이상이라면
        // 5글자만 보여주고 나머지는 ...처리
        String title = b.getTitle();
        if (title.length() > 5) {
            String subStr = title.substring(0, 5);
            b.setShortTitle(subStr + "...");
        } else {
            b.setShortTitle(title);
        }

    }

    // 게시물 상세 조회 요청 중간 처리
    @Transactional // sql 여러개 돌때 하나라도 실패하면 rollback 둘다 성공해야 sql 동작
    public Board findOneService(Long boardNo, HttpServletResponse response, HttpServletRequest request) {
        log.info("findOne service start - {}", boardNo);
        Board board = repository.findOne(boardNo);

        // 해당 게시물 번호에 해당하는 쿠키가 있는지 확인
        // 쿠키가 없으면 조회수를 상승시켜주고 쿠키를 만들어서 클라이언트에 전송

        // 쿠키를 조회 - 해당 이름의 쿠키가 있으면 쿠키가 들어오고 없으면 null이 들어옴
        Cookie findCookie = WebUtils.getCookie(request, "b" + boardNo);

        makeViewCount(boardNo, response, findCookie);


        return board;
    }

    private void makeViewCount(Long boardNo, HttpServletResponse response, Cookie findCookie) {
        if (findCookie == null) {
            repository.upViewCount(boardNo);

            Cookie cookie = new Cookie("b" + boardNo, String.valueOf(boardNo));// 쿠키 생성
            cookie.setMaxAge(60);  // 쿠키 수명 설정 - 초 단위
            cookie.setPath("/board/content");  // 쿠키 작동 범위

            response.addCookie(cookie);  // 클라이언트에 쿠키 전송
        }
    }

    // 게시물 삭제 요청 중간 처리
    public boolean removeService(Long boardNo) {
        log.info("remove service start - {}", boardNo);
        return repository.remove(boardNo);
    }

    // 게시물 수정 요청 중간 처리
    public boolean modifyService(Board board) {
        log.info("modify service start - {}", board);
        return repository.modify(board);
    }
}