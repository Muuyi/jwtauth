package com.muabatech.jwtauth.service;

import com.muabatech.jwtauth.dto.UserDTO;
import com.muabatech.jwtauth.utils.BaseResponseDTO;

public interface UserService {

		BaseResponseDTO registerAccount(UserDTO userDTO);
}
