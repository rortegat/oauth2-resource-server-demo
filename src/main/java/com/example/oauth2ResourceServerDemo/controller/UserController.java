package com.example.oauth2ResourceServerDemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/whoami")
    @PreAuthorize("hasAnyRole('app_admin','app_manager','app_user')")
    public String whoami(@AuthenticationPrincipal Jwt principal){
        return "Hello: "+ principal.getClaims().get("name");
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('app_manager')")
    public String manager(){
        return "Hello manager";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('app_user')")
    public String user(){
        return "Hello user";
    }


}
