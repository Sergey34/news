package net.news.domain.users.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contact extends UserNew {
    private String phoneNumber;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Parent> parents;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    public static class Parent {
        @Id
        @GeneratedValue
        private int id;
        private String name;
    }
}