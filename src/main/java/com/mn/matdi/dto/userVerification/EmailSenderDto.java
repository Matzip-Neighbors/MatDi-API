package com.mn.matdi.dto.userVerification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailSenderDto {

    private String fromMail;
    private String toMail;
    private String title;
    private String content;
}
