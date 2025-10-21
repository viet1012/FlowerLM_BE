package com.example.leminhflowerBE.dto;

import com.example.leminhflowerBE.mapper.FlowerMapper;
import com.example.leminhflowerBE.model.FlowerGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowerGroupDTO {
    private Long groupId;
    private String groupName;
    private String description;
    private List<FlowerSummaryDTO> flowers;

    public static FlowerGroupDTO fromEntity(FlowerGroup group) {
        FlowerGroupDTO dto = new FlowerGroupDTO();
        dto.setGroupId(group.getGroupId());
        dto.setGroupName(group.getGroupName());
        dto.setDescription(group.getDescription());

        if (group.getFlowers() != null) {
            dto.setFlowers(group.getFlowers().stream()
                    .map(FlowerMapper::toSummaryDTO)
                    .collect(Collectors.toList()));

        }

        return dto;
    }


}
