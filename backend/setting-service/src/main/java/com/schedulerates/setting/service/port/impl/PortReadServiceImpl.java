package com.schedulerates.setting.service.port.impl;

import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.port.Port;
import com.schedulerates.setting.model.port.dto.request.PortPagingRequest;
import com.schedulerates.setting.model.port.entity.PortEntity;
import com.schedulerates.setting.model.port.mapper.ListPortEntityToListPortMapper;
import com.schedulerates.setting.model.port.mapper.PortEntityToPortMapper;
import com.schedulerates.setting.repository.PortRepository;
import com.schedulerates.setting.service.port.PortReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation named {@link PortReadServiceImpl} for reading
 * companies.
 */
@Service
@RequiredArgsConstructor
public class PortReadServiceImpl implements PortReadService {

    private final PortRepository portRepository;

    private final PortEntityToPortMapper portEntityToPortMapper = PortEntityToPortMapper.initialize();

    private final ListPortEntityToListPortMapper listPortEntityToListPortMapper = ListPortEntityToListPortMapper
            .initialize();

    /**
     * Retrieves a port by its unique ID.
     *
     * @param portId The ID of the port to retrieve.
     * @return The Port object corresponding to the given ID.
     * @throws PortNotFoundException If no port with the given ID exists.
     */
    @Override
    public Port getPortById(String portId) {

        final PortEntity portEntityFromDB = portRepository
                .findById(portId)
                .orElseThrow(() -> new NotFoundException("With given portID = " + portId));

        return portEntityToPortMapper.map(portEntityFromDB);
    }

    /**
     * Retrieves all ports.
     *
     * @return a list of all ports
     */
    @Override
    public List<Port> getAllPorts() {
        List<PortEntity> entities = portRepository.findAll();
        return entities.stream()
                .map(portEntityToPortMapper::map)
                .toList();
    }
    /**
     * Retrieves a page of ports based on the paging request criteria.
     *
     * @param portPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of ports that match the paging
     *         criteria.
     * @throws PortNotFoundException If no ports are found based on the
     *                                  paging criteria.
     */
    @Override
    public CustomPage<Port> getPorts(PortPagingRequest portPagingRequest) {
        final Page<PortEntity> faqEntityPage;
        if (portPagingRequest.getSearch() != null && !portPagingRequest.getSearch().isEmpty()) {
            faqEntityPage = portRepository
                    .findByPortNameContainingIgnoreCase(portPagingRequest.getSearch(),
                            portPagingRequest.toPageable());
        } else {
            faqEntityPage = portRepository.findAll(portPagingRequest.toPageable());
        }

        final List<Port> faqDomainModels = listPortEntityToListPortMapper
                .toPortList(faqEntityPage.getContent());

        return CustomPage.of(faqDomainModels, faqEntityPage);

    }

}
