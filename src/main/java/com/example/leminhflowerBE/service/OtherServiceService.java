package com.example.leminhflowerBE.service;

import com.example.leminhflowerBE.model.OtherService;
import com.example.leminhflowerBE.repository.OtherServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OtherServiceService {

    private final OtherServiceRepository repository;

    public OtherServiceService(OtherServiceRepository repository) {
        this.repository = repository;
    }

    // 🔹 Lấy tất cả dịch vụ
    public List<OtherService> getAll() {
        return repository.findAll();
    }

    // 🔹 Tìm theo ID
    public Optional<OtherService> getById(Long id) {
        return repository.findById(id);
    }

    // 🔹 Tìm theo loại (type)
    public List<OtherService> getByType(String type) {
        return repository.findByTypeIgnoreCase(type);
    }

    // 🔹 Tìm theo tiêu đề chính xác
    public List<OtherService> getByTitle(String title) {
        return repository.findByTitleIgnoreCase(title);
    }

    // 🔹 Tìm theo tiêu đề gần đúng (LIKE)
    public List<OtherService> searchByTitle(String keyword) {
        return repository.findByTitleContainingIgnoreCase(keyword);
    }

    // 🔹 Tìm theo mô tả chứa từ khóa
    public List<OtherService> searchByDescription(String keyword) {
        return repository.findByDescriptionContainingIgnoreCase(keyword);
    }

    // 🟢 Thêm 1 dịch vụ
    public OtherService create(OtherService service) {
        return repository.save(service);
    }

    // 🟢 Thêm nhiều dịch vụ
    public List<OtherService> createBatch(List<OtherService> services) {
        return repository.saveAll(services);
    }

    // 🟠 Cập nhật 1 dịch vụ
    public OtherService update(Long id, OtherService updated) {
        Optional<OtherService> optionalExisting = repository.findById(id);
        if (optionalExisting.isEmpty()) return null;

        OtherService existing = optionalExisting.get();
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setType(updated.getType());
        existing.setImages(updated.getImages());

        return repository.save(existing);
    }

    // 🟠 Cập nhật nhiều dịch vụ
    public List<OtherService> updateBatch(List<OtherService> services) {
        return repository.saveAll(services);
    }

    // 🔴 Xóa 1 dịch vụ
    public boolean delete(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    // 🔴 Xóa nhiều dịch vụ
    public void deleteBatch(List<Long> ids) {
        ids.forEach(repository::deleteById);
    }

    // 🔴 Xóa tất cả
    public void deleteAll() {
        repository.deleteAll();
    }
}
