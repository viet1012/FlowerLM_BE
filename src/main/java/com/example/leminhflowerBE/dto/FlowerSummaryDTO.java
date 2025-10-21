package com.example.leminhflowerBE.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FlowerSummaryDTO {
    private String name;
    private double price;
    private String groupName;
    private List<FlowerImageDTO> images;
}
