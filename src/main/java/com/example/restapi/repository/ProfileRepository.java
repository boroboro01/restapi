package com.example.restapi.repository;

import com.example.restapi.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    /**
     * It will find the single user from database by email
     * @param email
     * @return Optional
     */
    Optional<ProfileEntity> findByEmail(String email);
}
