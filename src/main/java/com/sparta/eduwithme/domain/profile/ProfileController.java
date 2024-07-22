package com.sparta.eduwithme.domain.profile;

import com.sparta.eduwithme.domain.profile.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{userId}")
    public UserProfileDto getUserProfile(@PathVariable Long userId) {
        return profileService.getUserProfile(userId);
    }
}