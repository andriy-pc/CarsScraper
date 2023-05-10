package org.automotive.notifications.email.javabean;

import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmailDetails {
    List<String> to;
    List<String> cc;
    List<String> bcc;
    String subject;
    String text;
}