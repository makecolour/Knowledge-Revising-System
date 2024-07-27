package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.*;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import com.krs.knowledgerevisingsystem.repository.CourseRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImplement implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImplement(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Boolean save(Role role, String courseName, User teacher, Subject subject, String description, boolean status) throws SaveAccountInvalidException {

        Course course = new Course();
        course.setRole(role);
        course.setCourseName(courseName);
        course.setTeacher(teacher);
        course.setSubject(subject);
        course.setDescription(description);
        course.setStatus(status);
        courseRepository.save(course);
        return true;
    }

    @Override
    public List<Course> getAllCourse() {
        return courseRepository.getAll();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course findCourseByName(String name) {
        return courseRepository.findByCourseName(name);
    }

    @Transactional
    @Override
    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Page<Course> findAllCourseBySubjectContaining(Pageable pageable, String name, String column, String direction) {
        Page<Course> course = courseRepository.findAllBySubject_Name(pageable, name);
        List<Course> sortedCourses;
        if (column == null || direction == null) {
            return course;
        } else {
            sortedCourses = course.stream()
                    .sorted((course1, course2) -> {
                        Object value1;
                        Object value2;
                        // Replace "manager" and "speciality" with appropriate Course fields
                        value1 = course1.getRole().getName().length() >= 2 ? course1.getRole().getName().substring(course1.getRole().getName().length() - 2) : course1.getRole().getName();
                        value2 = course2.getRole().getName().length() >= 2 ? course2.getRole().getName().substring(course2.getRole().getName().length() - 2) : course2.getRole().getName();

                        if (value1 instanceof Comparable && value2 instanceof Comparable) {
                            Comparable comparable1 = (Comparable) value1;
                            Comparable comparable2 = (Comparable) value2;
                            return "asc".equalsIgnoreCase(direction) ? comparable1.compareTo(comparable2) : comparable2.compareTo(comparable1);
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());
        }

        return new PageImpl<>(sortedCourses, pageable, course.getTotalElements());
    }

    @Transactional
    @Override
    public Page<Course> findAllBySubject_IdAndCourseNameContaining(Pageable pageable, long id, String name, String column, String direction) {
        Page<Course> course = courseRepository.findAllBySubject_IdAndStatusAndCourseNameContaining(pageable, id, true, name);
        List<Course> sortedCourses;
        if (column == null || direction == null) {
            return course;
        } else {
            sortedCourses = course.stream()
                    .sorted((course1, course2) -> {
                        Object value1;
                        Object value2;
                        if ("semester".equals(column)) {
                            if (!course1.getRole().getName().isEmpty() && !course2.getRole().getName().isEmpty()) {
                                value1 = course1.getRole().getName().length() >= 2 ? course1.getRole().getName().substring(course1.getRole().getName().length() - 2) : course1.getRole().getName();
                                value2 = course2.getRole().getName().length() >= 2 ? course2.getRole().getName().substring(course2.getRole().getName().length() - 2) : course2.getRole().getName();
                            } else {
                                return 0;
                            }
                        } else {
                            value1 = getFieldValue(course1, column);
                            value2 = getFieldValue(course2, column);
                        }


                        if (value1 instanceof Comparable && value2 instanceof Comparable) {
                            Comparable comparable1 = (Comparable) value1;
                            Comparable comparable2 = (Comparable) value2;
                            return "asc".equalsIgnoreCase(direction) ? comparable1.compareTo(comparable2) : comparable2.compareTo(comparable1);
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(sortedCourses, pageable, course.getTotalElements());
    }

    @Override
    public void updateCourse(Long id, String courseName, String description, boolean status, Subject subjectId, User teacherId, Role role) {

        Course course = courseRepository.findById(id).get();
        course.setCourseName(courseName);
        course.setDescription(description);
        course.setSubject(subjectId);
        course.setTeacher(teacherId);
        course.setStatus(status);
        course.setRole(role);
        courseRepository.save(course);
    }

    @Transactional
    @Override
    public Page<Course> findAllBySubject_IdAndStatusAndCourseNameContaining(Pageable pageable, long id, boolean status, String name, String column, String direction) {

        Page<Course> course = courseRepository.findAllBySubject_IdAndStatusAndCourseNameContaining(pageable, id, status, name);
        List<Course> sortedCourses;
        if (column == null || direction == null) {
            return course;
        } else {
            sortedCourses = course.stream()
                    .sorted((course1, course2) -> {
                        Object value1;
                        Object value2;
                        if ("semester".equals(column)) {
                            if (!course1.getRole().getName().isEmpty() && !course2.getRole().getName().isEmpty()) {
                                value1 = course1.getRole().getName().length() >= 2 ? course1.getRole().getName().substring(course1.getRole().getName().length() - 2) : course1.getRole().getName();
                                value2 = course2.getRole().getName().length() >= 2 ? course2.getRole().getName().substring(course2.getRole().getName().length() - 2) : course2.getRole().getName();
                            } else {
                                return 0;
                            }
                        } else {
                            value1 = getFieldValue(course1, column);
                            value2 = getFieldValue(course2, column);
                        }
                        if (value1 instanceof Comparable && value2 instanceof Comparable) {
                            Comparable comparable1 = (Comparable) value1;
                            Comparable comparable2 = (Comparable) value2;
                            return "asc".equalsIgnoreCase(direction) ? comparable1.compareTo(comparable2) : comparable2.compareTo(comparable1);
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());
        }

        return new PageImpl<>(sortedCourses, pageable, course.getTotalElements());
    }

    @Transactional
    @Override
    public Page<Course> findAllByStatusAndCourseNameContaining(Pageable pageable, boolean status, String name, String column, String direction) {
        Page<Course> course = courseRepository.findAllByStatusAndCourseNameContaining(pageable, status, name);
        List<Course> sortedCourses;
        if (column == null || direction == null) {
            return course;
        } else {
            sortedCourses = course.stream()
                    .sorted((course1, course2) -> {
                        Object value1;
                        Object value2;
                        // Replace "manager" and "speciality" with appropriate Course fields
                        if ("semester".equals(column)) {
                            if (!course1.getRole().getName().isEmpty() && !course2.getRole().getName().isEmpty()) {
                                value1 = course1.getRole().getName().length() >= 2 ? course1.getRole().getName().substring(course1.getRole().getName().length() - 2) : course1.getRole().getName();
                                value2 = course2.getRole().getName().length() >= 2 ? course2.getRole().getName().substring(course2.getRole().getName().length() - 2) : course2.getRole().getName();
                            } else {
                                return 0;
                            }
                        } else {
                            value1 = getFieldValue(course1, column);
                            value2 = getFieldValue(course2, column);
                        }
                        if (value1 instanceof Comparable && value2 instanceof Comparable) {
                            Comparable comparable1 = (Comparable) value1;
                            Comparable comparable2 = (Comparable) value2;
                            return "asc".equalsIgnoreCase(direction) ? comparable1.compareTo(comparable2) : comparable2.compareTo(comparable1);
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(sortedCourses, pageable, course.getTotalElements());
    }

    @Override
    public Page<Course> findAllByStatus(Pageable pageable, boolean status) {
        return courseRepository.findAllByStatus(pageable, status);
    }

    @Override
    public void updateByStatusId(Long id, boolean b) {
        Course course = courseRepository.findById(id).get();
        course.setStatus(b);
        courseRepository.save(course);
    }

    @Override
    public Page<Course> findByCourseNameContaining(Pageable pageable, String name, String column, String direction) {
        Page<Course> course = courseRepository.findByStatusAndAndCourseNameContaining(pageable, true, name);
        List<Course> sortedCourses;
        if (column == null || direction == null) {
            return course;
        } else {
            sortedCourses = course.stream()
                    .sorted((course1, course2) -> {
                        Object value1;
                        Object value2;
                        if ("semester".equals(column)) {
                            if (!course1.getRole().getName().isEmpty() && !course2.getRole().getName().isEmpty()) {
                                value1 = course1.getRole().getName().length() >= 2 ? course1.getRole().getName().substring(course1.getRole().getName().length() - 2) : course1.getRole().getName();
                                value2 = course2.getRole().getName().length() >= 2 ? course2.getRole().getName().substring(course2.getRole().getName().length() - 2) : course2.getRole().getName();                            } else {
                                return 0;
                            }
                        } else {
                            value1 = getFieldValue(course1, column);
                            value2 = getFieldValue(course2, column);
                        }

                        if (value1 instanceof Comparable && value2 instanceof Comparable) {
                            Comparable comparable1 = (Comparable) value1;
                            Comparable comparable2 = (Comparable) value2;
                            return "asc".equalsIgnoreCase(direction) ? comparable1.compareTo(comparable2) : comparable2.compareTo(comparable1);
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(sortedCourses, pageable, course.getTotalElements());
    }


    public Object getFieldValue(Course course, String fieldName) {
        try {
            Object currentObject = course;
            String[] parts = fieldName.split("\\.");
            for (String part : parts) {
                Field field = currentObject.getClass().getDeclaredField(part);
                field.setAccessible(true);
                currentObject = field.get(currentObject);
            }
            return currentObject;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

//    public Object getFieldValue(Course subject, String fieldName) {
//        try {
//            Field field = Course.class.getDeclaredField(fieldName);
//            field.setAccessible(true);
//            Object value = field.get(subject);
//            if (value instanceof Role) {
//                return value;  // return the Role instance itself
//            }
//            return value;
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void saveUserToDatabase(MultipartFile file) throws Exception {
        if (isValidExcelFile(file)) {
            List<User> users = getUserDataFromExcel(file.getInputStream());
            // Save users to database here
        } else {
            throw new Exception("Invalid file");
        }
    }

    @Override
    public List<Course> findAllByRole_Id( long id) {
        return courseRepository.findAllByRole_Id( id);
    }

    @Override
    public List<Course> getAllCourseByTeacher(User user) {
        return courseRepository.findAllByTeacherAndStatus(user, true);
    }

    @Override
    public List<Course> getAllCourseByManager(User user) {
        List <Course> courses = new ArrayList<>();
        for(Course course : courseRepository.findAll()){
            for(SubjectManager subjectManager : user.getSubjectManager()){
                if(course.getSubject().getId() == subjectManager.getSubject().getId()&& course.isStatus()){
                    courses.add(course);
                }
            }
        }
        return courses;
    }


    public Page<Course> findAllByNameContainingIgnoreCaseAndStatusAndRoleIdAndUserIdAndSubjectId(String name, Boolean status, Long roleId, Long userId, Long subjectId, Pageable pageable, String column, String direction) {
        Page<Course> course = courseRepository.findAllByNameContainingIgnoreCaseAndStatusAndRoleIdAndUserIdAndSubjectId(name, status, roleId, userId, subjectId, pageable);
        List<Course> sortedCourses;
        if (column == null || direction == null) {
            return course;
        } else {
            sortedCourses = course.stream()
                    .sorted((course1, course2) -> {
                        Object value1;
                        Object value2;
                        if ("semester".equals(column)) {
                            if (!course1.getRole().getName().isEmpty() && !course2.getRole().getName().isEmpty()) {
                                value1 = course1.getRole().getName().length() >= 2 ? course1.getRole().getName().substring(course1.getRole().getName().length() - 2) : course1.getRole().getName();
                                value2 = course2.getRole().getName().length() >= 2 ? course2.getRole().getName().substring(course2.getRole().getName().length() - 2) : course2.getRole().getName();                            } else {
                                return 0;
                            }
                        } else {
                            value1 = getFieldValue(course1, column);
                            value2 = getFieldValue(course2, column);
                        }

                        if (value1 instanceof Comparable && value2 instanceof Comparable) {
                            Comparable comparable1 = (Comparable) value1;
                            Comparable comparable2 = (Comparable) value2;
                            return "asc".equalsIgnoreCase(direction) ? comparable1.compareTo(comparable2) : comparable2.compareTo(comparable1);
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(sortedCourses, pageable, course.getTotalElements());
    }

    public static boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public static List<User> getUserDataFromExcel(InputStream inputStream) {
        List<User> users = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("Sheet1");
            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                User user = new User();
                int cellIndex = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0:
                            user.setFullName(cell.getStringCellValue());
                            break;
                        case 1:
                            user.setEmail(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                }
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }


}
