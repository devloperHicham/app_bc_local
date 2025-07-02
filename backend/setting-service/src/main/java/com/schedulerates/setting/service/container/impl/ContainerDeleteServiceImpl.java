package com.schedulerates.setting.service.container.impl;

import com.schedulerates.setting.client.ComparisonServiceClient;
import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.container.entity.ContainerEntity;
import com.schedulerates.setting.repository.ContainerRepository;
import com.schedulerates.setting.service.container.ContainerDeleteService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link ContainerDeleteServiceImpl} for deleting Containers.
 */
@Service
@RequiredArgsConstructor
public class ContainerDeleteServiceImpl implements ContainerDeleteService {

    private final ContainerRepository containerRepository;
    private final ComparisonServiceClient comparisonServiceClient;
    private final HttpServletRequest httpServletRequest; // Inject the current request

    /**
     * Deletes a Container identified by its unique ID.
     *
     * @param ContainerId The ID of the Container to delete.
     * @throws ContainerNotFoundException If no Container with the given ID exists.
     */
    @Override
    public void deleteContainerById(String containerId) {
        String authHeader = httpServletRequest.getHeader("Authorization");

        final ContainerEntity containerEntityToBeDelete = containerRepository
                .findById(containerId)
                .orElseThrow(() -> new NotFoundException("With given containerID = " + containerId));

                // Check if in use
        boolean isUsedComparison = comparisonServiceClient.existsByCompanyById(containerId, authHeader);
        if (isUsedComparison) {
            throw new NotFoundException("container is in use");
        }
        containerRepository.delete(containerEntityToBeDelete);

    }

}