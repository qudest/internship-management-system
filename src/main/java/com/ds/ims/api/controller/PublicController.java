package com.ds.ims.api.controller;

import com.ds.ims.api.dto.InternshipDto;
import com.ds.ims.api.service.InternshipService;
import com.ds.ims.api.utils.ApiPaths;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(ApiPaths.PUBLIC)
public class PublicController {
    InternshipService internshipService;
    @GetMapping(ApiPaths.INTERNSHIPS)
    public List<InternshipDto> getInternships() {
        return internshipService.getInternshipsOpenForRegistration();
    }

    @GetMapping(ApiPaths.INTERNSHIP_BY_ID)
    public InternshipDto getInternshipById(@PathVariable Long id) {
        return internshipService.getInternshipById(id).get();
    }
}
