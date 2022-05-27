package com.devmountain.photocollect.entities;

import com.devmountain.photocollect.dtos.UserDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@Data               ///Getters and Setters///
@AllArgsConstructor ///Constructor with all arguments///
@NoArgsConstructor///Constructor with no arguments///
public class User {
///Instance Variables///
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;


    /////One user to many posts; Set where posts will be stored. /////
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference
    private Set<Post> postSet = new HashSet<>();


////link to User Entity////
    public User(UserDto userDto){
        if(userDto.getUsername() != null){
            this.username = userDto.getUsername();
        }
        if(userDto.getPassword() != null) {
            this.password = userDto.getPassword();
        }
    }

}
