package com.pranav.interviewpipeline.stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StageService {

    @Autowired
    StageRepository stageRepository;

    public List<Stage> getAllStages() {
        List<Stage> stages = stageRepository.findAllByOrderByPosition();
        long lastStageId = 0;
        long lastStagePosition = 0;
        if(stages.size() > 0) {
            lastStageId = stages.get(stages.size() - 1).getId();
            lastStagePosition = stages.get(stages.size() - 1).getPosition();
        }
        stages.add(new Stage(lastStageId + 1, "Offered", lastStagePosition + 1));
        stages.add(new Stage(lastStageId + 2, "Hired", lastStagePosition + 2));
        return stages;
    }

    public Stage getStage(long id) {
        return stageRepository.findById(id).orElse(null);
    }

    public void addStage(Stage stage) {
        Stage lastStage = stageRepository.findTopByOrderByPositionDesc();
        long position = 1;
        if(lastStage != null) {
            position = lastStage.getPosition() + 1;
        }
        stage.setPosition(position);
        stageRepository.save(stage);
    }

    public void updateStage(Stage stage) {
        Stage oldStage = getStage(stage.getId());
        if(stage.getName()==null) {
            stage.setName(oldStage.getName());
        }
        if(stage.getPosition() == 0) {
            stage.setPosition(oldStage.getPosition());
        }
        else {
            if(stage.getPosition() < oldStage.getPosition()) {
                stage.setPosition(stage.getPosition()<=0 ? 1: stage.getPosition());
                stageRepository.upShift(stage.getPosition(), oldStage.getPosition());
            }
            else if(stage.getPosition() > oldStage.getPosition()) {
                long lastStagePosition = stageRepository.findTopByOrderByPositionDesc().getPosition();
                stage.setPosition(stage.getPosition()>lastStagePosition ? lastStagePosition: stage.getPosition());
                stageRepository.downShift(stage.getPosition(), oldStage.getPosition());
            }
        }
        stageRepository.save(stage);
    }

    public void deleteStage(long id) {
        Stage stage = getStage(id);
        Stage lastStage = stageRepository.findTopByOrderByPositionDesc();
        stageRepository.deleteById(id);
        stageRepository.downShift(lastStage.getPosition(), stage.getPosition());
    }
}
