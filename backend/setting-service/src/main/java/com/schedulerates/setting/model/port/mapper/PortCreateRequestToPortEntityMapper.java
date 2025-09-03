package com.schedulerates.setting.model.port.mapper;

import com.schedulerates.setting.model.common.mapper.BaseMapper;
import com.schedulerates.setting.model.port.dto.request.PortCreateRequest;
import com.schedulerates.setting.model.port.entity.PortEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

/**
 * Mapper interface named {@link PortCreateRequestToPortEntityMapper} for
 * converting {@link PortCreateRequest} to {@link PortEntity}.
 */

@Mapper
public interface PortCreateRequestToPortEntityMapper extends BaseMapper<PortCreateRequest, PortEntity> {

    /**
     * Maps MultipartFile to String (e.g., original filename).
     *
     * @param file MultipartFile to map.
     * @return String representation (e.g., original filename) or null if file is null.
     */
    default String map(MultipartFile file) {
        return (file != null) ? file.getOriginalFilename() : null;
    }

    /**
     * Maps PortCreateRequest to PortEntity for saving purposes.
     *
     * @param portCreateRequest The PortCreateRequest object to map.
     * @return PortEntity object containing mapped data.
     */
    @Named("mapForSaving")
    default PortEntity mapForSaving(PortCreateRequest request) {
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
     * PortCreateRequestToPortEntityMapper.
     *
     * @return Initialized PortCreateRequestToPortEntityMapper instance.
     */
    static PortCreateRequestToPortEntityMapper initialize() {
        return Mappers.getMapper(PortCreateRequestToPortEntityMapper.class);
    }

}
