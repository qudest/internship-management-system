package com.ds.ims.api.controller;

import com.ds.ims.api.dto.InternshipDto;
import com.ds.ims.api.service.InternshipService;
import com.ds.ims.api.utils.ApiPaths;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для публичной части
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(ApiPaths.PUBLIC)
public class PublicController {
    InternshipService internshipService;

    /**
     * Получение списка стажировок, открытых для регистрации
     * GET api/public/internships
     *
     * @return - список стажировок
     */
    @GetMapping(ApiPaths.INTERNSHIPS)
    public List<InternshipDto> getInternships() {
        return internshipService.getInternshipsOpenForRegistration();
    }

    /**
     * Получение стажировки по идентификатору
     * GET api/public/internships/{internshipId}
     *
     * @param internshipId - идентификатор стажировки
     * @return - стажировка
     */
    @GetMapping(ApiPaths.INTERNSHIP_BY_ID)
    public InternshipDto getInternshipById(@PathVariable Long internshipId) {
        return internshipService.getInternshipById(internshipId);
    }
}
