package com.konew.backend.service;

import com.konew.backend.security.userDetails.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


class BookAuthenticationService
{
    Long getUserId(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        return principal.getId();
    }

    boolean isInstanceUserDetailsImpl(Authentication authentication) {
        return authentication.getPrincipal() instanceof UserDetailsImpl;
    }

    Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
