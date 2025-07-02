package com.schedulerates.setting.service.company.impl;

import com.schedulerates.setting.exception.NotFoundException;
import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.company.Company;
import com.schedulerates.setting.model.company.dto.request.CompanyPagingRequest;
import com.schedulerates.setting.model.company.entity.CompanyEntity;
import com.schedulerates.setting.model.company.mapper.ListCompanyEntityToListCompanyMapper;
import com.schedulerates.setting.model.company.mapper.CompanyEntityToCompanyMapper;
import com.schedulerates.setting.repository.CompanyRepository;
import com.schedulerates.setting.service.company.CompanyReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation named {@link CompanyReadServiceImpl} for reading
 * companies.
 */
@Service
@RequiredArgsConstructor
public class CompanyReadServiceImpl implements CompanyReadService {

    private final CompanyRepository companyRepository;

    private final CompanyEntityToCompanyMapper companyEntityToCompanyMapper = CompanyEntityToCompanyMapper.initialize();

    private final ListCompanyEntityToListCompanyMapper listCompanyEntityToListCompanyMapper = ListCompanyEntityToListCompanyMapper
            .initialize();

    /**
     * Retrieves a company by its unique ID.
     *
     * @param companyId The ID of the company to retrieve.
     * @return The Company object corresponding to the given ID.
     * @throws CompanyNotFoundException If no company with the given ID exists.
     */
    @Override
    public Company getCompanyById(String companyId) {

        final CompanyEntity companyEntityFromDB = companyRepository
                .findById(companyId)
                .orElseThrow(() -> new NotFoundException("With given companyID = " + companyId));

        return companyEntityToCompanyMapper.map(companyEntityFromDB);
    }

    /**
     * Retrieves all companies.
     *
     * @return A list of all companies.
     */
    @Override
    public List<Company> getAllCompanies() {
        List<CompanyEntity> entities = companyRepository.findAll();
        return entities.stream()
            .map(companyEntityToCompanyMapper::map)
            .toList();
    }
    /**
     * Retrieves a page of companies based on the paging request criteria.
     *
     * @param companyPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of companies that match the paging
     *         criteria.
     * @throws CompanyNotFoundException If no companies are found based on the
     *                                  paging criteria.
     */
    @Override
    public CustomPage<Company> getCompanies(CompanyPagingRequest companyPagingRequest) {
        final Page<CompanyEntity> faqEntityPage;
        if (companyPagingRequest.getSearch() != null && !companyPagingRequest.getSearch().isEmpty()) {
            faqEntityPage = companyRepository
                    .findByCompanyNameContainingIgnoreCase(companyPagingRequest.getSearch(),
                            companyPagingRequest.toPageable());
        } else {
            faqEntityPage = companyRepository.findAll(companyPagingRequest.toPageable());
        }

        final List<Company> faqDomainModels = listCompanyEntityToListCompanyMapper
                .toCompanyList(faqEntityPage.getContent());

        return CustomPage.of(faqDomainModels, faqEntityPage);

    }

}