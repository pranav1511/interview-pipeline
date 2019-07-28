package com.pranav.interviewpipeline.interview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewService {

    @Autowired
    InterviewRepository interviewRepository;

    public List<Interview> getAllInterviews(long stageId) {
        return interviewRepository.findByStageIdOrderByPosition(stageId);
    }

    public Interview getInterview(long id) {
        return interviewRepository.findById(id).orElse(null);
    }

    public void addInterview(Interview interview) {
        Interview lastInterview = interviewRepository.findTopByStageIdOrderByPositionDesc(interview.getStage().getId());
        long position = 1;
        if(lastInterview != null) {
            position = lastInterview.getPosition() + 1;
        }
        interview.setPosition(position);
        interviewRepository.save(interview);
    }

    public void updateInterview(Interview interview) {
        Interview oldInterview = getInterview(interview.getId());
        if(interview.getStage().getId() != oldInterview.getStage().getId())
            return;
        if(interview.getName()==null) {
            interview.setName(oldInterview.getName());
        }
        if(interview.getPosition() == 0) {
            interview.setPosition(oldInterview.getPosition());
        }
        else {
            if(interview.getPosition() < oldInterview.getPosition()) {
                interview.setPosition(interview.getPosition()<=0 ? 1: interview.getPosition());
                interviewRepository.upShift(interview.getPosition(), oldInterview.getPosition(), interview.getStage().getId());
            }
            else if(interview.getPosition() > oldInterview.getPosition()) {
                long lastInterviewPosition = interviewRepository.findTopByStageIdOrderByPositionDesc(interview.getStage().getId()).getPosition();
                interview.setPosition(interview.getPosition()>lastInterviewPosition ? lastInterviewPosition: interview.getPosition());
                interviewRepository.downShift(interview.getPosition(), oldInterview.getPosition(), interview.getStage().getId());
            }
        }
        interviewRepository.save(interview);
    }

    public void deleteInterview(long id) {
        Interview interview = getInterview(id);
        Interview lastInterview = interviewRepository.findTopByStageIdOrderByPositionDesc(interview.getStage().getId());
        interviewRepository.deleteById(id);
        interviewRepository.downShift(lastInterview.getPosition(), interview.getPosition(), interview.getStage().getId());
    }
}