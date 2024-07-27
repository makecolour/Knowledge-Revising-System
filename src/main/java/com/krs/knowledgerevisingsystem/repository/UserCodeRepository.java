package com.krs.knowledgerevisingsystem.repository;

import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.entity.UserCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserCodeRepository extends JpaRepository<UserCode, Long> {
    UserCode findByUserId(Long id);
    
    @Modifying
    @Transactional
    @Query("UPDATE UserCode uc SET uc.code = null WHERE uc.time < :#{T(java.time.LocalDateTime).now().minusMinutes(5)}")
    void updateUserCodeToNullIfTimeIsMoreThan30MinutesAgo();

    @Modifying
    @Transactional
    @Query("UPDATE UserCode uc SET uc.code = null WHERE uc.userId = :id")
    void setCodeToNull(Long id);

}
