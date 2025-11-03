package com.LOGIN.login.page.userEntry;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.security.Principal;

//public record DailyEntry(@Id Long id, String dailyText, String fixedText, String owner) {
//}





@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class DailyEntry{




    private Long id;
    private String dailyText;
    private String fixedText;
    private String owner;


}