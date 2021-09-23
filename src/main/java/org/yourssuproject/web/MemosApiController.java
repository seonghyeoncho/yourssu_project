package org.yourssuproject.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.yourssuproject.service.memos.MemosService;
import org.yourssuproject.web.dto.MemosSaveRequestDto;
import org.yourssuproject.web.dto.MemosResponseDto;
import org.yourssuproject.web.dto.MemosUpdateRequestDto;

import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MemosApiController {
    private final MemosService memosService;

    @PostMapping("/memos")
    public ResponseEntity<?> save(@RequestBody @Valid MemosSaveRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errMessage, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(memosService.save(requestDto), HttpStatus.OK);
    }

    @PutMapping("/memos/{memoId}")
    @ResponseStatus(HttpStatus.OK)
    public MemosResponseDto update(@PathVariable Long memoId, @RequestBody MemosUpdateRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("error");
        }
        return memosService.update(memoId, requestDto);
    }

    @GetMapping("/memos")
    @ResponseStatus(HttpStatus.OK)
    public List<MemosResponseDto> searchByDate(@RequestParam Map<String, String> param, @PageableDefault(size = 5) Pageable pageable){
        return memosService.searchByDate(param.get("date"), pageable);
    }

    @GetMapping("/memos/{memoId}")
    @ResponseStatus(HttpStatus.OK)
    public MemosResponseDto detail(@PathVariable Long memoId) { return memosService.detail(memoId); }

    @DeleteMapping("/memos/{memoId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long memoId) { memosService.delete(memoId); }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runTimeEx(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
