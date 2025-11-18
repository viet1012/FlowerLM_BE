package com.example.leminhflowerBE.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FlowerPackageDTO {
    private Long id;
    private String type;
    private Integer count;
    private List<FlowerDTO> flowers;
}
