package com.wonseok.subject.domain.user.service.impl;

import com.wonseok.subject.domain.common.exception.UserException;
import com.wonseok.subject.domain.user.repository.MemberRepository;
import com.wonseok.subject.domain.user.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String userId) {
        return memberRepository.findOneWithAuthoritiesByUserId(userId)
                .map(member -> createUser(userId, member))
                .orElseThrow(() -> {
                    throw new UserException("계정이 존재하지 않습니다.");
                });
    }

    private org.springframework.security.core.userdetails.User createUser(String userId, Member member) {
        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(member.getUserId(),
                "",
                grantedAuthorities);
    }
}
