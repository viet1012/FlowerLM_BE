package com.example.leminhflowerBE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// DTO tạo nhóm mới
public class CreateFlowerGroupRequest {
    private String groupName;
    private String description;
    private String image;
    // getter, setter
}

