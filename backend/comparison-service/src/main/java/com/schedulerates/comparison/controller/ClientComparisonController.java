package com.schedulerates.comparison.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.schedulerates.comparison.model.common.dto.response.CustomResponse;
import com.schedulerates.comparison.model.comparison.ClientComparison;
import com.schedulerates.comparison.model.comparison.dto.request.ClientComparisonCreateRequest;
import com.schedulerates.comparison.service.comparison.ClientComparisonCreateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/comparisons/clients")
@RequiredArgsConstructor
@Validated
public class ClientComparisonController {

    private final ClientComparisonCreateService clientComparisonCreateService;

    @PostMapping("/create-comp-clients")
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    public CustomResponse<List<String>> createComparison(
            @RequestBody @Valid final ClientComparisonCreateRequest clientComparisonCreateRequest) {

        List<ClientComparison> createdComparisons = clientComparisonCreateService
                .createComparison(clientComparisonCreateRequest);

        // Extract IDs of all created comparisons
        List<String> createdIds = createdComparisons.stream()
                .map(ClientComparison::getId)
                .toList();

        return CustomResponse.successOf(createdIds);
    }
}
