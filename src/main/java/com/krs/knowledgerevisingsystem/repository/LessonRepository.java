package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.dto.LessonsDto;
import com.krs.knowledgerevisingsystem.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findALLBySubject_Id(Long id);

    @Query("SELECT NEW com.krs.knowledgerevisingsystem.dto.LessonsDto(s.id,s.name)  FROM Lesson s WHERE s.subject.id= :id AND  (:name IS NULL OR :name = '' OR s.name LIKE %:name%)")
    Page<LessonsDto> findLessonsByAll(Pageable pageable, @Param("id") String id, @Param("name") String name);

    Lesson findLessonById(Long id);

    Lesson findLessonBySubject_Id(Long id);

    Page<Lesson> findAllBySubject_Id(Long id, Pageable pageable);

    Page<Lesson> findAllByNameContaining(String name, Pageable pageable);

    @Query("SELECT s FROM Lesson s")
    Page<Lesson> findAllLessons(Pageable pageable);

    @Query("SELECT s FROM Lesson s WHERE s.name LIKE %:name% AND s.status = :status")
    Page<Lesson> findAllByNameContainingAndStatus(Pageable pageable, String name, boolean status);

    @Query("SELECT s FROM Lesson s WHERE s.name LIKE %:name% AND s.status = :status AND s.subject.id = :subjectId")
    Page<Lesson> findAllByNameContainingAndStatusAndSubjectId(Pageable pageable, String name, boolean status, Long subjectId);

    @Query("SELECT s FROM Lesson s WHERE s.name LIKE %:name% AND s.subject.id = :subjectId")
    Page<Lesson> findAllByNameContainingAndSubjectId(Pageable pageable, String name, Long subjectId);
}
