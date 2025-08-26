package com.jobportal.service;

import com.jobportal.entity.Users;
import com.jobportal.entity.UsersType;
import com.jobportal.repository.UsersRepository;
import com.jobportal.repository.UsersTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersTypeService {

    private final UsersTypeRepository usersTypeRepository;

    public List<UsersType> getAll(){
        return usersTypeRepository.findAll();
    }
}
