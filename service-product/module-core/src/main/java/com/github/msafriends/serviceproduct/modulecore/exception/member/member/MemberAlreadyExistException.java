package com.github.msafriends.serviceproduct.modulecore.exception.member.member;

import com.github.msafriends.serviceproduct.modulecore.exception.BusinessException;
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;

public class MemberAlreadyExistException extends BusinessException {
    public MemberAlreadyExistException(String email) {
        super(ErrorCode.MEMBER_ALREADY_EXIST, email);
    }
}
