package com.sparta.board.controller;

import com.sparta.board.dto.CmtRequestDto;
import com.sparta.board.dto.CmtResponseDto;
import com.sparta.board.service.CmtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CmtController {
    private final CmtService cmtService;
    @PostMapping("/comment")
    public CmtResponseDto createcmt(@RequestBody CmtRequestDto cmtRequestDto){
        return cmtService.createcmt(cmtRequestDto);
    }
    @PutMapping("/comment/{id}")
    public CmtResponseDto updatecmt(@PathVariable Long id,@RequestBody CmtRequestDto cmtRequestDto){
        return cmtService.updatecmt(id,cmtRequestDto);
    }
    @DeleteMapping("/comment/{id}")
    public void deletecmt(@PathVariable Long id){
        cmtService.deletecmt(id);
    }
}
