package pl.maprzybysz.springboot2security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private AdminTokenRepo adminTokenRepo;
    private MailSenderService mailSenderService;

    @Autowired
    public UserService(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder,
                       VerificationTokenRepo verificationTokenRepo, AdminTokenRepo adminTokenRepo ,
                       MailSenderService mailSenderService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepo = verificationTokenRepo;
        this.adminTokenRepo = adminTokenRepo;
        this.mailSenderService = mailSenderService;
    }



    public void addNewUser(AppUser user, HttpServletRequest httpServletRequest) throws MessagingException {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(new SimpleGrantedAuthority("ROLE_USER"));
        appUserRepo.save(user);
        String token = UUID.randomUUID().toString();


        VerificationToken verificationToken = new VerificationToken(token,user);
        verificationTokenRepo.save(verificationToken);

        String url = "http://"+ httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort()
                + httpServletRequest.getContextPath() + "/verifyToken?token="+token;

        mailSenderService.sendMail(user.getUsername(), "Verification Token",url, false);

        if(user.isAdmin()){
            String adminTokenValue = UUID.randomUUID().toString();
            AdminToken adminToken = new AdminToken(adminTokenValue,user);
            adminTokenRepo.save(adminToken);

            String urlAdmin = "http://"+ httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort()
                    + httpServletRequest.getContextPath() + "/verifyAdmin?token="+token;

            mailSenderService.sendMail("mefiudev678@gmail.com", "Verification AdminToken",urlAdmin, false);
        }

    }


    public void verifyToken(String token) {
        AppUser appUser = verificationTokenRepo.findByValue(token).getAppUser();
        appUser.setEnabled(true);
        appUserRepo.save(appUser);
    }

    public void verifyAdminToken(String token) {
        AppUser appUser = verificationTokenRepo.findByValue(token).getAppUser();
        appUser.setAuthority(new SimpleGrantedAuthority("ROLE_ADMIN"));
        appUserRepo.save(appUser);
    }
}
