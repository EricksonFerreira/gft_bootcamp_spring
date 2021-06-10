package br.com.springboot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PhoneType {
    
    HOME("home"),
    MOBILE("mobile"),
    COMMERCIAL("comercial");

    private final String description;
}
