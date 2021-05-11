package pl.maprzybysz.springboot2security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserService {

    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenRepo verificationTokenRepo;
    private MailSenderService mailSenderService;

    @Autowired
    public UserService(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder, VerificationTokenRepo verificationTokenRepo, MailSenderService mailSenderService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepo = verificationTokenRepo;
        this.mailSenderService = mailSenderService;
    }



    public void addNewUser(AppUser user, HttpServletRequest httpServletRequest) throws MessagingException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        appUserRepo.save(user);
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken(token,user);
        verificationTokenRepo.save(verificationToken);

        String url = "http://"+ httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort()
                + httpServletRequest.getContextPath() + "/verifyToken?token="+token;

        mailSenderService.sendMail(user.getUsername(), "Verification Token",url, false);


    }

    public void verifyToken(String token) {
        AppUser appUser = verificationTokenRepo.findByValue(token).getAppUser();
        appUser.setEnabled(true);
        appUserRepo.save(appUser);
    }
}
