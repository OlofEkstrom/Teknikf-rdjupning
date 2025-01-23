package oe.teknikfordjupning.Services;

import oe.teknikfordjupning.Models.User;
import oe.teknikfordjupning.Models.VerificationToken;

import java.util.Optional;

public interface IUserService {

    String registerUser(String email, String firstName, String lastName, String password);

    Optional<User> findByEmail(String email);

    void saveUserVerificationToken(User theUser, String verificationToken);

    String validateToken(String theToken);

    VerificationToken generateNewVerificationToken(String oldToken);

    void createPasswordResetTokenForUser(User user, String passwordToken);

    String validatePasswordResetToken(String passwordResetToken);

    User findUserByPasswordToken(String passwordResetToken);

    void resetUserPassword(User user, String newPassword);



}
