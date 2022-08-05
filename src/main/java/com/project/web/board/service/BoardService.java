package com.project.web.board.service;

import com.project.web.board.domain.Board;
import com.project.web.board.repository.BoardMapper;
import com.project.web.common.paging.Page;
import com.project.web.common.search.Search;
import com.project.web.reply.repository.ReplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardService {

    // 이름 다 바꾸기 - shift + f6
    private final BoardMapper boardMapper;
    private final ReplyMapper replyMapper;

    // 게시물 등록 요청 중간 처리
    @Transactional
    public boolean saveService(Board board) {
        log.info("save service start - {}", board);

        // 게시물 내용 DB에 저장
        boolean flag = boardMapper.save(board);

        List<String> fileNames = board.getFileNames();

        if (fileNames != null && fileNames.size() > 0) {
            for (String fileName : fileNames) {
                // 첨부파일 내용 DB에 저장
                boardMapper.addFile(fileName);
            }
        }


        return flag;
    }

    // 게시물 전체 조회 요청 중간 처리
//    public List<Board> findAllService() {
//        log.info("findAll service start");
//        List<Board> boardList = repository.findAll();
//
//        // 목록 중간 데이터처리
//        processConverting(boardList);
//
//        return boardList;
//    }

    public Map<String, Object> findAllService(Page page) {
        log.info("findAll service start");

        HashMap<String, Object> findDataMap = new HashMap<>();

        List<Board> boardList = boardMapper.findAll(page);

        findDataMap.put("bList", boardList);
        findDataMap.put("tc", boardMapper.getTotalCount());

        // 목록 중간 데이터처리
        processConverting(boardList);

        return findDataMap;
    }

    // with searching
    public Map<String, Object> findAllService(Search search) {
        log.info("findAll service start");

        HashMap<String, Object> findDataMap = new HashMap<>();

        List<Board> boardList = boardMapper.findAll2(search);
        log.info("boardList: {}", boardList);
        log.info("boardList.size: {}", boardList.size());
        findDataMap.put("bList", boardList);
        findDataMap.put("tc", boardMapper.getTotalCount2(search));
//        findDataMap.put("tc", boardList.size());

        // 목록 중간 데이터처리
        processConverting(boardList);

        return findDataMap;
    }



    private void processConverting(List<Board> boardList) {
        for (Board b : boardList) {
            convertDateFormat(b);
            substringTitle(b);
            checkNewArticle(b);
            setReplyCount(b);
        }
    }

    public void setReplyCount(Board b) {
        b.setReplyCount(replyMapper.getReplyCount(b.getBoardNo()));
    }

    // 신규 게시물 여부 처리
    private void checkNewArticle(Board b) {
        // 게시물의 작성일자와 현재 시간을 대조

        // 게시물의 작성일자 가져오기
        long regDateTime = b.getRegDate().getTime();

        // 현재 시간 얻기 (밀리초)
        long nowTime = System.currentTimeMillis();

        // 현재시간 - 작성시간
        long diff = nowTime - regDateTime;

        // 신규 게시물 제한시간
        long limitTime = 60 * 5 * 1000; // 5분

        if (diff < limitTime) {
            b.setNewArticle(true);
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
    public Board findOneService(Long boardNo, HttpServletResponse response, HttpServletRequest request, String fm) {
        log.info("findOne service start - {}", boardNo);
        Board board = boardMapper.findOne(boardNo);

        // 해당 게시물 번호에 해당하는 쿠키가 있는지 확인
        // 쿠키가 없으면 조회수를 상승시켜주고 쿠키를 만들어서 클라이언트에 전송

        // 쿠키를 조회 - 해당 이름의 쿠키가 있으면 쿠키가 들어오고 없으면 null이 들어옴
        Cookie findCookie = WebUtils.getCookie(request, "b" + boardNo);

        makeViewCount(boardNo, response, findCookie, fm);


        return board;
    }

    private void makeViewCount(Long boardNo, HttpServletResponse response, Cookie findCookie, String fm) {
        if (Objects.equals(fm, "modify")) return;
        if (findCookie == null) {
            boardMapper.upViewCount(boardNo);

            Cookie cookie = new Cookie("b" + boardNo, String.valueOf(boardNo));// 쿠키 생성
            cookie.setMaxAge(60);  // 쿠키 수명 설정 - 초 단위
            cookie.setPath("/board/content");  // 쿠키 작동 범위

            response.addCookie(cookie);  // 클라이언트에 쿠키 전송
        }
    }

    // 게시물 삭제 요청 중간 처리
    @Transactional
    public boolean removeService(Long boardNo) {
        log.info("remove service start - {}", boardNo);
        replyMapper.removeAll(boardNo);

        boolean remove = boardMapper.remove(boardNo);


        return remove;
    }

    // 게시물 수정 요청 중간 처리
    public boolean modifyService(Board board) {
        log.info("modify service start - {}", board);
        return boardMapper.modify(board);
    }


    // 첨부파일 목록 가져오는 중간 처리
    public List<String> getFiles(Long bno) {
        return boardMapper.findFileNames(bno);
    }

}