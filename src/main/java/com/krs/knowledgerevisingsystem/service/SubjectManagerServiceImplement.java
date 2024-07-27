package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.Subject;
import com.krs.knowledgerevisingsystem.entity.SubjectManager;
import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import com.krs.knowledgerevisingsystem.repository.SubjectManagerRepository;
import com.krs.knowledgerevisingsystem.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SubjectManagerServiceImplement implements SubjectManagerService {
    private final SubjectManagerRepository subjectManagerRepository;
    private final UserRepository userRepository;

    public SubjectManagerServiceImplement(SubjectManagerRepository subjectManagerRepository, UserRepository userRepository) {
        this.subjectManagerRepository = subjectManagerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<SubjectManager> findAll(Pageable pageable) {
        return subjectManagerRepository.findAll(pageable);
    }

    @Override
    public List<SubjectManager> findAll() {
        return subjectManagerRepository.findAll();
    }

    @Override
    public void save(Subject subject, User user) {
        SubjectManager subjectManager = new SubjectManager();
        subjectManager.setSubject(subject);
        subjectManager.setUser(user);
        subjectManagerRepository.save(subjectManager);
    }

    @Override
    public void updateManager(Long id, Long manager) throws DataIntegrityViolationException, SaveAccountInvalidException{
        try {
            SubjectManager subjectManager = subjectManagerRepository.findBySubject_Id(id);
            User user = userRepository.findById(manager).orElseThrow(() -> new IllegalArgumentException("User not found"));
            subjectManager.setUser(user);
            subjectManagerRepository.save(subjectManager);
        } catch (DataIntegrityViolationException e) {
            throw new SaveAccountInvalidException("Subject Manager with id " + id + " already exists");
        }
    }

    @Override
    public Page<SubjectManager> findAllBySubject_NameContainingOrSubject_CodeContaining(String name, String code, Pageable pageable, String column, String direction) {
        Page<SubjectManager> subjects = subjectManagerRepository.findAllBySubject_NameContainingOrSubject_CodeContaining(code, name, pageable);
        List<SubjectManager> sortedSubjects;
        if (column == null || direction == null) {
            return subjects;
        } else {
            sortedSubjects = subjects.stream()
                    .sorted((subjects1, subjects2) -> {
                        Object value1;
                        Object value2;
                        if ("manager".equals(column)) {
                            if (!subjects1.getSubject().getSubjectManager().isEmpty() && !subjects2.getSubject().getSubjectManager().isEmpty()) {
                                value1 = subjects1.getSubject().getSubjectManager().get(0).getUser().getFullName();
                                value2 = subjects2.getSubject().getSubjectManager().get(0).getUser().getFullName();
                            } else {
                                return 0;
                            }
                        }else if("speciality".equals(column)) {
                            if (!subjects1.getSubject().getRole().getSpeciality().isEmpty() && !subjects2.getSubject().getRole().getSpeciality().isEmpty()) {
                                value1 = subjects1.getSubject().getRole().getSpeciality();
                                value2 = subjects2.getSubject().getRole().getSpeciality();
                            } else {
                                return 0;
                            }
                        }
                        else {
                            value1 = getFieldValue(subjects1, column);
                            value2 = getFieldValue(subjects2, column);
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

        return new PageImpl<>(sortedSubjects, pageable, subjects.getTotalElements());
    }

    @Override
    public Page<SubjectManager> findAllBySubject_StatusAndSubject_NameContainingOrSubject_CodeContaining(boolean status, String name, String code, Pageable pageable, String column, String direction) {
        Page<SubjectManager> subjects = subjectManagerRepository.findAllBySubject_StatusAndSubject_NameContainingOrSubject_CodeContaining(status, code, name, pageable);
        List<SubjectManager> sortedSubjects;
        if (column == null || direction == null) {
            return subjects;
        } else {
            sortedSubjects = subjects.stream()
                    .sorted((subjects1, subjects2) -> {
                        Object value1;
                        Object value2;
                        if ("manager".equals(column)) {
                            if (!subjects1.getSubject().getSubjectManager().isEmpty() && !subjects2.getSubject().getSubjectManager().isEmpty()) {
                                value1 = subjects1.getSubject().getSubjectManager().get(0).getUser().getFullName();
                                value2 = subjects2.getSubject().getSubjectManager().get(0).getUser().getFullName();
                            } else {
                                return 0;
                            }
                        }else if("speciality".equals(column)) {
                            if (!subjects1.getSubject().getRole().getSpeciality().isEmpty() && !subjects2.getSubject().getRole().getSpeciality().isEmpty()) {
                                value1 = subjects1.getSubject().getRole().getSpeciality();
                                value2 = subjects2.getSubject().getRole().getSpeciality();
                            } else {
                                return 0;
                            }
                        }
                        else {
                            value1 = getFieldValue(subjects1, column);
                            value2 = getFieldValue(subjects2, column);
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

        return new PageImpl<>(sortedSubjects, pageable, subjects.getTotalElements());
    }

    @Override
    public Page<SubjectManager> findAllBySubject_StatusAndSubject_Role_IdAndSubject_NameContainingOrSubject_CodeContaining(boolean status, Long speciality, String name, String code, Pageable pageable, String column, String direction) {
        Page<SubjectManager> subjects = subjectManagerRepository.findAllBySubject_StatusAndSubject_Role_IdAndSubject_NameContainingOrSubject_CodeContaining(status, speciality, name, code, pageable);
        List<SubjectManager> sortedSubjects;
        if (column == null || direction == null) {
            return subjects;
        } else {
            sortedSubjects = subjects.stream()
                    .sorted((subjects1, subjects2) -> {
                        Object value1;
                        Object value2;
                        if ("manager".equals(column)) {
                            if (!subjects1.getSubject().getSubjectManager().isEmpty() && !subjects2.getSubject().getSubjectManager().isEmpty()) {
                                value1 = subjects1.getSubject().getSubjectManager().get(0).getUser().getFullName();
                                value2 = subjects2.getSubject().getSubjectManager().get(0).getUser().getFullName();
                            } else {
                                return 0;
                            }
                        }else if("speciality".equals(column)) {
                            if (!subjects1.getSubject().getRole().getSpeciality().isEmpty() && !subjects2.getSubject().getRole().getSpeciality().isEmpty()) {
                                value1 = subjects1.getSubject().getRole().getSpeciality();
                                value2 = subjects2.getSubject().getRole().getSpeciality();
                            } else {
                                return 0;
                            }
                        }
                        else {
                            value1 = getFieldValue(subjects1, column);
                            value2 = getFieldValue(subjects2, column);
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

        return new PageImpl<>(sortedSubjects, pageable, subjects.getTotalElements());
    }

    @Override
    public Page<SubjectManager> findAllBySubject_Role_IdAndSubject_NameContainingOrSubject_CodeContaining(Long speciality, String name, String code, Pageable pageable, String column, String direction) {
        Page<SubjectManager> subjects = subjectManagerRepository.findAllBySubject_Role_IdAndSubject_NameContainingOrSubject_CodeContaining(speciality, name, code, pageable);
        List<SubjectManager> sortedSubjects;
        if (column == null || direction == null) {
            return subjects;
        } else {
            sortedSubjects = subjects.stream()
                    .sorted((subjects1, subjects2) -> {
                        Object value1;
                        Object value2;
                        if ("manager".equals(column)) {
                            if (!subjects1.getSubject().getSubjectManager().isEmpty() && !subjects2.getSubject().getSubjectManager().isEmpty()) {
                                value1 = subjects1.getSubject().getSubjectManager().get(0).getUser().getFullName();
                                value2 = subjects2.getSubject().getSubjectManager().get(0).getUser().getFullName();
                            } else {
                                return 0;
                            }
                        }else if("speciality".equals(column)) {
                            if (!subjects1.getSubject().getRole().getSpeciality().isEmpty() && !subjects2.getSubject().getRole().getSpeciality().isEmpty()) {
                                value1 = subjects1.getSubject().getRole().getSpeciality();
                                value2 = subjects2.getSubject().getRole().getSpeciality();
                            } else {
                                return 0;
                            }
                        }
                        else {
                            value1 = getFieldValue(subjects1, column);
                            value2 = getFieldValue(subjects2, column);
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

        return new PageImpl<>(sortedSubjects, pageable, subjects.getTotalElements());
    }

    @Override
    public Page<SubjectManager> findAllBySubject_StatusAndUser_IdAndSubject_NameContainingOrSubject_CodeContaining(boolean status, Long id, String name, String code, Pageable pageable, String column, String direction) {
        Page<SubjectManager> subjects = subjectManagerRepository.findAllBySubject_StatusAndUser_IdAndSubject_NameContainingOrSubject_CodeContaining(status,id, name, code, pageable);
        List<SubjectManager> sortedSubjects;
        if (column == null || direction == null) {
            return subjects;
        } else {
            sortedSubjects = subjects.stream()
                    .sorted((subjects1, subjects2) -> {
                        Object value1;
                        Object value2;
                        if ("manager".equals(column)) {
                            if (!subjects1.getSubject().getSubjectManager().isEmpty() && !subjects2.getSubject().getSubjectManager().isEmpty()) {
                                value1 = subjects1.getSubject().getSubjectManager().get(0).getUser().getFullName();
                                value2 = subjects2.getSubject().getSubjectManager().get(0).getUser().getFullName();
                            } else {
                                return 0;
                            }
                        }else if("speciality".equals(column)) {
                            if (!subjects1.getSubject().getRole().getSpeciality().isEmpty() && !subjects2.getSubject().getRole().getSpeciality().isEmpty()) {
                                value1 = subjects1.getSubject().getRole().getSpeciality();
                                value2 = subjects2.getSubject().getRole().getSpeciality();
                            } else {
                                return 0;
                            }
                        }
                        else {
                            value1 = getFieldValue(subjects1, column);
                            value2 = getFieldValue(subjects2, column);
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

        return new PageImpl<>(sortedSubjects, pageable, subjects.getTotalElements());
    }

    @Override
    public Page<SubjectManager> findAllBySubject_StatusAndUser_IdAndSubject_Role_IdAndSubject_NameContainingOrSubject_CodeContaining(boolean status, Long id, Long speciality, String name, String code, Pageable pageable, String column, String direction) {
        Page<SubjectManager> subjects = subjectManagerRepository.findAllBySubject_StatusAndUser_IdAndSubject_Role_IdAndSubject_NameContainingOrSubject_CodeContaining(status,id, speciality, name, code, pageable);
        List<SubjectManager> sortedSubjects;
        if (column == null || direction == null) {
            return subjects;
        } else {
            sortedSubjects = subjects.stream()
                    .sorted((subjects1, subjects2) -> {
                        Object value1;
                        Object value2;
                        if ("manager".equals(column)) {
                            if (!subjects1.getSubject().getSubjectManager().isEmpty() && !subjects2.getSubject().getSubjectManager().isEmpty()) {
                                value1 = subjects1.getSubject().getSubjectManager().get(0).getUser().getFullName();
                                value2 = subjects2.getSubject().getSubjectManager().get(0).getUser().getFullName();
                            } else {
                                return 0;
                            }
                        }else if("speciality".equals(column)) {
                            if (!subjects1.getSubject().getRole().getSpeciality().isEmpty() && !subjects2.getSubject().getRole().getSpeciality().isEmpty()) {
                                value1 = subjects1.getSubject().getRole().getSpeciality();
                                value2 = subjects2.getSubject().getRole().getSpeciality();
                            } else {
                                return 0;
                            }
                        }
                        else {
                            value1 = getFieldValue(subjects1, column);
                            value2 = getFieldValue(subjects2, column);
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

        return new PageImpl<>(sortedSubjects, pageable, subjects.getTotalElements());
    }

    @Override
    public Page<SubjectManager> findAllBySubject_Role_IdAndUser_IdAndSubject_NameContainingOrSubject_CodeContaining(Long speciality, Long id, String name, String code, Pageable pageable, String column, String direction) {
        Page<SubjectManager> subjects = subjectManagerRepository.findAllBySubject_Role_IdAndUser_IdAndSubject_NameContainingOrSubject_CodeContaining(speciality,id, name, code, pageable);
        List<SubjectManager> sortedSubjects;
        if (column == null || direction == null) {
            return subjects;
        } else {
            sortedSubjects = subjects.stream()
                    .sorted((subjects1, subjects2) -> {
                        Object value1;
                        Object value2;
                        if ("manager".equals(column)) {
                            if (!subjects1.getSubject().getSubjectManager().isEmpty() && !subjects2.getSubject().getSubjectManager().isEmpty()) {
                                value1 = subjects1.getSubject().getSubjectManager().get(0).getUser().getFullName();
                                value2 = subjects2.getSubject().getSubjectManager().get(0).getUser().getFullName();
                            } else {
                                return 0;
                            }
                        }else if("speciality".equals(column)) {
                            if (!subjects1.getSubject().getRole().getSpeciality().isEmpty() && !subjects2.getSubject().getRole().getSpeciality().isEmpty()) {
                                value1 = subjects1.getSubject().getRole().getSpeciality();
                                value2 = subjects2.getSubject().getRole().getSpeciality();
                            } else {
                                return 0;
                            }
                        }
                        else {
                            value1 = getFieldValue(subjects1, column);
                            value2 = getFieldValue(subjects2, column);
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

        return new PageImpl<>(sortedSubjects, pageable, subjects.getTotalElements());
    }

    @Override
    public Page<SubjectManager> findAllByUser_Id(Long id, Pageable pageable) {
        return subjectManagerRepository.findAllByUser_Id(id, pageable);
    }

    @Override
    public Page<SubjectManager> findAllByUser_IdAndSubject_NameContainingOrSubject_CodeContaining(Long id, String name, String code, Pageable pageable, String column, String direction) {
        Page<SubjectManager> subjects = subjectManagerRepository.findAllByUser_IdAndSubject_NameContainingOrSubject_CodeContaining(id, name, code, pageable);
        List<SubjectManager> sortedSubjects;
        if (column == null || direction == null) {
            return subjects;
        } else {
            sortedSubjects = subjects.stream()
                    .sorted((subjects1, subjects2) -> {
                        Object value1;
                        Object value2;
                        if ("manager".equals(column)) {
                            if (!subjects1.getSubject().getSubjectManager().isEmpty() && !subjects2.getSubject().getSubjectManager().isEmpty()) {
                                value1 = subjects1.getSubject().getSubjectManager().get(0).getUser().getFullName();
                                value2 = subjects2.getSubject().getSubjectManager().get(0).getUser().getFullName();
                            } else {
                                return 0;
                            }
                        }else if("speciality".equals(column)) {
                            if (!subjects1.getSubject().getRole().getSpeciality().isEmpty() && !subjects2.getSubject().getRole().getSpeciality().isEmpty()) {
                                value1 = subjects1.getSubject().getRole().getSpeciality();
                                value2 = subjects2.getSubject().getRole().getSpeciality();
                            } else {
                                return 0;
                            }
                        }
                        else {
                            value1 = getFieldValue(subjects1, column);
                            value2 = getFieldValue(subjects2, column);
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

        return new PageImpl<>(sortedSubjects, pageable, subjects.getTotalElements());
    }


    public Object getFieldValue(SubjectManager subjectManager, String fieldName) {
        try {
            Object currentObject = subjectManager;
            for (String part : fieldName.split("\\.")) {
                Field field = currentObject.getClass().getDeclaredField(part);
                field.setAccessible(true);
                currentObject = field.get(currentObject);
            }
            return currentObject;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
