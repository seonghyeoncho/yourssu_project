package org.yourssuproject.domain.memos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MemosRepository extends JpaRepository<Memos, Long> {
    @Query("SELECT m FROM Memos m ORDER BY m.id DESC")
    List<Memos> findAllDesc();

    List<Memos> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
