package com.example.of_course.job;

import com.example.of_course.dto.JobAttribute;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Map<String, List<JobAttribute>>> getAllStatusTypes() {
        Map<String, List<JobAttribute>> statusTypes = jobAttributeService.getAllStatusTypes();
        return ResponseEntity.ok(statusTypes);
    }


    @GetMapping("/levels")
    public ResponseEntity<Map<String, List<JobAttribute>>> getAllLevels() {
        Map<String, List<JobAttribute>> levels = jobAttributeService.getAllLevels();
        return ResponseEntity.ok(levels);
    }

    @GetMapping("/interviewStages")
    public ResponseEntity<Map<String, List<JobAttribute>>> getAllInterviewStages() {
        Map<String, List<JobAttribute>> stages = jobAttributeService.getInterviewStages();
        return ResponseEntity.ok(stages);
    }
}
