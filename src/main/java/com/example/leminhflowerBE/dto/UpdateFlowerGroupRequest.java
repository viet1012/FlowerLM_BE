package com.example.leminhflowerBE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// DTO cập nhật nhóm
public class UpdateFlowerGroupRequest {
    private String groupName;
    private String description;
    private String image;
    // getter, setter
}
