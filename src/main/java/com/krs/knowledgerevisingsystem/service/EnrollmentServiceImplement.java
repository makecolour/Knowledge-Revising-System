package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.dto.EnrollmentDTO;
import com.krs.knowledgerevisingsystem.entity.Course;
import com.krs.knowledgerevisingsystem.entity.Enrollment;
import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import com.krs.knowledgerevisingsystem.repository.EnrollmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service
public class EnrollmentServiceImplement implements EnrollmentService{

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentServiceImplement(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Transactional
    @Override
    public Enrollment add(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Boolean save(User userId, Course course) throws SaveAccountInvalidException {
        List<Enrollment> userCourseList = enrollmentRepository.findAllByUserId(userId.getId());
        for (Enrollment value : userCourseList) {
            if (Objects.equals(value.getCourse().getId(), course.getId())) {
                throw new SaveAccountInvalidException("Course with id " + course.getId() + " already exists");
            }
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(userId);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);
        return true;
    }

//public Boolean save(User user, Course course) throws SaveAccountInvalidException {
//    List<Enrollment> userCourseList = enrollmentRepository.findAllByUserId(user.getId());
//    for (Enrollment value : userCourseList) {
//        if (Objects.equals(value.getCourse().getId(), course.getId())) {
//            throw new SaveAccountInvalidException("Course with id " + course.getId() + " already exists");
//        }
//    }
//    Enrollment enrollment = new Enrollment();
//    enrollment.setUser(user);
//    enrollment.setCourse(course);
//    enrollmentRepository.save(enrollment);
//    return true;
//}

    @Override
    public List<Enrollment> getAllUserSameCourse(Long courseId) {
        return enrollmentRepository.findAllByCourseId(courseId);
    }

    @Override
    public Boolean checkEnrollment(User user, Course course) {
        if (user != null && course != null) {
            Long userId = user.getId();
            Long courseId = course.getId();
            Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
            return enrollment != null;
        }
        return false;
    }

    @Override
    public void deleteEnrollment(User user, Course course) {
        if (user != null && course != null) {
            Long userId = user.getId();
            Long courseId = course.getId();
            Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
            if (enrollment != null) {
                enrollmentRepository.delete(enrollment);
            }
        }
    }

    @Override
    public List<EnrollmentDTO> findTopCoursesByEnrollments() {
        return enrollmentRepository.findTopCoursesByEnrollments();
    }

    @Override
    public Page<Enrollment> findAllByCourseId(Long id,Pageable pageable) {
        return enrollmentRepository.findAllByCourseId(id, pageable);
    }

    @Override
    public Page<Enrollment> findAll(Pageable pageable) {
        return enrollmentRepository.findAll(pageable);
    }

    @Override
    public Page<Enrollment> findAllByUserId(Pageable pageable, Long id) {
        return enrollmentRepository.findAllByUserId(pageable,id);
    }

    @Override
    public Page<Enrollment> findAllByCourse_CourseNameContainingAndUserIdAndCourse_Status(Pageable pageable, String name, Long id, boolean status){
        return enrollmentRepository.findAllByCourse_CourseNameContainingAndUserIdAndCourse_Status(pageable,name,id, status);
    }

    @Override
    public Page<Enrollment> findAllByUserIdAndCourse_Subject_Name(Pageable pageable, Long id, String name) {
        return enrollmentRepository.findAllByUserIdAndCourse_Subject_Name(pageable,id,name);
    }

    @Override
    public Page<Enrollment> findAllByCourseIdAndUser_FullNameContaining(Long id, String name, Pageable pageable) {
        return enrollmentRepository.findAllByCourseIdAndUser_FullNameContaining(id,name,pageable) ;
    }

    @Override
    public Page<Enrollment> findAllByCourseIdAndUser_FullNameContainingAndUser_Role_Name(Long id, String name, String role, Pageable pageable) {
        return enrollmentRepository.findAllByCourseIdAndUser_FullNameContainingAndUser_Role_Name(id,name,role,pageable);
    }

    @Override
    public Page<Enrollment> findAllByCourseIdAndUser_FullNameContainingAndUser_Status(Long courseId, String name, boolean status2, Pageable pageable) {
        return enrollmentRepository.findAllByCourseIdAndUser_FullNameContainingAndUser_Status(courseId,name,status2,pageable);
    }

    @Override
    public Page<Enrollment> findAllByCourseIdAndUser_FullNameContainingAndUser_Role_NameAndUser_Status(Long courseId, String name, String role, boolean status2, Pageable pageable) {
        return enrollmentRepository.findAllByCourseIdAndUser_FullNameContainingAndUser_Role_NameAndUser_Status(courseId,name,role,status2,pageable);
    }

    @Override
    public Page<Enrollment> findAllByUserIdAndCourse_CourseNameContainingAndCourse_Subject_Id(Pageable pageable, Long id, String name, long subjectId) {
        return enrollmentRepository.findAllByUserIdAndCourse_CourseNameContainingAndCourse_Subject_Id(pageable,id,name,subjectId);
    }

    @Override
    public List<Enrollment> findAllByUserId(Long userId) {
        return enrollmentRepository.findAllByUserId(userId);
    }

    @Override
    public Page<Enrollment> findAllByUserIdAndCourse_Status(Pageable pageable, Long id, boolean status) {
        return enrollmentRepository.findAllByUserIdAndCourse_Status(pageable,id,status);
    }


}
