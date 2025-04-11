package com.example.of_course.job;

import com.example.of_course.job.dto.JobAttributeDto;
import com.example.of_course.job.entity.Company;
import com.example.of_course.job.entity.InterviewStage;
import com.example.of_course.job.entity.Level;
import com.example.of_course.job.entity.Status;
import com.example.of_course.job.repository.CompanyRepository;
import com.example.of_course.job.repository.InterviewStageRepository;
import com.example.of_course.job.repository.LevelRepository;
import com.example.of_course.job.repository.StatusRepository;
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

    public Map<String, List<JobAttributeDto>> getAllStatusTypes() {
        List<JobAttributeDto> statusTypesList = statusRepository.findAll()
                .stream()
                .map(status -> new JobAttributeDto(status.getId(), status.getLabel()))
                .toList();

        Map<String, List<JobAttributeDto>> statusTypes;
        statusTypes = Collections.singletonMap("statusTypes", statusTypesList);
        return statusTypes;
    }


    public Map<String, List<JobAttributeDto>> getAllLevels() {
        List<JobAttributeDto> levelsList = levelRepository.findAll()
                .stream()
                .map(level -> new JobAttributeDto(level.getId(), level.getLabel()))
                .toList();

        Map<String, List<JobAttributeDto>> levelsOutput;
        levelsOutput = Collections.singletonMap("levels", levelsList);
        return levelsOutput;
    }


    public Map<String, List<JobAttributeDto>> getInterviewStages() {
        List<JobAttributeDto> stagesList = interviewStageRepository.findAll()
                .stream()
                .map(level -> new JobAttributeDto(level.getId(), level.getLabel()))
                .toList();

        Map<String, List<JobAttributeDto>> stagesOutput;
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

    public Company getCompanyById(int id) {
        return companyRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No such company with id" + id)
        );
    }
}
