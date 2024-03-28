package com.some.pettracker.repository;

import com.some.pettracker.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {
    List<PetEntity> findByOwnerId(int ownerId);
    List<PetEntity> findAllByInZoneFalse();
}