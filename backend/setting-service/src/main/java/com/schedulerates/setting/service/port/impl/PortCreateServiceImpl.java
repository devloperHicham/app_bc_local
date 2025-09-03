package com.schedulerates.setting.service.port.impl;

import com.schedulerates.setting.exception.AlreadyExistException;
import com.schedulerates.setting.model.port.Port;
import com.schedulerates.setting.model.port.dto.request.PortCreateRequest;
import com.schedulerates.setting.model.port.entity.PortEntity;
import com.schedulerates.setting.model.port.mapper.PortEntityToPortMapper;
import com.schedulerates.setting.repository.PortRepository;
import com.schedulerates.setting.service.port.PortCreateService;
import com.schedulerates.setting.storage.StorageImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service implementation for creating companies with logo upload support.
 */
@Service
@RequiredArgsConstructor
public class PortCreateServiceImpl implements PortCreateService {

    private final PortRepository portRepository;
    private final StorageImageService storageService;

    private final PortEntityToPortMapper portEntityToPortMapper = PortEntityToPortMapper.initialize();

    /**
     * Creates a new port with logo upload support.
     *
     * @param portCreateRequest The port creation request
     * @param logoFile             Optional logo file (can be null)
     * @return The created Port object
     * @throws AlreadyExistException If port name exists
     */
    @Override
    public Port createPort(PortCreateRequest portCreateRequest, MultipartFile logoFile) {
        checkUniquenessPortName(portCreateRequest.getPortName());

        String storedFilename = null;
        if (logoFile != null && !logoFile.isEmpty()) {
            storedFilename = storageService.store(logoFile);
        }

        // Instead of setting it back on portCreateRequest, pass filename to mapper
        // or create entity here
        PortEntity portEntity = PortEntity.builder()
                .portName(portCreateRequest.getPortName())
                .countryName(portCreateRequest.getCountryName())
                .countryNameAbbreviation(portCreateRequest.getCountryNameAbbreviation())
                .portCode(portCreateRequest.getPortCode())
                .portLatitude(portCreateRequest.getPortLatitude())
                .portLongitude(portCreateRequest.getPortLongitude())
                .portLogo(storedFilename) // filename string, not MultipartFile
                .obs(portCreateRequest.getObs())
                .build();

        PortEntity savedEntity = portRepository.save(portEntity);

        return portEntityToPortMapper.map(savedEntity);
    }

    /**
     * Checks port name uniqueness
     */
    private void checkUniquenessPortName(String portName) {
        if (portRepository.existsPortEntityByPortName(portName)) {
            throw new AlreadyExistException("Port with name '" + portName + "' already exists");
        }
    }
}
