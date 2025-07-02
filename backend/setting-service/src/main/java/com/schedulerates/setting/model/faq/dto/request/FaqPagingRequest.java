package com.schedulerates.setting.model.faq.dto.request;

import com.schedulerates.setting.model.common.dto.request.CustomPagingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a paging request object for retrieving FAQs as {@link FaqPagingRequest}.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class FaqPagingRequest extends CustomPagingRequest {
    private String search; // this will hold dtParams.search.value
}