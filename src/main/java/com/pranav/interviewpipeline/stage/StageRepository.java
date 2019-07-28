package com.pranav.interviewpipeline.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {

    List<Stage> findAllByOrderByPosition();

    Stage findTopByOrderByPositionDesc();

    @Transactional
    @Modifying
    @Query(value = "update stage set position = position + 1 where position>= ? and position< ?", nativeQuery = true)
    int upShift(long newPosition, long oldPosition);

    @Transactional
    @Modifying
    @Query(value = "update stage set position = position - 1 where position<= ? and position> ?", nativeQuery = true)
    int downShift(long newPosition, long oldPosition);
}
