package com.ds.ims.api.controller;

import com.ds.ims.api.dto.InternshipDto;
import com.ds.ims.api.utils.ApiPaths;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(ApiPaths.PUBLIC)
public class PublicController {
    @GetMapping(ApiPaths.INTERNSHIPS)
    public List<InternshipDto> getInternships() {
        return null;
    }

    @GetMapping(ApiPaths.INTERNSHIP_BY_ID)
    public InternshipDto getInternshipById(@PathVariable Long id) {
        return null;
    }
}
