package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.basicDto.UserBasicDTO;
import com.alkemy.wallet.mapper.UserMapper;
import com.alkemy.wallet.model.User;
import com.alkemy.wallet.repository.IUserRepository;
import com.alkemy.wallet.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void delete(Long id) throws Exception {
        if(!userRepository.findById(id).isPresent())
        {
            throw new Exception("User not found in database");
        }
        else
            userRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserBasicDTO> getUsers() {
        List<User> entities = userRepository.findAll();
        List <UserBasicDTO> userBasicDTO = userMapper.userEntity2DTOList(entities);
        return userBasicDTO;
    }

    @Override
    public User findById(long userId) throws Exception {
        return userRepository.findById(userId).orElseThrow(()-> new Exception("User not found"));
    }


}
