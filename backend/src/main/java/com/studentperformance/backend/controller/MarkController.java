package com.studentperformance.backend.controller;

import com.studentperformance.backend.entity.Mark;
import com.studentperformance.backend.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marks")
@CrossOrigin(origins = "*")
public class MarkController {

    @Autowired
    private MarkService markService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Mark>> getMarksByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(markService.getMarksByStudentId(studentId));
    }

    @PostMapping
    public ResponseEntity<Mark> createMark(@RequestBody Mark mark) {
        return ResponseEntity.ok(markService.saveMark(mark));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMark(@PathVariable Long id) {
        markService.deleteMark(id);
        return ResponseEntity.noContent().build();
    }
}
