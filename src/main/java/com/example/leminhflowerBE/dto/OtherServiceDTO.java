package com.example.leminhflowerBE.dto;


import lombok.Data;
import java.util.List;

@Data
public class OtherServiceDTO {
    private Long id;
    private String title;
    private String description;
    private String type;
    private List<String> images;
}
