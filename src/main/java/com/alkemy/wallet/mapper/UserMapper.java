package com.alkemy.wallet.mapper;

import com.alkemy.wallet.dto.TransactionPaymentDto;
import com.alkemy.wallet.dto.basicDto.UserBasicDTO;
import com.alkemy.wallet.model.Transaction;
import com.alkemy.wallet.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

import java.util.List;
@Component
public class UserMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public UserBasicDTO userEntity2DTO(User entity){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserBasicDTO result = this.modelMapper.map(entity, UserBasicDTO.class);
        return result;
    }

    public User userDTO2Entity(UserBasicDTO userBasicDTO){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User result = this.modelMapper.map(userBasicDTO, User.class);
        return result;
    }

    public List<UserBasicDTO> userEntity2DTOList(List<User> entities){
        List<UserBasicDTO> dtos = new ArrayList<>();
        for(User entity:entities){
            dtos.add(this.userEntity2DTO(entity));
        }
        return dtos;
    }

    public List<User> userDTO2EntityList(List<UserBasicDTO> dtos) {
        List<User> entities = new ArrayList<>();
        for (UserBasicDTO dto : dtos) {
            entities.add(this.userDTO2Entity(dto));
        }
        return entities;
    }


}