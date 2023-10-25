package com.project.rampcards.dto;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int userId;
    private String name;
    private String password;
}
