package com.github.msafriends.serviceproduct.modulecore.exception.member.member;

import com.github.msafriends.serviceproduct.modulecore.exception.EntityNotFoundException;
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;

public class MemberNotExistException extends EntityNotFoundException {

    public MemberNotExistException(Long memberId) {
        super(ErrorCode.MEMBER_NOT_EXIST, memberId);
    }
}
