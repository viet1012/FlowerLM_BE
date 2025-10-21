package com.example.leminhflowerBE.service;

import com.example.leminhflowerBE.model.FlowerFeature;
import com.example.leminhflowerBE.repository.FlowerFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlowerFeatureService {

    @Autowired
    private FlowerFeatureRepository repository;
    private static final int MAX_FEATURES = 6;

    public List<FlowerFeature> getAllFeatures() {
        return repository.findAll();
    }

    public Optional<FlowerFeature> getFeatureById(Long id) {
        return repository.findById(id);
    }



    public List<FlowerFeature> createFeatures(List<FlowerFeature> newFeatures) {
        long currentCount = repository.count();
        int spaceLeft = (int) (MAX_FEATURES - currentCount);

        if (spaceLeft <= 0) {
            throw new RuntimeException("❌ Đã đạt giới hạn tối đa 6 feature items.");
        }

        if (newFeatures.size() > spaceLeft) {
            // chỉ lấy đủ số lượng cho phép
            newFeatures = newFeatures.subList(0, spaceLeft);
        }

        List<FlowerFeature> saved = new ArrayList<>();
        for (FlowerFeature feature : newFeatures) {
            saved.add(repository.save(feature));
        }

        return saved;
    }

    public FlowerFeature updateFeature(Long id, FlowerFeature newData) {
        return repository.findById(id).map(feature -> {
            feature.setTitle(newData.getTitle());
            feature.setDescription(newData.getDescription());
            feature.setIconUrl(newData.getIconUrl());
            feature.setColorTheme(newData.getColorTheme());
            feature.setOrderIndex(newData.getOrderIndex());
            feature.setActive(newData.getActive());
            return repository.save(feature);
        }).orElseThrow(() -> new RuntimeException("Feature not found with id " + id));
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void deleteFeature(Long id) {
        repository.deleteById(id);
    }
}
