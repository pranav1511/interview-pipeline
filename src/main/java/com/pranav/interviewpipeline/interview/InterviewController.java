package com.pranav.interviewpipeline.interview;

import com.pranav.interviewpipeline.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InterviewController {

    @Autowired
    InterviewService interviewService;

    @GetMapping("/stages/{stageId}/interviews")
    public List<Interview> getAllInterviews(@PathVariable long stageId) {
        return interviewService.getAllInterviews(stageId);
    }

    @GetMapping("/stages/{stageId}/interviews/{id}")
    public Interview getInterview(@PathVariable long id) {
        return interviewService.getInterview(id);
    }

    @PostMapping("/stages/{stageId}/interviews")
    public void addInterview(@RequestBody Interview interview, @PathVariable long stageId) {
        interview.setStage(new Stage(stageId,"", 0L ));
        interviewService.addInterview(interview);
    }

    @PutMapping("/stages/{stageId}/interviews/{id}")
    public void updateInterview(@RequestBody Interview interview, @PathVariable long stageId, @PathVariable long id) {
        interview.setStage(new Stage(stageId,"", 0L ));
        interview.setId(id);
        interviewService.updateInterview(interview);
    }

    @DeleteMapping("/stages/{stageId}/interviews/{id}")
    public void deleteInterview(@PathVariable long id) {
        interviewService.deleteInterview(id);
    }

}
