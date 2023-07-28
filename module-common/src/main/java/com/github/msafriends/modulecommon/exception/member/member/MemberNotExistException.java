package com.github.msafriends.modulecommon.exception.member.member;

import com.github.msafriends.modulecommon.exception.EntityNotFoundException;
import com.github.msafriends.modulecommon.exception.ErrorCode;

public class MemberNotExistException extends EntityNotFoundException {

    public MemberNotExistException(Long memberId) {
        super(ErrorCode.MEMBER_NOT_EXIST, memberId);
    }
}
