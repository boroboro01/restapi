package com.example.restapi.service;

import com.example.restapi.dto.ProfileDTO;

public interface ProfileService {
    /**
     * It will save the user details to database
     * @param profileDTO (profile dto)
     * @return profileDTO
     */
    ProfileDTO createProfile(ProfileDTO profileDTO);
}
