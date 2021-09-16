package org.yourssuproject.service.memos;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yourssuproject.domain.memos.Memos;
import org.yourssuproject.domain.memos.MemosRepository;
import org.yourssuproject.web.dto.MemosSaveRequestDto;
import org.yourssuproject.web.dto.MemosResponseDto;
import org.yourssuproject.web.dto.MemosUpdateRequestDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemosService {
    private final MemosRepository memosRepository;

    @Transactional
    public MemosResponseDto save(MemosSaveRequestDto requestDto) {
        Memos entity = memosRepository.save(requestDto.toEntity());
        return new MemosResponseDto(entity);
    }

    @Transactional
    public MemosResponseDto update(Long id, MemosUpdateRequestDto requestDto) {
        Memos memos = memosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메모가 없습니다. id" + id));
        memos.update(requestDto.getTitle(), requestDto.getText());

        return new MemosResponseDto(memos);
    }

    @Transactional
    public MemosResponseDto detail(Long id) {
        Memos memo = memosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메모가 없습니다. id" + id));

        return new MemosResponseDto(memo);
    }

    @Transactional
    public void delete(Long id) {
        Memos memos = memosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메모가 없습니다. id" + id));

       memosRepository.delete(memos);
    }

    @Transactional
    public List<MemosResponseDto> searchByDate(String date, Pageable pageable) {
        System.out.println(">>>>>>>>>> date: " + date);

        LocalDateTime startDate = LocalDateTime.of(LocalDate.parse(date), LocalTime.of(0,0,0));
        LocalDateTime endDate = LocalDateTime.of(LocalDate.parse(date), LocalTime.of(23,59,59));

        System.out.println(">>>>>>>>>>" + startDate);
        System.out.println(">>>>>>>>>>" + endDate);

        List<Memos> memosList = memosRepository.findByCreatedAtBetween(startDate, endDate, pageable);
        List<MemosResponseDto> memoDtoList = new ArrayList<>();

        if (memosList.isEmpty()) return memoDtoList;

        for(Memos memo : memosList) {
            memoDtoList.add(new MemosResponseDto(memo));
        }

        return memoDtoList;
    }

//    @Transactional
//    public List<MemosResponseDto> page(Pageable pageable) {
//        Page<Memos> memosList = memosRepository.findAll(pageable);
//        List<MemosResponseDto> memoDtoList = new ArrayList<>();
//
//        if (memosList.isEmpty()) return memoDtoList;
//
//        for(Memos memo : memosList) {
//            memoDtoList.add(new MemosResponseDto(memo));
//        }
//
//        return memoDtoList;
//    }
}
