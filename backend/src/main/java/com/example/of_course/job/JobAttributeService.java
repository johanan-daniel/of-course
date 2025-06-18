package com.example.of_course.job;

import com.example.of_course.exception.CompanyNameAlreadyExistsException;
import com.example.of_course.job.dto.CompanyDto;
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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
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

    public Status getStatusById(int id) {
        return statusRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No such status with id" + id)
        );
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

    public Level getLevelById(int id) {
        return levelRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No such level with id" + id)
        );
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

    public InterviewStage getInterviewStageById(int id) {
        return interviewStageRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No such interview stage with id" + id)
        );
    }


    public Map<String, List<CompanyDto>> getAllCompanies() {
        List<CompanyDto> companiesList = companyRepository.findAll()
                .stream()
                .map(company -> new
                        CompanyDto(company.getId(), company.getName(), company.getCareersPageUrl())
                )
                .toList();

        Map<String, List<CompanyDto>> companiesOutput;
        companiesOutput = Collections.singletonMap("companies", companiesList);
        return companiesOutput;
    }


    public CompanyDto getCompanyById(int id) {
        Company company = companyRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No such company with id" + id)
        );

        return new CompanyDto(company.getId(), company.getName(), company.getCareersPageUrl());
    }

    public CompanyDto createCompany(CompanyDto companyDto) {
        System.out.println(companyDto.toString());

        if (companyDto.getName() == null || companyDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Company name is required");
        }

        if (companyDto.getName().length() > 100) {
            throw new IllegalArgumentException("Company name must be less than 100 characters");
        }

        if (companyDto.getCareersPageUrl() != null && companyDto.getCareersPageUrl().length() > 500) {
            throw new IllegalArgumentException("Careers page URL must be less than 500 characters");
        }

        if (companyRepository.existsByName(companyDto.getName())) {
            throw new CompanyNameAlreadyExistsException("Company with this name already exists", companyDto.getName());
        }

        Company company = new Company(companyDto.getName(), companyDto.getCareersPageUrl());
        // Saved company will have an auto-generated ID added
        Company savedCompany = companyRepository.save(company);
        return new CompanyDto(savedCompany.getId(), savedCompany.getName(), savedCompany.getCareersPageUrl());
    }
}
