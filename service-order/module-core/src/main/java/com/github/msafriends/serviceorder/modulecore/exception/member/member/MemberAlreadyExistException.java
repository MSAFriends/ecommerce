package com.github.msafriends.serviceorder.modulecore.exception.member.member;

import com.github.msafriends.serviceorder.modulecore.exception.BusinessException;
import com.github.msafriends.serviceorder.modulecore.exception.ErrorCode;

public class MemberAlreadyExistException extends BusinessException {
    public MemberAlreadyExistException(String email) {
        super(ErrorCode.MEMBER_ALREADY_EXIST, email);
    }
}
