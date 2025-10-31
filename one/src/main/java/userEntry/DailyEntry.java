package userEntry;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.security.Principal;

//public record DailyEntry(@Id Long id, String dailyText, String fixedText, String owner) {
//}




@Entity
@Table(name = "userData")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class DailyEntry{



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dailyText;
    private String fixedText;
    private String owner;


}