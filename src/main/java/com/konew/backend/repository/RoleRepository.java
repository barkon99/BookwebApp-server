package com.konew.backend.repository;

import com.konew.backend.entity.Role;
import com.konew.backend.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT id from role where name=:name ", nativeQuery = true)
    int findByName(@Param("name") String name);

    Optional<Role> findByName(RoleEnum name);
}
