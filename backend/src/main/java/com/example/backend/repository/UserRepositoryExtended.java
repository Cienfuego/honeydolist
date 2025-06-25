package com.example.backend.repository;

import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


    public interface UserRepositoryExtended extends JpaRepository<User, Long> {
        @Query("SELECT u FROM User u WHERE u.id = :id AND u.dateDeleted IS NULL")
        Optional<Long> findByUserId(Long id);
    }

