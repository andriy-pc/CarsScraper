package org.automotive.notifications.email.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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