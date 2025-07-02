package com.schedulerates.setting.model.faq.mapper;

import com.schedulerates.setting.model.common.CustomPage;
import com.schedulerates.setting.model.common.dto.response.CustomPagingResponse;
import com.schedulerates.setting.model.faq.Faq;
import com.schedulerates.setting.model.faq.dto.response.FaqResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link CustomPageToCustomPagingResponseMapper} for converting {@link CustomPage<Faq>} to {@link CustomPagingResponse<FaqResponse>}.
 */

@Mapper
public interface CustomPageToCustomPagingResponseMapper {

    FaqToFaqResponseMapper faqToFaqResponseMapper = Mappers.getMapper(FaqToFaqResponseMapper.class);

    /**
     * Converts a CustomPage<Faq> object to CustomPagingResponse<FaqResponse>.
     *
     * @param faqPage The CustomPage<Faq> object to convert.
     * @return CustomPagingResponse<FaqResponse> object containing mapped data.
     */
    default CustomPagingResponse<FaqResponse> toPagingResponse(CustomPage<Faq> faqPage) {

        if (faqPage == null) {
            return null;
        }

        return CustomPagingResponse.<FaqResponse>builder()
                .content(toFaqResponseList(faqPage.getContent()))
                .totalElementCount(faqPage.getTotalElementCount())
                .totalPageCount(faqPage.getTotalPageCount())
                .pageNumber(faqPage.getPageNumber())
                .pageSize(faqPage.getPageSize())
                .build();

    }

    /**
     * Converts a list of Faq objects to a list of FaqResponse objects.
     *
     * @param faqs The list of Faq objects to convert.
     * @return List of FaqResponse objects containing mapped data.
     */
    default List<FaqResponse> toFaqResponseList(List<Faq> faqs) {

        if (faqs == null) {
            return null;
        }

        return faqs.stream()
                .map(faqToFaqResponseMapper::map)
                .collect(Collectors.toList());

    }

    /**
     * Initializes and returns an instance of CustomPageToCustomPagingResponseMapper.
     *
     * @return Initialized CustomPageToCustomPagingResponseMapper instance.
     */
    static CustomPageToCustomPagingResponseMapper initialize() {
        return Mappers.getMapper(CustomPageToCustomPagingResponseMapper.class);
    }

}