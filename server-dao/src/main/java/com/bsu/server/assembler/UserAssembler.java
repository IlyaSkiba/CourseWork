package com.bsu.server.assembler;

import com.bsu.server.dto.security.UserAccount;
import com.bsu.service.api.global.admin.dto.UserDto;

/**
 * @author Ilya SKiba
 * @created 23/11/12
 */
public class UserAssembler {
    public static UserAccount assemble(UserDto userDto) {
        UserAccount entity = new UserAccount();
        entity.setId(userDto.getUserId());
        entity.setFirstName(userDto.getFirstName());
        entity.setLastName(userDto.getLastName());
        entity.setMiddleName(userDto.getLastName());
        //TODO: increase this!
        return entity;
    }

    public static UserDto assemble(UserAccount userAccount) {
        UserDto dto = new UserDto();
        dto.setUserId(userAccount.getId()).setFirstName(userAccount.getFirstName())
                .setLastName(userAccount.getLastName()).setMiddleName(userAccount.getMiddleName());
        return dto;
    }
}
