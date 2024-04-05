package com.iamnana.DemoSpringSecurity.repo;

import com.iamnana.DemoSpringSecurity.entity.OurUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OurUserRepository extends JpaRepository<OurUser, Long> {
    Optional<OurUser> findByEmail(String email);
}
