package ru.vsu.cs.socialservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.socialservice.dto.ActionDTO;
import ru.vsu.cs.socialservice.dto.UserShortDTO;
import ru.vsu.cs.socialservice.entity.enums.Relation;
import ru.vsu.cs.socialservice.service.RelationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/social")
@RequiredArgsConstructor
public class RelationController {

    private final RelationService relationService;

    @GetMapping("/friends/random/{id}")
    public ResponseEntity<?> getRandomFriend(@PathVariable UUID id,
                                             @RequestHeader("Authorization") String token) {
        List<UserShortDTO> randomFriends = relationService.getRandomFriends(id, token);
        return ResponseEntity.ok(randomFriends);
    }

    @GetMapping("/{relation}/{id}")
    public ResponseEntity<?> getUsers(@PathVariable UUID id,
                                      @PathVariable Relation relation,
                                      @RequestHeader("Authorization") String token) {
        List<UserShortDTO> users = relationService.getUsers(id, relation, token);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/count/{relation}/{id}")
    public ResponseEntity<?> getCountRelations(@PathVariable UUID id,
                                               @PathVariable Relation relation,
                                               @RequestHeader("Authorization") String token) {
        Integer count = relationService.getCountUsers(id, relation, token);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/{action}")
    public ResponseEntity<?> relationAction(@RequestBody ActionDTO actionDTO,
                                            @RequestHeader("Authorization") String token){
        relationService.action(actionDTO, token);
        return ResponseEntity.ok("The action is completely successful");
    }
}
