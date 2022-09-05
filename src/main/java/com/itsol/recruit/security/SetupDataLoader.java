package com.itsol.recruit.security;

import com.itsol.recruit.entity.Role;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.RoleRepository;
import com.itsol.recruit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Configuration
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    final
    RoleRepository roleRepository;

    final
    UserRepository userRepository;

    final
    PasswordEncoder passwordEncoder;

    public SetupDataLoader(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }
        if (userRepository.findByUserName("admin") == null) {
            List<Role> adminRole = roleRepository.findByCode("ROLE_ADMIN");
            User user = new User();
            user.setUserName("admin");
            user.setName("admin");
            user.setEmail("admin@gmail.com");
            user.setPhoneNumber("0388888888");
            user.setBirthDay(new Date(1999 - 04 - 29));
            user.setPassword(passwordEncoder.encode("Phongxx99@"));
            user.setActivate(true);
            user.setDelete(false);
            user.setRoles(adminRole);
            userRepository.save(user);
            alreadySetup = true;
        }
        else{
            alreadySetup = true;
        }
    }
}
