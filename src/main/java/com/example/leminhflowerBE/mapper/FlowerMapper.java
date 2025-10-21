package com.example.leminhflowerBE.mapper;

import com.example.leminhflowerBE.dto.FlowerDTO;
import com.example.leminhflowerBE.dto.FlowerImageDTO;
import com.example.leminhflowerBE.dto.FlowerSummaryDTO;
import com.example.leminhflowerBE.model.Flower;

import java.util.stream.Collectors;
import java.util.List;

public class FlowerMapper {

    // ✅ Dùng cho chi tiết đầy đủ
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
                            .map(img -> new FlowerImageDTO(
                                    img.getImageId(),
                                    img.getImageUrl(),
                                    img.getImageType() != null ? img.getImageType().name() : null,
                                    img.getFlower().getFlowerId()
                            ))
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }

    // ✅ Dùng cho bản tóm tắt (summary)
    public static FlowerSummaryDTO toSummaryDTO(Flower flower) {
        List<FlowerImageDTO> imageDTOs = flower.getImages() != null
                ? flower.getImages().stream()
                .map(img -> new FlowerImageDTO(
                        img.getImageId(),
                        img.getImageUrl(),
                        img.getImageType() != null ? img.getImageType().name() : null,
                        img.getFlower().getFlowerId()
                ))
                .collect(Collectors.toList())
                : List.of();

        return new FlowerSummaryDTO(
                flower.getName(),
                flower.getPrice() != null ? flower.getPrice() : 0.0,
                flower.getGroup().getGroupName(),
                imageDTOs
        );
    }
}
