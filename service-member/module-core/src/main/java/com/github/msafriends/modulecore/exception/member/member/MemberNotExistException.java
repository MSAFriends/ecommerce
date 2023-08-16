package com.github.msafriends.modulecore.exception.member.member;

import com.github.msafriends.modulecore.exception.EntityNotFoundException;
import com.github.msafriends.modulecore.exception.ErrorCode;

public class MemberNotExistException extends EntityNotFoundException {

    public MemberNotExistException(Long memberId) {
        super(ErrorCode.MEMBER_NOT_EXIST, memberId);
    }
}
