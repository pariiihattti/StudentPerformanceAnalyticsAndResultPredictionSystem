package com.studentperformance.backend.service;

import com.studentperformance.backend.entity.Mark;
import com.studentperformance.backend.repository.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkService {

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private AnalysisService analysisService;

    public List<Mark> getMarksByStudentId(Long studentId) {
        return markRepository.findByStudentId(studentId);
    }

    public Mark saveMark(Mark mark) {
        if (mark.getInternalMarks() > 40) {
            mark.setInternalMarks(40);
        }
        if (mark.getExternalMarks() > 60) {
            mark.setExternalMarks(60);
        }
        double total = mark.getInternalMarks() + mark.getExternalMarks();
        mark.setTotalMarks(total);
        mark.setGrade(calculateGrade(total));
        Mark savedMark = markRepository.save(mark);
        analysisService.generateAnalysisForStudent(mark.getStudentId());
        return savedMark;
    }

    public void deleteMark(Long id) {
        Mark mark = markRepository.findById(id).orElse(null);
        if (mark != null) {
            Long studentId = mark.getStudentId();
            markRepository.deleteById(id);
            analysisService.generateAnalysisForStudent(studentId);
        }
    }

    public static String calculateGrade(double score) {
        if (score >= 90) return "A+";
        if (score >= 80) return "A";
        if (score >= 70) return "B";
        if (score >= 60) return "C";
        if (score >= 50) return "D";
        return "F";
    }
}
