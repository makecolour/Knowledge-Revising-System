package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.dto.SubjectDTO;
import com.krs.knowledgerevisingsystem.entity.Course;
import com.krs.knowledgerevisingsystem.entity.Subject;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @NotNull
    List<Subject> findAll();

    @Override
    Optional<Subject> findById(Long id);

    @Query("SELECT s FROM Subject s WHERE s.id = :id")
    Subject findSubjectById(@Param("id") Long id);

    List<Subject> findByNameContainingOrCodeContaining(String name, String code);

    Page<Subject> findByNameContainingOrCodeContaining(String name, String code, Pageable pageable);

    Page<Subject> findAll(Pageable pageable);

//    Page<SubjectDTO> findSubjectByAll(Pageable pageable);

    @Query("SELECT s FROM Subject s WHERE s.status = :status AND s.name LIKE %:name% OR s.code LIKE %:code%")
    Page<Subject> findByStatusAndNameContainingOrCodeContaining(@Param("status") boolean status, @Param("name") String name, @Param("code") String code, Pageable pageable);

    @Query("SELECT e FROM Subject e WHERE " +
            "(?1 IS NULL OR e.status = ?1) AND " +
            "(?2 IS NULL OR e.role.id = ?2) AND " +
            "LOWER(e.name) LIKE LOWER(CONCAT('%', ?3, '%')) AND " +
            "LOWER(e.code) LIKE LOWER(CONCAT('%', ?4, '%'))")
    Page<Subject> findByStatusAndRole_IdAndNameContainingOrCodeContaining(Boolean status, Long speciality, String name, String code, Pageable pageable);

    Page<Subject> findAllByRole_IdAndNameContainingOrCodeContaining(Long speciality, String name, String code, Pageable pageable);

    List<Subject> findAllByStatus(boolean status);

    Subject findById(long id);


    @Query("SELECT e FROM Subject e LEFT JOIN e.subjectManager m WHERE " +
            "(?1 IS NULL OR e.status = ?1) AND " +
            "(?2 IS NULL OR e.role.id = ?2) AND " +
            "(?3 IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', ?3, '%'))) AND " +
            "(?4 IS NULL OR LOWER(e.code) LIKE LOWER(CONCAT('%', ?4, '%'))) AND " +
            "(?5 IS NULL OR m.user.id = ?5)")
    Page<Subject> findByStatusAndRole_IdAndNameContainingOrCodeContainingAndSubjectManagerContaining(Boolean status, Long speciality, String name, String code, Long manager, Pageable pageable);

}
