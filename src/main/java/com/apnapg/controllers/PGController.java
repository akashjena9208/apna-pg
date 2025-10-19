package com.apnapg.controllers;

import com.apnapg.dto.PGResponse;
import com.apnapg.entity.PG;
import com.apnapg.mapper.PGMapper;
import com.apnapg.service.PGService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pgs")
@RequiredArgsConstructor
public class PGController {

    private final PGService pgService;

//    @GetMapping("/search")
//    public ResponseEntity<List<PGResponse>> searchPGs(
//            @RequestParam String city,
//            @RequestParam int rentMax
//    ) {
//        List<PGResponse> result = pgService.searchPGs(city, rentMax)
//                .stream()
//                .map(PGMapper::toResponse)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(result);
//    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPGs(
            @RequestParam String city,
            @RequestParam int rentMax
    ) {
        List<PGResponse> result = pgService.searchPGs(city, rentMax)
                .stream()
                .map(PGMapper::toResponse)
                .toList();

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No PGs found for city: " + city + " within rent: " + rentMax);
        }

        return ResponseEntity.ok(result);
    }
}
