package com.example.of_course.unit.job;

import com.example.of_course.exception.CompanyNameAlreadyExistsException;
import com.example.of_course.job.JobAttributeService;
import com.example.of_course.job.dto.CompanyDto;
import com.example.of_course.job.dto.JobAttributeDto;
import com.example.of_course.job.entity.Company;
import com.example.of_course.job.entity.Status;
import com.example.of_course.job.repository.CompanyRepository;
import com.example.of_course.job.repository.InterviewStageRepository;
import com.example.of_course.job.repository.LevelRepository;
import com.example.of_course.job.repository.StatusRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class JobAttributeServiceTest {
    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private LevelRepository levelRepository;

    @Mock
    private InterviewStageRepository interviewStageRepository;

    @InjectMocks
    private JobAttributeService jobAttributeService;

    @Nested
    class StatusTypeTest {
        @Test
        void getAllStatusTypes() {
            List<Status> statusList = List.of(new Status(1, "Applied"), new Status(2, "OA"));
            when(statusRepository.findAll()).thenReturn(statusList);

            Map<String, List<JobAttributeDto>> result = jobAttributeService.getAllStatusTypes();

            assertThat(result)
                    .as("Result should have 1 key named statusTypes")
                    .containsOnlyKeys("statusTypes");
            assertThat(result.get("statusTypes")).
                    as("List should have %d items", statusList.size())
                    .hasSize(statusList.size());
        }

        @Test
        void whenCreateCompanyWithNoName_thenThrowsIllegalArgument() {
            CompanyDto companyDto = new CompanyDto("");

            assertThrows(IllegalArgumentException.class, () -> jobAttributeService.createCompany(companyDto));
        }

        @Test
        void whenCreateCompanyWithNameTooLong_thenThrowsIllegalArgument() {
            String longName = "a".repeat(101);
            CompanyDto companyDto = new CompanyDto(longName);

            assertThrows(IllegalArgumentException.class, () -> jobAttributeService.createCompany(companyDto));
        }

        @Test
        void whenCreateCompanyWithCareersPageUrlTooLong_thenThrowsIllegalArgument() {
            String longUrl = "a".repeat(501);
            CompanyDto companyDto = new CompanyDto("Company", longUrl);

            assertThrows(IllegalArgumentException.class, () -> jobAttributeService.createCompany(companyDto));
        }

        @Test
        void whenCreateCompanyWithNameAlreadyExists_thenThrowsCompanyNameAlreadyExists() {
            String companyName = "Company";
            CompanyDto companyDto = new CompanyDto(companyName);

            when(companyRepository.existsByName(companyName)).thenReturn(true);

            assertThrows(CompanyNameAlreadyExistsException.class, () -> jobAttributeService.createCompany(companyDto));
        }

        @Test
        void whenValidCreateCompany_thenReturnCompanyDto() {
            String companyName = "Company";
            String careersPageUrl = "http://example.com";
            CompanyDto companyDto = new CompanyDto(companyName, careersPageUrl);

            // CHECK any string instead of companyName?
            when(companyRepository.existsByName(companyName)).thenReturn(false);
            when(companyRepository.save(any(Company.class))).thenReturn(new Company(companyName, careersPageUrl));

            CompanyDto result = jobAttributeService.createCompany(companyDto);

            assertThat(result.getName()).isEqualTo(companyName);
            assertThat(result.getCareersPageUrl()).isEqualTo(careersPageUrl);
        }
    }
}
