package com.example.leminhflowerBE.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FlowerPackageRequestDTO {
    private String type;
    private Integer count;
}
