package org.yourssuproject.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.yourssuproject.domain.memos.Memos;
import org.yourssuproject.service.memos.MemosService;
import org.yourssuproject.web.dto.MemosSaveRequestDto;
import org.yourssuproject.web.dto.MemosResponseDto;
import org.yourssuproject.web.dto.MemosUpdateRequestDto;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MemosApiController {
    private final MemosService memosService;

    @PostMapping("/memos")
    public MemosResponseDto save(@RequestBody MemosSaveRequestDto requestDto) { return memosService.save(requestDto); }

    @PutMapping("/memos/{memoId}")
    public MemosResponseDto update(@PathVariable Long memoId, @RequestBody MemosUpdateRequestDto requestDto) {
        return memosService.update(memoId, requestDto);
    }

    @GetMapping("/memos")
    public List<MemosResponseDto> searchByDate(@RequestParam Map<String, String> param, @PageableDefault(size = 5) Pageable pageable){
        return memosService.searchByDate(param.get("date"), pageable);
    }

//    @GetMapping("/memos")
//    public List<MemosResponseDto> page(@PageableDefault(size = 5) Pageable pageable) {
//        return memosService.page(pageable);
//    }

    @GetMapping("/memos/{memoId}")
    public MemosResponseDto detail(@PathVariable Long memoId) { return memosService.detail(memoId); }

    @DeleteMapping("/memos/{memoId}")
    public void delete(@PathVariable Long memoId) { memosService.delete(memoId); }


}
