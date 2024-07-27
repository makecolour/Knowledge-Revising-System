package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.dto.LessonsDto;
import com.krs.knowledgerevisingsystem.entity.Role;
import com.krs.knowledgerevisingsystem.entity.StudySet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudySetRepository extends JpaRepository<StudySet, Long> {
    @Query("SELECT s FROM StudySet s WHERE s.lesson.id= :id AND  (:name IS NULL OR :name = '' OR s.term LIKE %:name%)")
    Page<StudySet> findStudySetByAll(Pageable pageable, @Param("id") String id , @Param("name") String name);
    Page<StudySet> findAllByLessonId(Long id, Pageable pageable);
    List<StudySet> findByLessonId(Long lessonId);

}
