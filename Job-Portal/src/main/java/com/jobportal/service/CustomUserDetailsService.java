package com.jobportal.service;

import com.jobportal.entity.Users;
import com.jobportal.repository.UsersRepository;
import com.jobportal.util.CustomUsersDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Could Not Found User"));
        return new CustomUsersDetails(user);
    }
}
