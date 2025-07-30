package com.luna.identity_service.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //Tạo 1 builder class cho DTO

public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    private String username;
    @Size(min = 8, message = "PASSWORD_INVALID") //message se return khi vi pham validate
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;


}

//Vi du cho annotation @Builder (Khong can phai .set__() nua)
//          UserCreationRequest request = UserCreationRequest.builder()
//                  .username("...")
//                  .password("...")
//                  .build();
// Nó sẽ lam cho code clean hơn