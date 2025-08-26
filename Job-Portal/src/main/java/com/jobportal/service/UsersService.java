package com.jobportal.service;

import com.jobportal.entity.JobSeekerProfile;
import com.jobportal.entity.RecruiterProfile;
import com.jobportal.entity.Users;
import com.jobportal.repository.JobSeekerProfileRepository;
import com.jobportal.repository.RecuriterProfileRepository;
import com.jobportal.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final RecuriterProfileRepository recuriterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public Users addNewUser(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser = usersRepository.save(users);
        int userTypeId = users.getUserTypeId().getUserTypeId();
        if(userTypeId == 1){
            recuriterProfileRepository.save(new RecruiterProfile(savedUser));
        }
        else{
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }
        return savedUser;
    }

    public Optional<Users> getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }

    public Object getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof AnonymousAuthenticationToken){
            String userName = authentication.getName();
            Users users = usersRepository.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
            int userId = users.getUserId();
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                RecruiterProfile recruiterProfile = recuriterProfileRepository.findById(userId)
                        .orElse(new RecruiterProfile());
                return recruiterProfile;
            }
            else {
                JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById(userId)
                        .orElse(new JobSeekerProfile());
                return jobSeekerProfile;
            }
        }
        return null;
    }
}
