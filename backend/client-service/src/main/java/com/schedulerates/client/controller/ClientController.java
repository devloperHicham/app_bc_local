package com.schedulerates.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller named {@link ScheduleController} for managing Schedules.
 * Provides endpoints to create, read, update, and delete Schedules.
 */
@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Validated
public class ClientController {

}