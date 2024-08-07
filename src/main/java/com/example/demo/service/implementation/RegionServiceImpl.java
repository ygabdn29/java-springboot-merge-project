package com.example.demo.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Region;
import com.example.demo.repository.RegionRepository;
import com.example.demo.service.RegionService;

@Service
public class RegionServiceImpl implements RegionService{
  @Autowired
  private RegionRepository regionRepository;

  @Override
  public List<Region> get() {
    return regionRepository.findAll();
  }

  @Override
  public Region get(Integer id) {
    return regionRepository.findById(id).orElse(null);
  }

  @Override
  public Boolean save(Region entity) {
    regionRepository.save(entity);
    return regionRepository.findById(entity.getId()).isPresent();
  }

  @Override
  public Boolean delete(Integer id) {
    regionRepository.deleteById(id);
    return regionRepository.findById(id).isEmpty();
  }
}
