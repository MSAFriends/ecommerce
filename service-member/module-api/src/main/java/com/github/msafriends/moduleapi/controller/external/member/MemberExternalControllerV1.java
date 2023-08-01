package com.github.msafriends.moduleapi.controller.external.member;

import com.github.msafriends.moduleapi.dto.request.member.MemberSignupRequest;
import com.github.msafriends.moduleapi.dto.response.member.MemberSignupResponse;
import com.github.msafriends.moduleapi.service.BatchService;
import com.github.msafriends.moduleapi.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
public class MemberExternalControllerV1 {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberSignupResponse> signUpMember(@RequestBody @Valid MemberSignupRequest request) {
        return ResponseEntity.ok(memberService.createMember(request));
    }
}
