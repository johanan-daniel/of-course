package com.example.of_course.unit.job;

import com.example.of_course.exception.CompanyNameAlreadyExistsException;
import com.example.of_course.job.JobAttributeController;
import com.example.of_course.job.JobAttributeService;
import com.example.of_course.job.dto.CompanyDto;
import com.example.of_course.job.dto.JobAttributeDto;
import com.example.of_course.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(JobAttributeController.class)
@Tag("unit")
public class JobAttributeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobAttributeService jobAttributeService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtService jwtService;

    private static final String JOB_ATTRIBUTE_URL = "/api/jobAttribute";


    @Test
    void whenGetAllStatusTypes_thenReturnStatusTypes() throws Exception {
        when(jobAttributeService.getAllStatusTypes())
                .thenReturn(
                        Map.of("statusTypes",
                                List.of(new JobAttributeDto(1, "Applied"))
                        )
                );

        mockMvc.perform(get(JOB_ATTRIBUTE_URL + "/statusTypes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusTypes").exists());

        verify(jobAttributeService).getAllStatusTypes();
    }

    @Test
    void whenGetAllLevels_thenReturnLevels() throws Exception {
        when(jobAttributeService.getAllLevels())
                .thenReturn(
                        Map.of("levels",
                                List.of(new JobAttributeDto(1, "Entry level"))
                        )
                );

        mockMvc.perform(get(JOB_ATTRIBUTE_URL + "/levels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.levels").exists());

        verify(jobAttributeService).getAllLevels();
    }

    @Test
    void whenGetAllInterviewStages_thenReturnInterviewStages() throws Exception {
        when(jobAttributeService.getInterviewStages())
                .thenReturn(
                        Map.of("interviewStages",
                                List.of(new JobAttributeDto(1, "Phone screen"))
                        )
                );

        mockMvc.perform(get(JOB_ATTRIBUTE_URL + "/interviewStages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.interviewStages").exists());

        verify(jobAttributeService).getInterviewStages();
    }

    @Test
    void whenGetAllCompanies_thenReturnCompanies() throws Exception {
        when(jobAttributeService.getAllCompanies())
                .thenReturn(
                        Map.of("companies",
                                List.of(new CompanyDto(1, "Google", null))
                        )
                );

        mockMvc.perform(get(JOB_ATTRIBUTE_URL + "/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companies").exists());

        verify(jobAttributeService).getAllCompanies();
    }

    @Test
    void whenGetCompanyById_thenReturnCompany() throws Exception {
        when(jobAttributeService.getCompanyById(1))
                .thenReturn(new CompanyDto(1, "Google", "https://careers.google.com"));

        mockMvc.perform(get(JOB_ATTRIBUTE_URL + "/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Google"))
                .andExpect(jsonPath("$.careersPageUrl").value("https://careers.google.com"));

        verify(jobAttributeService).getCompanyById(1);
    }

    @Test
    void whenGetCompanyByIdNotFound_thenReturn404() throws Exception {
        when(jobAttributeService.getCompanyById(999))
                .thenThrow(new EntityNotFoundException("No such company with id 999"));

        mockMvc.perform(get(JOB_ATTRIBUTE_URL + "/companies/999"))
                .andExpect(status().isNotFound());

        verify(jobAttributeService).getCompanyById(999);
    }

    @Test
    void whenValidCreateCompany_thenReturnCreatedCompany() throws Exception {
        CompanyDto companyDtoOutput = new CompanyDto(1, "Google", "https://careers.google.com");
        // CHECK null pointer exception when passing companyDtoOutput to createCompany instead of any
        when(jobAttributeService.createCompany(any(CompanyDto.class))).thenReturn(companyDtoOutput);
        String companyDtoJson = objectMapper.writeValueAsString(companyDtoOutput);


        mockMvc.perform(post(JOB_ATTRIBUTE_URL + "/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyDtoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(companyDtoOutput.getId()))
                .andExpect(jsonPath("$.name").value(companyDtoOutput.getName()))
                .andExpect(jsonPath("$.careersPageUrl").value(companyDtoOutput.getCareersPageUrl()));

        verify(jobAttributeService).createCompany(any(CompanyDto.class));
    }

    @Test
    void whenCreateCompanyWithExistingName_thenReturn409() throws Exception {
        CompanyDto companyDtoInput = new CompanyDto(1, "Google", "https://careers.google.com");
        when(jobAttributeService.createCompany(any(CompanyDto.class)))
                .thenThrow(new CompanyNameAlreadyExistsException("Company with name Google already exists"));

        String companyDtoJson = objectMapper.writeValueAsString(companyDtoInput);

        mockMvc.perform(post(JOB_ATTRIBUTE_URL + "/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyDtoJson))
                .andExpect(status().isConflict());

        verify(jobAttributeService).createCompany(any(CompanyDto.class));
    }

    @Test
    void whenCreateCompanyWithInvalidName_thenReturn400() throws Exception {
        CompanyDto companyDtoInput = new CompanyDto(1, "", "https://careers.google.com");
        String companyDtoJson = objectMapper.writeValueAsString(companyDtoInput);
        when(jobAttributeService.createCompany(any(CompanyDto.class)))
                .thenThrow(new IllegalArgumentException("Company name cannot be empty"));

        mockMvc.perform(post(JOB_ATTRIBUTE_URL + "/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyDtoJson))
                .andExpect(status().isBadRequest());

        verify(jobAttributeService).createCompany(any(CompanyDto.class));
    }
}
