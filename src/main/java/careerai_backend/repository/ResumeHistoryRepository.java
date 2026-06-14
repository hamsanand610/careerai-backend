package careerai_backend.repository;

import careerai_backend.entity.ResumeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

public interface ResumeHistoryRepository
        extends JpaRepository<ResumeHistory, Long> {

    Optional<ResumeHistory>
    findTopByOrderByUploadDateDesc();

    List<ResumeHistory>
    findAllByOrderByUploadDateDesc();

    @Query("SELECT MAX(r.atsScore) FROM ResumeHistory r")
    Integer findBestScore();

    @Query("SELECT AVG(r.atsScore) FROM ResumeHistory r")
    Double findAverageScore();
}