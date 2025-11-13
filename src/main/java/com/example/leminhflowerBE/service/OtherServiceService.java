package com.example.leminhflowerBE.service;// 📁 package com.example.leminhflowerBE.service

import com.example.leminhflowerBE.dto.OtherServiceDTO;
import com.example.leminhflowerBE.model.OtherService;
import com.example.leminhflowerBE.model.OtherServiceImage;
import com.example.leminhflowerBE.repository.OtherServiceRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OtherServiceService {

    private final OtherServiceRepository repository;

    public OtherServiceService(OtherServiceRepository repository) {
        this.repository = repository;
    }

    // 🔹 Chuyển từ entity sang DTO
    private OtherServiceDTO convertToDTO(OtherService entity) {
        OtherServiceDTO dto = new OtherServiceDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setType(entity.getType());
        if (entity.getImages() != null) {
            dto.setImages(
                    entity.getImages().stream()
                            .map(OtherServiceImage::getImageUrl)
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }

    // 🔹 Tạo mới
    public OtherServiceDTO create(OtherServiceDTO dto) {
        OtherService service = new OtherService();
        service.setTitle(dto.getTitle());
        service.setDescription(dto.getDescription());
        service.setType(dto.getType());

        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            service.setImages(dto.getImages().stream()
                    .map(url -> {
                        OtherServiceImage img = new OtherServiceImage();
                        img.setImageUrl(url);
                        img.setService(service);
                        return img;
                    })
                    .collect(Collectors.toList()));
        }

        return convertToDTO(repository.save(service));
    }

    // 🔹 Lấy tất cả
    public List<OtherServiceDTO> getAll() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Lấy theo ID
    public OtherServiceDTO getById(Long id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // 🔹 Lấy theo TYPE
    public List<OtherServiceDTO> getByType(String type) {
        return repository.findByTypeIgnoreCase(type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Cập nhật
    public OtherServiceDTO update(Long id, OtherServiceDTO dto) {
        Optional<OtherService> optional = repository.findById(id);
        if (optional.isEmpty()) return null;

        OtherService existing = optional.get();
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setType(dto.getType());

        // 🔄 Cập nhật danh sách ảnh (nếu có)
        if (dto.getImages() != null) {
            existing.getImages().clear();
            dto.getImages().forEach(url -> {
                OtherServiceImage img = new OtherServiceImage();
                img.setImageUrl(url);
                img.setService(existing);
                existing.getImages().add(img);
            });
        }

        return convertToDTO(repository.save(existing));
    }

    // 🔹 Xóa theo ID
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
