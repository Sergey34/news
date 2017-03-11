package net.news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.news.domain.users.Role;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String login;
    private String name;
    private String email;
    private Set<Role> roles;
    private Date lastVisit;
}
