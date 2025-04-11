package com.example.of_course.job;

import com.example.of_course.dto.JobAttribute;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class JobAttributeService {
    private final StatusRepository statusRepository;
    private final InterviewStageRepository interviewStageRepository;
    private final LevelRepository levelRepository;
    private final CompanyRepository companyRepository;

    public JobAttributeService(StatusRepository statusRepository, InterviewStageRepository interviewStageRepository, LevelRepository levelRepository, CompanyRepository companyRepository) {
        this.statusRepository = statusRepository;
        this.interviewStageRepository = interviewStageRepository;
        this.levelRepository = levelRepository;
        this.companyRepository = companyRepository;
    }

    public Map<String, List<JobAttribute>> getAllStatusTypes() {
        List<JobAttribute> statusTypesList = statusRepository.findAll()
                .stream()
                .map(status -> new JobAttribute(status.getId(), status.getLabel()))
                .toList();

        Map<String, List<JobAttribute>> statusTypes;
        statusTypes = Collections.singletonMap("statusTypes", statusTypesList);
        return statusTypes;
    }


    public Map<String, List<JobAttribute>> getAllLevels() {
        List<JobAttribute> levelsList = levelRepository.findAll()
                .stream()
                .map(level -> new JobAttribute(level.getId(), level.getLabel()))
                .toList();

        Map<String, List<JobAttribute>> levelsOutput;
        levelsOutput = Collections.singletonMap("levels", levelsList);
        return levelsOutput;
    }


    public Map<String, List<JobAttribute>> getInterviewStages() {
        List<JobAttribute> stagesList = interviewStageRepository.findAll()
                .stream()
                .map(level -> new JobAttribute(level.getId(), level.getLabel()))
                .toList();

        Map<String, List<JobAttribute>> stagesOutput;
        stagesOutput = Collections.singletonMap("stages", stagesList);
        return stagesOutput;
    }

    public Status getStatusById(int id) {
        return statusRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No such status with id" + id)
        );
    }


    public Level getLevelById(int id) {
        return levelRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No such level with id" + id)
        );
    }


    public InterviewStage getInterviewStageById(int id) {
        return interviewStageRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No such interview stage with id" + id)
        );
    }
}
