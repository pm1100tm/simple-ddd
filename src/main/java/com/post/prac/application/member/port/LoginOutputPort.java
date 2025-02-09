package com.post.prac.application.member.port;

import com.post.prac.application.member.output.LoginDto;
import com.post.prac.core.domain.member.dto.LoginImplOutDto;

public interface LoginOutputPort {

	LoginImplOutDto createToken(LoginDto dto);
}
