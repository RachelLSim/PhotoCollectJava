package com.devmountain.photocollect.entities;


import com.devmountain.photocollect.dtos.PostDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Posts")
@Data                ///Getters and Setters(lombok)///
@AllArgsConstructor  ///Constructor with id, url and caption arguments///
@NoArgsConstructor   ///Constructor with no arguments///
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String url;

    @Column(columnDefinition = "text")
    private String caption;

    @ManyToOne
    @JsonBackReference
    private User user;

    public Post(PostDto postDto) {
        if(postDto.getUrl() != null) {
            this.url = postDto.getUrl();
        }
        if(postDto.getCaption() != null) {
            this.caption = postDto.getCaption();
        }
    }

}
