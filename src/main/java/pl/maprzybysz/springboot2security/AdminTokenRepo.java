package pl.maprzybysz.springboot2security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminTokenRepo extends JpaRepository<AdminToken, Long> {
    VerificationToken findByValue(String value);
}
