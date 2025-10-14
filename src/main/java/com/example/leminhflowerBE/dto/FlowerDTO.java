package com.example.leminhflowerBE.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowerDTO {
    private Long flowerId;
    private String name;
    private String lifespan;
    private String origin;
    private String description;
    private String feature;
    private String meaning;
    private String groupName; // thay vì trả toàn bộ group object
    private List<FlowerImageDTO> images;
}
