package com.efitops.basaesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basaesetup.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}

