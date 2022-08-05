package com.project.web.reply.api;

import com.project.web.common.paging.Page;
import com.project.web.reply.domain.Reply;
import com.project.web.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/replies")
@CrossOrigin // 오픈 api (외부의 접근을 허용)
public class ReplyApiController {

    private final ReplyService replyService;
    /*
        - 댓글 목록 조회요청 : /api/v1/replies - GET
        - 댓글 개별 조회요청 : /api/v1/replies/72(replyNo) - GET
        - 댓글 쓰기 요청 : /api/v1/replies - POST
        - 댓글 수정 요청 : /api/v1/replies/72(replyNo) - PUT
        - 댓글 삭제 요청 : /api/v1/replies/72(replyNo) - DELETE
     */

    @GetMapping("")
    public Map<String, Object> list(Long boardNo, Page page) {
        log.info("/api/v1/replies GET! bno = {}, page = {}", boardNo, page);

        Map<String, Object> replies = replyService.getList(boardNo, page);

        return replies;
    }

    @PostMapping("")
    public String create(@RequestBody Reply reply) {
        log.info("/api/v1/replies POST! - {}", reply);
        boolean flag = replyService.write(reply);
        return flag ? "insert-success" : "insert-fail";
    }

    @PutMapping("/{rno}")
    public String modify(@PathVariable Long rno, @RequestBody Reply reply) {
        reply.setReplyNo(rno);
        log.info("/api/v1/replies PUT! - {}", reply);
        boolean flag = replyService.modify(reply);
        return flag ? "mod-success" : "mod-fail";
    }

    @DeleteMapping("/{rno}")
    public String delete(@PathVariable Long rno) {

        log.info("/api/v1/replies DEL! - {}", rno);
        boolean flag = replyService.remove(rno);
        return flag ? "del-success" : "insert-fail";
    }

}
