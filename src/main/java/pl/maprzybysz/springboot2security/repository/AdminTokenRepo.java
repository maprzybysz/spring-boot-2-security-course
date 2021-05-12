package pl.maprzybysz.springboot2security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.maprzybysz.springboot2security.model.AdminToken;
import pl.maprzybysz.springboot2security.model.VerificationToken;

@Repository
public interface AdminTokenRepo extends JpaRepository<AdminToken, Long> {
    VerificationToken findByValue(String value);
}
