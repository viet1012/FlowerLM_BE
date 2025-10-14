package com.example.leminhflowerBE.request;


import lombok.Data;
import java.util.List;

@Data
public class FlowerRequest {
    private Long groupId;
    private String name;
    private String lifespan;
    private String origin;
    private String description;
    private String feature;
    private String meaning;
    private List<ImageRequest> images;

    @Data
    public static class ImageRequest {
        private String imageUrl;
        private String imageType;
    }
}
