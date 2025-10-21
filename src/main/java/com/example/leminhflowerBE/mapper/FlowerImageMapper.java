package com.example.leminhflowerBE.mapper;


import com.example.leminhflowerBE.dto.FlowerImageDTO;
import com.example.leminhflowerBE.model.Flower;
import com.example.leminhflowerBE.model.FlowerImage;

public class FlowerImageMapper {

    public static FlowerImageDTO toDTO(FlowerImage entity) {
        return new FlowerImageDTO(
                entity.getImageId(),
                entity.getImageUrl(),
                entity.getImageType() != null ? entity.getImageType().name() : null,
                entity.getFlower().getFlowerId()
        );
    }

    public static FlowerImage toEntity(FlowerImageDTO dto, Flower flower) {
        FlowerImage entity = new FlowerImage();
        entity.setImageId(dto.getImageId());
        entity.setImageUrl(dto.getImageUrl());
        entity.setFlower(flower);
        entity.setImageType(
                dto.getImageType() != null
                        ? FlowerImage.ImageType.valueOf(dto.getImageType())
                        : null
        );
        return entity;
    }
}
