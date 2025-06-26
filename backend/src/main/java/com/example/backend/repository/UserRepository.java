package com.example.backend.repository;

import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.dateDeleted IS NULL")
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id = :userid AND u.dateDeleted IS NULL")
    Optional<User> findByUserId(Long userid);

    boolean existsByUsernameAndDateDeletedIsNull(String username);
}


