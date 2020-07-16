package com.exadel.placebook.converter;


import com.exadel.placebook.model.dto.UserDto;
import com.exadel.placebook.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto convert(User p) {
        UserDto pd = new UserDto();
        pd.setId(p.getId());
        pd.setEmail(p.getEmail());
        pd.setPassword(p.getPassword());
        pd.setName(p.getName());
        pd.setSurname(p.getSurname());
        pd.setHr_id(p.getHr_id());
        pd.setRole(p.getRole());
        pd.setPhotoUrl(p.getPhotoUrl());
        return pd;
    }

    public User convert(UserDto pd) {
        User p = new User();
        p.setId(pd.getId());
        p.setEmail(pd.getEmail());
        p.setPassword(pd.getPassword());
        p.setName(pd.getName());
        p.setSurname(pd.getSurname());
        p.setHr_id(pd.getHr_id());
        p.setRole(pd.getRole());
        p.setPhotoUrl(pd.getPhotoUrl());
        return p;
    }
}
