package pl.maprzybysz.springboot2security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.maprzybysz.springboot2security.model.AppUser;
import pl.maprzybysz.springboot2security.repository.AppUserRepo;

import javax.mail.MessagingException;

@Component
public class Start {

    private PasswordEncoder passwordEncoder;
    private AppUserRepo appUserRepo;




    public Start(PasswordEncoder passwordEncoder, AppUserRepo appUserRepo) throws MessagingException {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepo = appUserRepo;


        this.passwordEncoder = passwordEncoder;
        this.appUserRepo = appUserRepo;

        AppUser appUser = new AppUser();
        appUser.setUsername("Janusz");
        appUser.setAuthority(new SimpleGrantedAuthority("ROLE_USER"));
        appUser.setEnabled(true);
        appUser.setPassword(passwordEncoder.encode("Janusz123"));
        appUserRepo.save(appUser);

        AppUser appUser2 = new AppUser();
        appUser2.setUsername("Dobromir");
        appUser2.setAuthority(new SimpleGrantedAuthority("ROLE_ADMIN"));
        appUser2.setEnabled(true);
        appUser2.setPassword(passwordEncoder.encode("Dobromir123"));
        appUserRepo.save(appUser2);

    }
}
