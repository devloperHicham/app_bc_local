package com.schedulerates.setting.model.gargo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link ListFaqEntityToListFaqMapper} for converting {@link List<FaqEntity>} to {@link List<Faq>}.
 */
@Mapper
public interface ListGargoEntityToListGargoMapper {

    GargoEntityToGargoMapper gargoEntityToGargoMapper = Mappers.getMapper(GargoEntityToGargoMapper.class);

    /**
     * Converts a list of GargoEntity objects to a list of Gargo objects.
     *
     * @param gargoEntities The list of GargoEntity objects to convert.
     * @return List of Gargo objects containing mapped data.
     */
    default List<com.schedulerates.setting.model.gargo.Gargo> toGargoList(List<com.schedulerates.setting.model.gargo.entity.GargoEntity> gargoEntities) {

        if (gargoEntities == null) {
            return null;
        }

        return gargoEntities.stream()
                .map(gargoEntityToGargoMapper::map)
                .collect(Collectors.toList());

    }

    /**
     * Initializes and returns an instance of ListGargoEntityToListGargoMapper.
     *
     * @return Initialized ListGargoEntityToListGargoMapper instance.
     */
    static ListGargoEntityToListGargoMapper initialize() {
        return Mappers.getMapper(ListGargoEntityToListGargoMapper.class);
    }

}
