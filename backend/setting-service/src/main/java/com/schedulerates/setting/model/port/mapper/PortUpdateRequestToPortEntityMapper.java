package com.schedulerates.setting.model.port.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.port.dto.request.PortUpdateRequest;
import com.schedulerates.setting.model.port.entity.PortEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

/**
 * Mapper interface named {@link PortUpdateRequestToPortEntityMapper} for
 * updating {@link PortEntity} using {@link PortUpdateRequest}.
 */
@Mapper
public interface PortUpdateRequestToPortEntityMapper extends BaseMapper<PortUpdateRequest, PortEntity> {
    /**
     * Maps MultipartFile to String (e.g., original filename).
     *
     * @param file MultipartFile to map.
     * @return String representation (e.g., original filename) or null if file is
     *         null.
     */
    default String map(MultipartFile file) {
        return (file != null) ? file.getOriginalFilename() : null;
    }

    /**
     * Maps PortUpdateRequest to PortEntity for saving purposes.
     *
     * @param PortUpdateRequest The PortUpdateRequest object to map.
     * @return PortEntity object containing mapped data.
     */
    @Named("mapForUpdating")
    default PortEntity mapForUpdating(PortUpdateRequest request) {
        String logoPath = null;
        if (request.getPortLogo() != null && !request.getPortLogo().isEmpty()) {
            // In a real app, you'd upload and get the saved file path here
            logoPath = request.getPortLogo().getOriginalFilename();
        }

        return PortEntity.builder()
                .portName(request.getPortName())
                .countryName(request.getCountryName())
                .countryNameAbbreviation(request.getCountryNameAbbreviation())
                .portCode(request.getPortCode())
                .portLongitude(request.getPortLatitude())
                .portLatitude(request.getPortLatitude())
                .portLogo(logoPath)
                .obs(request.getObs())
                .build();
    }
    /**
     * Initializes and returns an instance of
     * PortUpdateRequestToPortEntityMapper.
     *
     * @return Initialized PortUpdateRequestToPortEntityMapper instance.
     */
    static PortUpdateRequestToPortEntityMapper initialize() {
        return Mappers.getMapper(PortUpdateRequestToPortEntityMapper.class);
    }
}
