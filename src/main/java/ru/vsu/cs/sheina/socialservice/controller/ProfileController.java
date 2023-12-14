package ru.vsu.cs.sheina.socialservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.sheina.socialservice.dto.UpdateUserDataDTO;
import ru.vsu.cs.sheina.socialservice.dto.fields.NameDTO;
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

    @PostMapping("/data")
    public ResponseEntity<?> setUserData(@RequestBody UpdateUserDataDTO updateUserDataDTO,
                                         @RequestHeader("Authorization") String token) {
        userDataService.setUserData(updateUserDataDTO, token);
        return ResponseEntity.ok("Data updated successfully");
    }

    @PostMapping("/name/{id}")
    public ResponseEntity<?> setUserName(@RequestBody NameDTO nameDTO,
                                         @PathVariable UUID id) {
        userDataService.setUserName(id, nameDTO);
        return ResponseEntity.ok("User's name updated successfully");
    }
}
