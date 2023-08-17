package com.github.msafriends.serviceorder.modulecore.exception.member.member;

import com.github.msafriends.serviceorder.modulecore.exception.EntityNotFoundException;
import com.github.msafriends.serviceorder.modulecore.exception.ErrorCode;

public class MemberNotExistException extends EntityNotFoundException {

    public MemberNotExistException(Long memberId) {
        super(ErrorCode.MEMBER_NOT_EXIST, memberId);
    }
}
