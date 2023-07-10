package com.sparta.board.controller;

import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.MessageResponseDto;
import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.service.BoardService;
import com.sparta.board.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final BoardService boardService;
    private final UserService userService;
    @PostMapping("/user/signup")
    public String signup(@RequestBody @Valid SignupRequestDto requestDto){
        userService.signup(requestDto);
        return "가입완료";
    }

    @PostMapping("/user/login")
    public String login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res){
        userService.login(requestDto,res);
        return "로그인 완료";
    }
}
