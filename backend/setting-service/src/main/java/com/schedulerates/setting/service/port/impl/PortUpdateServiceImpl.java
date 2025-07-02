package com.schedulerates.setting.service.port.impl;

import com.schedulerates.setting.exception.AlreadyExistException;
import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.port.Port;
import com.schedulerates.setting.model.port.dto.request.PortUpdateRequest;
import com.schedulerates.setting.model.port.entity.PortEntity;
import com.schedulerates.setting.model.port.mapper.PortEntityToPortMapper;
import com.schedulerates.setting.repository.PortRepository;
import com.schedulerates.setting.service.port.PortUpdateService;
import com.schedulerates.setting.storage.StorageImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PortUpdateServiceImpl implements PortUpdateService {

    private final PortRepository portRepository;
    private final StorageImageService storageService;

    private final PortEntityToPortMapper portEntityToPortMapper = PortEntityToPortMapper.initialize();

    /**
     * Updates a port with logo upload support.
     *
     * @param updateRequest The port update request
     * @param logoFile      Optional logo file (can be null)
     * @return The updated Port object
     * @throws AlreadyExistException If port name exists
     */
    @Override
    public Port updatePortById(String portId,
            PortUpdateRequest updateRequest,
            MultipartFile newLogoFile) {

        // 1. Fetch existing port
        PortEntity entity = portRepository.findById(portId)
                .orElseThrow(() -> new NotFoundException("Port not found"));

        // 2. Validate at least one field is being updated
        if (updateRequest.getPortName() == null && (newLogoFile == null || newLogoFile.isEmpty())) {
            throw new IllegalArgumentException("At least one field (name or logo) must be updated");
        }

        // 3. Handle port name update (if provided)
        if (updateRequest.getPortName() != null) {
            validatePortNameUniqueness(updateRequest.getPortName(), entity.getPortName());
            entity.setPortName(updateRequest.getPortName());
        }

        // 4. Handle logo update (if provided)
        if (newLogoFile != null && !newLogoFile.isEmpty()) {
            updatePortLogo(entity, newLogoFile);
        }

        // 5. Update country name if provided
        if (updateRequest.getCountryName() != null) {
            entity.setCountryName(updateRequest.getCountryName());
        }

        // 6. Update port code if provided
        if (updateRequest.getPortCode() != null) {
            entity.setPortCode(updateRequest.getPortCode());
        }

        // 7. Update latitude,  if provided
        if (updateRequest.getPortLatitude() != null) {
            entity.setPortLatitude(updateRequest.getPortLatitude());
        }

        // 8. Update longitude if provided
        if (updateRequest.getPortLongitude() != null) {
            entity.setPortLongitude(updateRequest.getPortLongitude());
        }

        // 9. Update observation if provided
        if (updateRequest.getObs() != null) {
            entity.setObs(updateRequest.getObs());
        }

        // 6. Save the updated entity
        PortEntity savedEntity = portRepository.save(entity);

        return portEntityToPortMapper.map(savedEntity);
    }

    private void updatePortLogo(PortEntity entity, MultipartFile newLogoFile) {
        try {
            String storedFilename = storageService.store(newLogoFile);
            entity.setPortLogo(storedFilename);
        } catch (Exception e) {
            throw new NotFoundException("Failed to update port logo: " + e.getMessage());
        }
    }

    private void validatePortNameUniqueness(String newName, String currentName) {
        if (!newName.equals(currentName) && portRepository.existsPortEntityByPortName(newName)) {
            throw new AlreadyExistException("Port name already exists");
        }
    }
}
