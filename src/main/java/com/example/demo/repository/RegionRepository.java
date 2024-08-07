package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Region;

@Repository

public interface RegionRepository extends JpaRepository <Region, Integer>{
}
