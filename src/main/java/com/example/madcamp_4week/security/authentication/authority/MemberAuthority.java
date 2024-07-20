package com.example.madcamp_4week.security.authentication.authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@Getter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
class MemberAuthority implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return Roles.MEMBER.getRole();
    }
}
