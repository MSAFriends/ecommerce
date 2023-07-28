package com.github.msafriends.modulecommon.exception.member.member;

import com.github.msafriends.modulecommon.exception.BusinessException;
import com.github.msafriends.modulecommon.exception.ErrorCode;

public class MemberAlreadyExistException extends BusinessException {
    public MemberAlreadyExistException(String email) {
        super(ErrorCode.MEMBER_ALREADY_EXIST, email);
    }
}
