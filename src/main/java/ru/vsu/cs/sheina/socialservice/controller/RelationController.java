package ru.vsu.cs.sheina.socialservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.sheina.socialservice.dto.ActionDTO;
import ru.vsu.cs.sheina.socialservice.dto.UserShortDTO;
import ru.vsu.cs.sheina.socialservice.service.RelationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/social")
@RequiredArgsConstructor
public class RelationController {

    private final RelationService relationService;

    @GetMapping("/friends/random/{id}")
    public ResponseEntity<?> getRandomFriend(@PathVariable UUID id) {
        List<UserShortDTO> randomFriends = relationService.getRandomFriends(id);
        return ResponseEntity.ok(randomFriends);
    }

    @GetMapping("/relation/{relation}/{id}")
    public ResponseEntity<?> getUsers(@PathVariable UUID id,
                                      @PathVariable String relation) {
        List<UserShortDTO> users = relationService.getUsers(id, relation);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/count/{relation}/{id}")
    public ResponseEntity<?> getCountRelations(@PathVariable UUID id,
                                               @PathVariable String relation) {
        Integer count = relationService.getCountUsers(id, relation);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/action")
    public ResponseEntity<?> relationAction(@RequestBody ActionDTO actionDTO,
                                            @RequestHeader("Authorization") String token){
        relationService.action(actionDTO, token);
        return ResponseEntity.ok("The action is completely successful");
    }
}
