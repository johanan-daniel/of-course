package com.example.of_course.job;

import com.example.of_course.job.dto.CompanyDto;
import com.example.of_course.job.dto.JobAttributeDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobAttribute")
public class JobAttributeController {
    private final JobAttributeService jobAttributeService;

    public JobAttributeController(JobAttributeService jobAttributeService) {
        this.jobAttributeService = jobAttributeService;
    }

    @GetMapping("/statusTypes")
    public ResponseEntity<Map<String, List<JobAttributeDto>>> getAllStatusTypes() {
        Map<String, List<JobAttributeDto>> statusTypes = jobAttributeService.getAllStatusTypes();
        return ResponseEntity.ok(statusTypes);
    }


    @GetMapping("/levels")
    public ResponseEntity<Map<String, List<JobAttributeDto>>> getAllLevels() {
        Map<String, List<JobAttributeDto>> levels = jobAttributeService.getAllLevels();
        return ResponseEntity.ok(levels);
    }

    @GetMapping("/interviewStages")
    public ResponseEntity<Map<String, List<JobAttributeDto>>> getAllInterviewStages() {
        Map<String, List<JobAttributeDto>> stages = jobAttributeService.getInterviewStages();
        return ResponseEntity.ok(stages);
    }

    @GetMapping("/companies")
    public ResponseEntity<Map<String, List<CompanyDto>>> getAllCompanies() {
        Map<String, List<CompanyDto>> companies = jobAttributeService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }


    @GetMapping("/companies/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable int id) {
        CompanyDto company = jobAttributeService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @PostMapping("/companies")
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto) {
        CompanyDto createdCompany = jobAttributeService.createCompany(companyDto);
        URI location = URI.create("/api/jobAttribute/companies/" + createdCompany.getId());

        return ResponseEntity.created(location).body(createdCompany);
    }
}
