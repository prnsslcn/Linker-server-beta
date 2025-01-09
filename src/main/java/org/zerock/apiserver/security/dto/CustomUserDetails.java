package org.zerock.apiserver.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.zerock.apiserver.domain.Member;

import java.util.*;

@Getter
@Setter
@ToString
public class CustomUserDetails implements UserDetails {

    private Member member;

    private Long mno;

    public CustomUserDetails(Member member) {
        this.member = member;
        this.mno = member.getMno();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + member.getMemberRole()));
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("mno", member.getMno());
        dataMap.put("email", member.getEmail());
        dataMap.put("nickname", member.getNickname());
        dataMap.put("social", member.isSocial());
        dataMap.put("role", member.getMemberRole().toString());
        return dataMap;
    }
}
