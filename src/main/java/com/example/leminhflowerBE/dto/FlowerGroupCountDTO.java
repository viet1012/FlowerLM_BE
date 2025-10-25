package com.example.leminhflowerBE.dto;


import com.example.leminhflowerBE.model.FlowerGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowerGroupCountDTO {
    private Long groupId;
    private String groupName;
    private String description;
    private String image;
    private long flowerCount;

    public static FlowerGroupCountDTO fromEntity(FlowerGroup group) {
        FlowerGroupCountDTO dto = new FlowerGroupCountDTO();
        dto.setGroupId(group.getGroupId());
        dto.setGroupName(group.getGroupName());
        dto.setDescription(group.getDescription());
        dto.setImage(group.getImage());
        dto.setFlowerCount(group.getFlowers() != null ? group.getFlowers().size() : 0);
        return dto;
    }
}
