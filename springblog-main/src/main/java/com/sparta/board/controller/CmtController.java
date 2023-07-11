package com.sparta.board.controller;

import com.sparta.board.dto.CmtRequestDto;
import com.sparta.board.dto.CmtResponseDto;
import com.sparta.board.dto.MessageResponseDto;
import com.sparta.board.dto.PostResponseDto;
import com.sparta.board.service.CmtService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CmtController {
    private final CmtService cmtService;
    @PostMapping("/comment")
    public CmtResponseDto createcmt(@RequestBody @Validated CmtRequestDto cmtRequestDto){
        CmtResponseDto cmtResponseDto =cmtService.createcmt(cmtRequestDto);
//        cmtService.insertcmt(cmtRequestDto.getPostid());
        return cmtResponseDto;
    }

//    @PostMapping("/comment/board")
//    public PostResponseDto insertcmt(@RequestBody CmtRequestDto cmtRequestDto){
//        return cmtService.insertcmt(cmtRequestDto.getPostid());
//    }

    @PutMapping("/comment/{id}")
    public MessageResponseDto updatecmt(@PathVariable Long id, @RequestBody CmtRequestDto cmtRequestDto){
        return cmtService.updatecmt(id,cmtRequestDto);
    }
    @DeleteMapping("/comment/{id}")
    public MessageResponseDto deletecmt(@PathVariable Long id){
        return cmtService.deletecmt(id);
    }
}
