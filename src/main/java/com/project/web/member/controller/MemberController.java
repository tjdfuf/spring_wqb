package com.project.web.member.controller;

import com.project.web.member.domain.Member;
import com.project.web.member.dto.LoginDTO;
import com.project.web.member.service.LoginFlag;
import com.project.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.*;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    // 회원가입 양식 띄우기 요청
    @GetMapping("/sign-up")
    public void signUp() {
        log.info("/member/sign-up GET - forwarding to sign-up.jsp");
    }

    // 회원가입 처리 요청
    @PostMapping("/sign-up")
    public String signUp(Member member, RedirectAttributes ra) {
        log.info("/member/sign-up POST! - {}", member);

        boolean flag = memberService.signUp(member);
        ra.addFlashAttribute("msg", "reg-success");

        return flag ? "redirect:/member/sign-in" : "redirect:/member/sign-up";
    }


    // 아이디, 이메일 중복확인 비동기 요청 처리
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<Boolean> check(String type, String value) {
        log.info("/member/check?type={}&value={} GET!! ASYNC", type, value);

        boolean flag = memberService.checkSignUpValue(type, value);

        return new ResponseEntity<>(flag, HttpStatus.OK);
    }


    // 로그인 화면을 열어주는 요청처리
    @GetMapping("/sign-in")
    public void signIn(HttpServletRequest request) {
        log.info("/member/sign-in GET! - forwarding to sign-in.jsp");

        // 요청 정보 헤더 안에는 Referer라는 키가 있는데
        // 여기 안에는 이 페이지로 진입할 때 어디에서 왔는지 URI 정보가 들어있음.
        String referer = request.getHeader("Referer");
        log.info("referer: {}", referer);

        request.getSession().setAttribute("redirectURI", referer);
    }

    // 로그인 요청 처리
    @PostMapping("/sign-in")
    public String signIn(LoginDTO inputData, RedirectAttributes ra, HttpSession session, Model model) {

        log.info("/member/sign-in POST! - {}", inputData);
//        log.info("session timeout : {}", session.getMaxInactiveInterval());

        // 로그인 서비스 호출
        LoginFlag flag = memberService.login(inputData, session);

        if (flag == LoginFlag.SUCCESS) {
            log.info("login success!!");
            String redirectURI = (String) session.getAttribute("redirectURI");
            return "redirect:" + redirectURI;
        }
        model.addAttribute("loginMsg", flag);
        return "member/sign-in";
    }


    @GetMapping("/sign-out")
    public String signOut(HttpSession session) {
        if (session.getAttribute("loginUser") != null) {
            // 1. 세션에서 정보를 삭제한다.
            session.removeAttribute("loginUser");

            // 2. 세션을 무효화한다.
            session.invalidate();
            return "redirect:/";
        }
        return "redirect:/member/sign-in";
    }

}
