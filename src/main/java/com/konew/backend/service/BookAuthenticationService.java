package com.konew.backend.service;

import com.konew.backend.security.userDetails.UserDetailsImpl;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class BookAuthenticationService
{
    public Long getUserId(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        return principal.getId();
    }

    public boolean isInstanceUserDetailsImpl(Authentication authentication) {
        return authentication.getPrincipal() instanceof UserDetailsImpl;
    }

    Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
