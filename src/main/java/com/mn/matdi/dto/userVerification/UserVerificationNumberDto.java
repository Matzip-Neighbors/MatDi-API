package com.mn.matdi.dto.userVerification;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVerificationNumberDto {
    private String email;
    private String vrfNo;
}
