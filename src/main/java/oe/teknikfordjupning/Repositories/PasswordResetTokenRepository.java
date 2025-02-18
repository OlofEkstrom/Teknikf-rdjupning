package oe.teknikfordjupning.Repositories;

import oe.teknikfordjupning.Models.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String theToken);
}
