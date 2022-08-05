package com.project.web.common.api;


import com.project.web.board.domain.Board;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

// jsp 뷰포워딩을 하지않고 클라이언트에게 JSON 데이터를 전송함
@RestController
@Log4j2
public class RestBasicController {

    @GetMapping("/api/hello")
    public String hello() {

        return "hellow!!!";
    }

    @GetMapping("/api/board")
    public Board board() {
        Board board = new Board();

        board.setBoardNo(10L);
        board.setContent("할룽~~");
        board.setTitle("메롱~~");
        board.setWriter("박영희");

        return board;
    }

    @GetMapping("/api/arr")
    public String[] arr() {
        // js 배열로 나옴
        // json은 js 생김새를 가져온것이지 js가 아님
        String[] foods = {"짜장면", "레몬에이드", "볶음밥"};
        return foods;
    }


    // post요청처리
    // json형태로 받을 때 앞에 @RequestBody를 붙여 형 변환을 할수 있게 해줘야 함.
    @PostMapping("api/join")
    public String join(@RequestBody List<String> info) { // 클라이언트에서 받는 것
        log.info("/api/join POST!! - {}", info);

        return "POST - OK";  // 클라이언트로 가는 것
    }

    // put요청처리
    @PutMapping("api/join")
    public String joinPut(@RequestBody Board board) {
        log.info("/api/join PUT!! - {}", board);
        return "PUT - OK";
    }

    // delete요청처리
    @DeleteMapping("api/join")
    public String joinDel() {
        log.info("/api/join Delete!!");
        return "DELETE - OK";
    }

    // RestController에서 뷰포워딩하기
    @GetMapping("/hoho")
    public ModelAndView hoho() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }



}

