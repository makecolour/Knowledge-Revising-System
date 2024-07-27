package com.krs.knowledgerevisingsystem.dto;

import com.krs.knowledgerevisingsystem.entity.Course;
import com.krs.knowledgerevisingsystem.entity.User;

public interface EnrollmentDTO {

    Long getCourseId();
    Long getTotalEnrollments();
}