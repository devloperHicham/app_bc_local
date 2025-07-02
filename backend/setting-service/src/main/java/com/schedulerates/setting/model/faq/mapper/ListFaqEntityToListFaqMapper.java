package com.schedulerates.setting.model.faq.mapper;

import com.schedulerates.setting.model.faq.Faq;
import com.schedulerates.setting.model.faq.entity.FaqEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link ListFaqEntityToListFaqMapper} for converting {@link List<FaqEntity>} to {@link List<Faq>}.
 */
@Mapper
public interface ListFaqEntityToListFaqMapper {

    FaqEntityToFaqMapper faqEntityToFaqMapper = Mappers.getMapper(FaqEntityToFaqMapper.class);

    /**
     * Converts a list of FaqEntity objects to a list of Faq objects.
     *
     * @param faqEntities The list of FaqEntity objects to convert.
     * @return List of Faq objects containing mapped data.
     */
    default List<Faq> toFaqList(List<FaqEntity> faqEntities) {

        if (faqEntities == null) {
            return null;
        }

        return faqEntities.stream()
                .map(faqEntityToFaqMapper::map)
                .collect(Collectors.toList());

    }

    /**
     * Initializes and returns an instance of ListFaqEntityToListFaqMapper.
     *
     * @return Initialized ListFaqEntityToListFaqMapper instance.
     */
    static ListFaqEntityToListFaqMapper initialize() {
        return Mappers.getMapper(ListFaqEntityToListFaqMapper.class);
    }

}