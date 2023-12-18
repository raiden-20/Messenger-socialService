package ru.vsu.cs.sheina.socialservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.sheina.socialservice.dto.UpdateUserDTO;
import ru.vsu.cs.sheina.socialservice.dto.UserRegistrationDTO;
import ru.vsu.cs.sheina.socialservice.service.UserDataService;

import java.util.UUID;

@RestController
@RequestMapping("/social")
@RequiredArgsConstructor
public class ProfileController {

    private final UserDataService userDataService;

    @GetMapping("/data/{id}")
    public ResponseEntity<?> getUserData(@PathVariable UUID id,
                                         @RequestHeader("Authorization") String token) {
       return ResponseEntity.ok(userDataService.getUserData(id, token));
    }

    @PostMapping(path = "/data")
    public ResponseEntity<?> setUserData(@RequestPart("avatar") MultipartFile avatar,
                                         @RequestPart("cover") MultipartFile cover,
                                         @RequestPart("updateUserDTO") UpdateUserDTO updateUserDTO,
                                         @RequestHeader("Authorization") String token) {
        userDataService.setUserData(updateUserDTO, avatar, cover, token);
        return ResponseEntity.ok("Data updated successfully");
    }

    @PostMapping("/registration")
    public ResponseEntity<?> setUserName(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        userDataService.register(userRegistrationDTO);
        return ResponseEntity.ok("User successfully registered");
    }
}
