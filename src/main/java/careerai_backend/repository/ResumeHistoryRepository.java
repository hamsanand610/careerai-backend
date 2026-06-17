package careerai_backend.repository;

import careerai_backend.entity.ResumeHistory;
import careerai_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResumeHistoryRepository
        extends JpaRepository<ResumeHistory, Long> {

    Optional<ResumeHistory>
    findTopByUserOrderByUploadDateDesc(User user);

    List<ResumeHistory>
    findByUserOrderByUploadDateDesc(User user);

    @Query("""
            SELECT MAX(r.atsScore)
            FROM ResumeHistory r
            WHERE r.user = :user
            """)
    Integer findBestScoreByUser(User user);

    @Query("""
            SELECT AVG(r.atsScore)
            FROM ResumeHistory r
            WHERE r.user = :user
            """)
    Double findAverageScoreByUser(User user);

    long countByUser(User user);
}