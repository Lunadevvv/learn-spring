package com.luna.identity_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;
}
