package com.schedulerates.setting.service.transportation.impl;

import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.transportation.Transportation;
import com.schedulerates.setting.model.transportation.dto.request.TransportationPagingRequest;
import com.schedulerates.setting.model.transportation.entity.TransportationEntity;
import com.schedulerates.setting.model.transportation.mapper.ListTransportationEntityToListTransportationMapper;
import com.schedulerates.setting.model.transportation.mapper.TransportationEntityToTransportationMapper;
import com.schedulerates.setting.repository.TransportationRepository;
import com.schedulerates.setting.service.transportation.TransportationReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation named {@link TransportationReadServiceImpl} for
 * reading Transportations.
 */
@Service
@RequiredArgsConstructor
public class TransportationReadServiceImpl implements TransportationReadService {

    private final TransportationRepository transportationRepository;

    private final TransportationEntityToTransportationMapper transportationEntityToTransportationMapper = TransportationEntityToTransportationMapper
            .initialize();

    private final ListTransportationEntityToListTransportationMapper listTransportationEntityToListTransportationMapper = ListTransportationEntityToListTransportationMapper
            .initialize();

    /**
     * Retrieves a Transportation by its unique ID.
     *
     * @param transportationId The ID of the Transportation to retrieve.
     * @return The Transportation object corresponding to the given ID.
     * @throws TransportationNotFoundException If no Transportation with the given
     *                                         ID exists.
     */
    @Override
    public Transportation getTransportationById(String transportationId) {

        final TransportationEntity transportationEntityFromDB = transportationRepository
                .findById(transportationId)
                .orElseThrow(() -> new NotFoundException("With given transportationId = " + transportationId));

        return transportationEntityToTransportationMapper.map(transportationEntityFromDB);
    }

    /**
     * Retrieves all Transportations.
     *
     * @return A list of all Transportations.
     */
    @Override
    public List<Transportation> getAllTransportations() {
        List<TransportationEntity> entities = transportationRepository.findAll();
        return entities.stream()
                .map(transportationEntityToTransportationMapper::map)
                .toList();
    }

    /**
     * Retrieves a page of Transportations based on the paging request criteria.
     *
     * @param transportationPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of Transportations that match the
     *         paging
     *         criteria.
     * @throws TransportationNotFoundException If no Transportations are found based
     *                                         on the paging
     *                                         criteria.
     */
    @Override
    public CustomPage<Transportation> getTransportations(TransportationPagingRequest transportationPagingRequest) {
        final Page<TransportationEntity> transportationEntityPage;
        if (transportationPagingRequest.getSearch() != null && !transportationPagingRequest.getSearch().isEmpty()) {
            transportationEntityPage = transportationRepository
                    .findByTransportationNameContainingIgnoreCase(transportationPagingRequest.getSearch(),
                            transportationPagingRequest.toPageable());
        } else {
            transportationEntityPage = transportationRepository.findAll(transportationPagingRequest.toPageable());
        }

        final List<Transportation> transportationDomainModels = listTransportationEntityToListTransportationMapper
                .toTransportationList(transportationEntityPage.getContent());

        return CustomPage.of(transportationDomainModels, transportationEntityPage);

    }

}
