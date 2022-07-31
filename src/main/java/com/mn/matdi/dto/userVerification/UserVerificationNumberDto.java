package com.mn.matdi.dto.userVerification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVerificationNumberDto {
    private String email;
    private String vrfNo;
}
