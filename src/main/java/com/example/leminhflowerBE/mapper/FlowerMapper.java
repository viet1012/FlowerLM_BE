package com.example.leminhflowerBE.mapper;

import com.example.leminhflowerBE.dto.FlowerDTO;
import com.example.leminhflowerBE.dto.FlowerImageDTO;
import com.example.leminhflowerBE.model.Flower;
import java.util.stream.Collectors;

public class FlowerMapper {

    public static FlowerDTO toDTO(Flower flower) {
        FlowerDTO dto = new FlowerDTO();
        dto.setFlowerId(flower.getFlowerId());
        dto.setName(flower.getName());
        dto.setLifespan(flower.getLifespan());
        dto.setOrigin(flower.getOrigin());
        dto.setDescription(flower.getDescription());
        dto.setFeature(flower.getFeature());
        dto.setMeaning(flower.getMeaning());
        dto.setGroupName(flower.getGroup() != null ? flower.getGroup().getGroupName() : null);

        if (flower.getImages() != null) {
            dto.setImages(
                    flower.getImages().stream()
                            .map(img -> new FlowerImageDTO(img.getImageId(), img.getImageUrl(), img.getImageType().name()))
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }
}
