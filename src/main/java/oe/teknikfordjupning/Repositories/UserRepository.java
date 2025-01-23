package oe.teknikfordjupning.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import oe.teknikfordjupning.Models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByEmail(String email);

}
