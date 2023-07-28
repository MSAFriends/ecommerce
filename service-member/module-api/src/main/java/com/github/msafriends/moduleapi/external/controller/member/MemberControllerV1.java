package com.github.msafriends.moduleapi.external.controller.member;

import com.github.msafriends.moduleapi.dto.request.member.MemberSignupRequest;
import com.github.msafriends.moduleapi.dto.response.member.MemberSignupResponse;
import com.github.msafriends.moduleapi.external.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
public class MemberControllerV1 {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberSignupResponse> signUpMember(@RequestBody @Valid MemberSignupRequest request) {
        return ResponseEntity.ok(memberService.createMember(request));
    }
}
