package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT s FROM Question s WHERE s.lesson.id= :id AND  (:name IS NULL OR :name = '' OR s.content LIKE %:name%)")
    Page<Question> findQuestionByAll(Pageable pageable, @Param("id") String id , @Param("name") String name);
    Question findQuestionByContent(String content);
    Question findQuestionById(Long id);
    List<Question> findAllByLessonId(Long lessonId);
    List<Question> findAllByLesson(Lesson lesson);
    List<Question> findAllByStatus(boolean status);
    Question findQuestionByContentAndLesson(String content, Lesson lesson);

}
