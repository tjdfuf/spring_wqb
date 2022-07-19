package com.project.web.board.controller;

import com.project.web.board.domain.Board;
import com.project.web.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    // 게시물 목록 요청
    @GetMapping("/list")
    public String list(Model model) {
        log.info("controller request /board/list GET");
        List<Board> boardList = boardService.findAllService();
        log.debug("return data - {}", boardList);
        model.addAttribute("bList", boardList);
        return "board/board-list";
    }

    // 게시물 상세 조회 요청
    @GetMapping("/content/{boardNo}")
    public String content(@PathVariable Long boardNo, Model model, HttpServletResponse response, HttpServletRequest request) {
        log.info("controller request /board/content GET - {}", boardNo);
        Board board = boardService.findOneService(boardNo, response, request);
        log.info("return data - {}", board);
        model.addAttribute("b", board);
        return "board/board-detail";
    }

    // 게시물 쓰기 화면 요청
    @GetMapping("/write")
    public String write() {
        log.info("controller request /board/write GET");
        return "board/board-write";
    }

    // 게시물 쓰기 등록 요청
    @PostMapping("/write")
    public String write(Board board, RedirectAttributes ra) {
        log.info("controller request /board/write POST! - {}", board);
        boolean flag = boardService.saveService(board);
        // 게시물 등록에 성공하면 클라이언트에 성공메세지 전송
        if (flag) ra.addAttribute("msg", "reg-success");

        return flag ? "redirect:/board/list" : "redirect:/";
    }


    // 게시물 삭제 요청
    @GetMapping("/delete/{boardNo}")
    public String remove(@PathVariable Long boardNo) {
        log.info("controller request /board/remove GET - {}", boardNo);
        boolean result = boardService.removeService(boardNo);

        return result ? "redirect:/board/list" : "redirect:/";
    }

    // 게시물 수정 화면 요청
    @GetMapping("/modify")
    public String modify(Long boardNo, Model model, HttpServletResponse response, HttpServletRequest request) {
        log.info("controller request /board/modify GET");
        Board board = boardService.findOneService(boardNo, response, request);
        log.info("find article: {}", board);
        model.addAttribute("board", board);
        return "board/board-modify";
    }

    // 수정 처리 요청
    @PostMapping("/modify")
    public String modify(Board board) {
        log.info("controller request /board/modify POST! - {}", board);
        boolean result = boardService.modifyService(board);
        return result ? "redirect:/board/content/" + board.getBoardNo() : "redirect:/";
    }


}
