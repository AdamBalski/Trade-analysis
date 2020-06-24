package com.trade_analysis.service;

import com.trade_analysis.dao.EmailVerificationTokenDbDao;
import com.trade_analysis.dao.UserDbDao;
import com.trade_analysis.dtos.UserSignUpDto;
import com.trade_analysis.exception.EmailVerificationTokenNotFoundException;
import com.trade_analysis.exception.UserNotFoundException;
import com.trade_analysis.model.EmailVerificationToken;
import com.trade_analysis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class UserService {
    @Autowired
    @Qualifier("userDbDao")
    private UserDbDao userDbDao;

    @Autowired
    @Qualifier("emailVerificationTokenDbDao")
    private EmailVerificationTokenDbDao emailVerificationTokenDbDao;

    public User getUserByUsername(String username) throws UserNotFoundException {
        Optional<User> userOptional = userDbDao.getSingleResultByUsername(username);

        return userOptional.orElseThrow(UserNotFoundException::new);
    }

    public List<User> getAllUsers() {
        return userDbDao.findAll();
    }

    public List<String> getAllUserLinks() {
        return getAllUsers()
                .stream()
                .map(User::getLink)
                .collect(Collectors.toList());
    }

    public User findUserById(UUID id) throws UserNotFoundException {
        Optional<User> userOptional = userDbDao.getSingleResultById(id);
        return userOptional.orElseThrow(UserNotFoundException::new);
    }

    public void signUp(UserSignUpDto userSignUpDto) throws DataIntegrityViolationException {
        User user = User.valueOf(userSignUpDto);
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken(user);

        userDbDao.save(user);
        emailVerificationTokenDbDao.save(emailVerificationToken);
    }

    public EmailVerificationToken getEmailVerificationToken(UUID tokenUUID) throws EmailVerificationTokenNotFoundException {
        Optional<EmailVerificationToken> optional = emailVerificationTokenDbDao.getSingleResultById(tokenUUID);

        return optional.orElseThrow(EmailVerificationTokenNotFoundException::new);
    }

    public void deleteEmailVerificationToken(UUID uuid) {
        emailVerificationTokenDbDao.deleteById(uuid);
    }

    public boolean existsByUsername(String username) {
        return userDbDao.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userDbDao.existsByEmail(email);
    }

    public void deleteUserById(UUID id) {
        userDbDao.deleteById(id);
    }
}
