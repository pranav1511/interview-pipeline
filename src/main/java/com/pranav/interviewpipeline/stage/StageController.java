package com.pranav.interviewpipeline.stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StageController {

    @Autowired
    StageService stageService;

    @GetMapping("/stages")
    public List<Stage> getAllStages() {
        return stageService.getAllStages();
    }

    @GetMapping("/stages/{id}")
    public Stage getStage(@PathVariable long id) {
        return stageService.getStage(id);
    }

    @PostMapping("/stages")
    public void addStages(@RequestBody Stage stage) {
        stageService.addStage(stage);
    }

    @PutMapping("/stages/{id}")
    public void updateStage(@RequestBody Stage stage, @PathVariable long id) {
        stage.setId(id);
        stageService.updateStage(stage);
    }

    @DeleteMapping("/stages/{id}")
    public void deleteTopic(@PathVariable long id) {
        stageService.deleteStage(id);
    }

}
