package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.Lesson;
import com.krs.knowledgerevisingsystem.entity.Role;
import com.krs.knowledgerevisingsystem.exception.ManageUser.SaveAccountInvalidException;
import com.krs.knowledgerevisingsystem.repository.EnrollmentRepository;
import com.krs.knowledgerevisingsystem.repository.LessonRepository;
import com.krs.knowledgerevisingsystem.repository.RoleRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.security.util.FieldUtils.getFieldValue;

@Service
public class RoleServiceImplement implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImplement(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Boolean save( String name, String description, String speciality, boolean status,Long priority) throws DataIntegrityViolationException, SaveAccountInvalidException {
        if (name == null || description == null || speciality == null || priority == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        Role role = new Role();

        role.setName(name);
        role.setDescription(description);
        role.setSpeciality(speciality);
        role.setStatus(status);
        role.setPriority(priority);
        try {
            roleRepository.save(role);
        } catch (DataIntegrityViolationException e) {
            throw new SaveAccountInvalidException("Role with name " + name + " already exists");
        }
        return true;
    }

    @Override
    public Page<Role> findAllByNameContaining(String name, String column, String direction, Pageable pageable) {
        Page<Role> roles = roleRepository.findAllByNameContaining(pageable, name);
        List<Role> sortedRoles;
        if (column == null || direction == null) {
            return roles;
        } else {
            sortedRoles = roles.stream()
                    .sorted((role1, role2) -> {
                        Object value1 = getFieldValue(role1, column);
                        Object value2 = getFieldValue(role2, column);
                        if (value1 instanceof Comparable && value2 instanceof Comparable) {
                            Comparable comparable1 = (Comparable) value1;
                            Comparable comparable2 = (Comparable) value2;
                            return "asc".equalsIgnoreCase(direction) ? comparable1.compareTo(comparable2) : comparable2.compareTo(comparable1);
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());
        }

        return new PageImpl<>(sortedRoles, pageable, roles.getTotalElements());
    }

    public Object getFieldValue(Role role, String fieldName) {
        try {
            Field field = Role.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object fieldValue = field.get(role);

            if ("semester".equals(fieldName) && fieldValue instanceof String) {
                String semester = (String) fieldValue;
                if (semester.length() >= 2) {
                    return semester.substring(semester.length() - 2);
                }
            }

            return fieldValue;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Role> findAllByNameContainingAndSpeciality(Pageable pageable, String name, String speciality, String column, String direction) {
        Page<Role> roles = roleRepository.findAllByNameContainingAndSpeciality(pageable, name, speciality);
        List<Role> sortedRoles;
        if (column == null || direction == null) {
            return roles;
        } else {
            sortedRoles = roles.stream()
                    .sorted((role1, role2) -> {
                        Object value1 = getFieldValue(role1, column);
                        Object value2 = getFieldValue(role2, column);
                        if (value1 instanceof Comparable && value2 instanceof Comparable) {
                            Comparable comparable1 = (Comparable) value1;
                            Comparable comparable2 = (Comparable) value2;
                            return "asc".equalsIgnoreCase(direction) ? comparable1.compareTo(comparable2) : comparable2.compareTo(comparable1);
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());
        }

        return new PageImpl<>(sortedRoles, pageable, roles.getTotalElements());
    }

    @Override
    public Page<Role> findAllByNameContainingAndStatus(Pageable pageable, String name, boolean status, String column, String direction) {
        Page<Role> roles = roleRepository.findAllByNameContainingAndStatus(pageable, name, status);
        List<Role> sortedRoles;
        if (column == null || direction == null) {
            return roles;
        } else {
            sortedRoles = roles.stream()
                    .sorted((role1, role2) -> {
                        Object value1 = getFieldValue(role1, column);
                        Object value2 = getFieldValue(role2, column);
                        if (value1 instanceof Comparable && value2 instanceof Comparable) {
                            Comparable comparable1 = (Comparable) value1;
                            Comparable comparable2 = (Comparable) value2;
                            return "asc".equalsIgnoreCase(direction) ? comparable1.compareTo(comparable2) : comparable2.compareTo(comparable1);
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());
        }

        return new PageImpl<>(sortedRoles, pageable, roles.getTotalElements());
    }

    @Override
    public Page<Role> findAllByNameContainingAndStatusAndSpeciality(Pageable pageable, String name, boolean status, String speciality, String column, String direction) {
        Page<Role> roles = roleRepository.findAllByNameContainingAndStatusAndSpeciality(pageable, name, status, speciality);
        List<Role> sortedRoles;
        if (column == null || direction == null) {
            return roles;
        } else {
            sortedRoles = roles.stream()
                    .sorted((role1, role2) -> {
                        Object value1 = getFieldValue(role1, column);
                        Object value2 = getFieldValue(role2, column);
                        if (value1 instanceof Comparable && value2 instanceof Comparable) {
                            Comparable comparable1 = (Comparable) value1;
                            Comparable comparable2 = (Comparable) value2;
                            return "asc".equalsIgnoreCase(direction) ? comparable1.compareTo(comparable2) : comparable2.compareTo(comparable1);
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());
        }

        return new PageImpl<>(sortedRoles, pageable, roles.getTotalElements());
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.getRoleById(id);
    }

    @Override
    public void updateUsingSaveMethod(Long id, String name, String speciality, String description, Boolean status, Long priority) throws DataIntegrityViolationException, SaveAccountInvalidException {
        try {
            Role role = roleRepository.getRoleById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));

            role.setName(name);
            role.setSpeciality(speciality);
            role.setDescription(description);
            role.setStatus(status);
            role.setPriority(priority);
            roleRepository.save(role);
        } catch (DataIntegrityViolationException e) {
            throw new SaveAccountInvalidException("Role with name " + name + " already exists");
        }
    }

    @Override
    public void updateByStatusId(Long id, boolean status) {
        Role role = getRoleById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
        role.setStatus(status);
        roleRepository.save(role);
    }
}
