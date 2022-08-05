package com.project.web.board.controller;

import com.project.web.board.domain.Board;
import com.project.web.board.service.BoardService;
import com.project.web.common.paging.Page;
import com.project.web.common.paging.PageMaker;
import com.project.web.common.search.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    // 게시물 목록 요청
    @GetMapping("/list")
    public String list(Search search, Model model) {
        log.info("controller request /board/list GET - {}", search);
        Map<String, Object> boardMap = boardService.findAllService(search);
//        log.debug("return data - {}", boardMap);


//        log.info("amount: {}", search.getPage().getAmount());
//        if (amount != 0) {
//            page.setAmount(amount);
//        }

        // 페이지 정보 생성
        PageMaker pm = new PageMaker(new Page(search.getPageNum(), search.getAmount()), (Integer) boardMap.get("tc"));


//        log.info(boardMap.get("bList"));
        model.addAttribute("bList", boardMap.get("bList"));
        model.addAttribute("pm", pm);
        model.addAttribute("search", search);
//        model.addAttribute("am", page.getAmount());

        return "board/board-list";
    }

    // 게시물 상세 조회 요청
    @GetMapping("/content/{boardNo}")
    public String content(@PathVariable Long boardNo, Page page, Model model, HttpServletResponse response, HttpServletRequest request, String fm) {
        log.info("controller request /board/content GET - {}", boardNo);
        Board board = boardService.findOneService(boardNo, response, request, fm);
        log.info("return data - {}", board);

        model.addAttribute("p", page);

        model.addAttribute("b", board);
        return "board/board-detail";
    }

    // 게시물 쓰기 화면 요청
    @GetMapping("/write")
    public String write(HttpSession session, RedirectAttributes ra) {
        log.info("controller request /board/write GET");

        if (session.getAttribute("loginUser") == null) {
            ra.addAttribute("warningMsg", "forbidden");
            return "redirect:/member/sign-in";
        }

        return "board/board-write";
    }

    // 게시물 쓰기 등록 요청
    @PostMapping("/write")
    public String write(Board board,
                        @RequestParam("files") List<MultipartFile> fileList,
                        RedirectAttributes ra) {
        log.info("controller request /board/write POST! - {}", board);

        /*
        if (fileList != null) {
            List<String> fileNames = new ArrayList<>();
            for (MultipartFile f : fileList) {
                log.info("attachmented file-name: {}", f.getOriginalFilename());
                fileNames.add(f.getOriginalFilename());
            }
            // board객체에 파일명 추가
        }
         */


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
    public String modify(Long boardNo, Model model, HttpServletResponse response, HttpServletRequest request, String fm) {
        log.info("controller request /board/modify GET");
        Board board = boardService.findOneService(boardNo, response, request, fm);
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

    // 특정 게시물에 붙은 첨부파일경로 리스트를 클라이언트에게 비동기 전송
    @GetMapping("/file/{bno}")
    @ResponseBody
    public ResponseEntity<List<String>> getFiles(@PathVariable Long bno) {

        List<String> files = boardService.getFiles(bno);
        log.info("/board/file/{} GET! ASYNC - {}", bno, files);

        return new ResponseEntity<>(files, HttpStatus.OK);
    }

}
