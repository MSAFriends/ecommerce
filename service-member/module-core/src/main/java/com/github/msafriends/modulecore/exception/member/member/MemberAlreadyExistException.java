package com.github.msafriends.modulecore.exception.member.member;

import com.github.msafriends.modulecore.exception.BusinessException;
import com.github.msafriends.modulecore.exception.ErrorCode;

public class MemberAlreadyExistException extends BusinessException {
    public MemberAlreadyExistException(String email) {
        super(ErrorCode.MEMBER_ALREADY_EXIST, email);
    }
}
