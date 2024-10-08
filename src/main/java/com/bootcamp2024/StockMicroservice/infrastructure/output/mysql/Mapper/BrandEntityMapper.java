package com.bootcamp2024.StockMicroservice.infrastructure.output.mysql.Mapper;


import com.bootcamp2024.StockMicroservice.domain.model.Brand;
import com.bootcamp2024.StockMicroservice.infrastructure.output.mysql.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandEntityMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    BrandEntity brandToBrandEntity(Brand brand);
    Brand brandEntityToBrand(BrandEntity brandEntity);

    List<Brand> toBrandList(List<BrandEntity> brandEntityList);
}
