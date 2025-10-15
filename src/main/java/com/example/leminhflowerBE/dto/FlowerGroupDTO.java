package com.example.leminhflowerBE.dto;

import java.util.List;
import java.util.stream.Collectors;
import com.example.leminhflowerBE.model.FlowerGroup;
import com.example.leminhflowerBE.model.Flower;
import lombok.Data;

@Data
public class FlowerGroupDTO {
    private Long groupId;
    private String groupName;
    private String description;
    private List<FlowerSummaryDTO> flowers; // ⚡ Danh sách đối tượng DTO

    public static FlowerGroupDTO fromEntity(FlowerGroup group) {
        FlowerGroupDTO dto = new FlowerGroupDTO();
        dto.setGroupId(group.getGroupId());
        dto.setGroupName(group.getGroupName());
        dto.setDescription(group.getDescription());

        if (group.getFlowers() != null) {
            dto.setFlowers(group.getFlowers().stream()
                    .map(f -> new FlowerSummaryDTO(f.getName(), f.getPrice() != null ? f.getPrice() : 0.0))
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
