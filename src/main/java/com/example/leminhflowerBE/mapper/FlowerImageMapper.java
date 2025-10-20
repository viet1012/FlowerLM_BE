package com.example.leminhflowerBE.mapper;


import com.example.leminhflowerBE.dto.FlowerImageDTO;
import com.example.leminhflowerBE.model.FlowerImage;

public class FlowerImageMapper {

    public static FlowerImageDTO toDTO(FlowerImage image) {
        FlowerImageDTO dto = new FlowerImageDTO();
        dto.setImageId(image.getImageId());
        dto.setImageUrl(image.getImageUrl());
        dto.setImageType(image.getImageType().name());
        dto.setFlowerId(image.getFlower() != null ? image.getFlower().getFlowerId() : null);
        return dto;
    }
}
