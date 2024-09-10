package com.example.restapi.controller;

import com.example.restapi.dto.ProfileDTO;
import com.example.restapi.io.AuthRequest;
import com.example.restapi.io.AuthResponse;
import com.example.restapi.io.ProfileRequest;
import com.example.restapi.io.ProfileResponse;
import com.example.restapi.service.CustomUserDetailsService;
import com.example.restapi.service.ProfileService;
import com.example.restapi.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final ModelMapper modelMapper;

    private final ProfileService profileService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final CustomUserDetailsService userDetailsService;

    /**
     * API endpoint to register new user
     * @param profileRequest (profile request)
     * @return profileResponse (profile response)
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ProfileResponse createProfile(@Valid @RequestBody ProfileRequest profileRequest) {
        log.info("API /register is called {}", profileRequest);
        ProfileDTO profileDTO = mapToProfileDTO(profileRequest);
        profileDTO = profileService.createProfile(profileDTO);
        log.info("Printing the profile dto details {}", profileDTO);
        return mapToProfileResponse(profileDTO);
    }

    /**
     * API endpoint to login user
     * @param authRequest (auth request)
     * @return profileResponse (profile response)
     */
    @PostMapping("/login")
    public AuthResponse authenticateProfile(@RequestBody AuthRequest authRequest) throws Exception {
        log.info("API /login is called {}", authRequest);
        authenticate(authRequest);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new AuthResponse(token, authRequest.getEmail());
    }

    private void authenticate(AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("Profile disable", e);
        } catch (BadCredentialsException e) {

        }
    }

    /**
     * Mapper method to map values from profile request to profile dto
     * @param profileRequest (profile request)
     * @return profileDTO
     */
    private ProfileDTO mapToProfileDTO(ProfileRequest profileRequest) {
        return modelMapper.map(profileRequest, ProfileDTO.class);
    }

    /**
     * Mapper method to map values from profile dto to profile response
     * @param profileDTO (profile dto)
     * @return profileResponse
     */
    private ProfileResponse mapToProfileResponse(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, ProfileResponse.class);
    }
}
