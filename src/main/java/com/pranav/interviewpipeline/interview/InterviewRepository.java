package com.pranav.interviewpipeline.interview;

import com.pranav.interviewpipeline.stage.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByStageIdOrderByPosition(long stageId);

    Interview findTopByStageIdOrderByPositionDesc(long stageId);

    int deleteByStage(Stage stage);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Interview SET position = position + 1 WHERE position>=? and position< ? and stage_id = ?", nativeQuery = true)
    int upShift(long newPosition, long oldPosition, long stageId);

    @Transactional
    @Modifying
    @Query(value = "update interview set position = position - 1 where position<= ? and position> ? and stage_id = ?", nativeQuery = true)
    int downShift(long newPosition, long oldPosition, long stageId);
}
