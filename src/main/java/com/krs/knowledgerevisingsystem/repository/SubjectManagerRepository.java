package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.entity.Subject;
import com.krs.knowledgerevisingsystem.entity.SubjectManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectManagerRepository extends JpaRepository<SubjectManager, Long> {
    Page<SubjectManager> findAll(Pageable pageable);

    SubjectManager findBySubject_Id(Long id);

    Page<SubjectManager> findAllBySubject_NameContainingOrSubject_CodeContaining(String name, String code, Pageable pageable);

    @Query ("select s from SubjectManager s where s.subject.status = :status and (s.subject.name like %:name% or s.subject.code like %:code%)")
    Page<SubjectManager> findAllBySubject_StatusAndSubject_NameContainingOrSubject_CodeContaining(boolean status, @Param("name") String name, @Param("code") String code, Pageable pageable);

    @Query ("select s from SubjectManager s where s.subject.status = :status and s.subject.role.id = :speciality and (s.subject.name like %:name% or s.subject.code like %:code%)")
    Page<SubjectManager> findAllBySubject_StatusAndSubject_Role_IdAndSubject_NameContainingOrSubject_CodeContaining(boolean status, Long speciality, @Param("name") String name, @Param("code") String code, Pageable pageable);

    @Query ("select s from SubjectManager s where s.subject.role.id = :speciality and (s.subject.name like %:name% or s.subject.code like %:code%)")
    Page<SubjectManager> findAllBySubject_Role_IdAndSubject_NameContainingOrSubject_CodeContaining(Long speciality, @Param("name") String name, @Param("code") String code, Pageable pageable);


    @Query("select s from SubjectManager s where s.subject.status = :status and (s.subject.name like %:name% or s.subject.code like %:code%) and s.user.id = :id")
    Page<SubjectManager> findAllBySubject_StatusAndUser_IdAndSubject_NameContainingOrSubject_CodeContaining(@Param("status") boolean status, @Param("id") Long id, @Param("name") String name, @Param("code") String code, Pageable pageable);

    @Query("select s from SubjectManager s where s.subject.status = :status and s.subject.role.id = :speciality and (s.subject.name like %:name% or s.subject.code like %:code%) and s.user.id = :id")
    Page<SubjectManager> findAllBySubject_StatusAndUser_IdAndSubject_Role_IdAndSubject_NameContainingOrSubject_CodeContaining(@Param("status") boolean status, @Param("id") Long id, @Param("speciality") Long speciality, @Param("name") String name, @Param("code") String code, Pageable pageable);

    @Query("select s from SubjectManager s where s.subject.role.id = :speciality and s.user.id = :id and (s.subject.name like %:name% or s.subject.code like %:code%)")
    Page<SubjectManager> findAllBySubject_Role_IdAndUser_IdAndSubject_NameContainingOrSubject_CodeContaining(@Param("speciality") Long speciality, @Param("id") Long id, @Param("name") String name, @Param("code") String code, Pageable pageable);

    Page<SubjectManager> findAllByUser_Id(Long id, Pageable pageable);

    Page<SubjectManager> findAllByUser_IdAndSubject_NameContainingOrSubject_CodeContaining(Long id, String name, String code, Pageable pageable);


}
