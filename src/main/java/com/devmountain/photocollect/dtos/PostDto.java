package com.devmountain.photocollect.dtos;

import com.devmountain.photocollect.entities.Post;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String url;
    private String caption;
    private UserDto userDto;

    public PostDto(Post post){
        if(post.getId() != null){
            this.id = post.getId();
        }
        if(post.getUrl() != null){
            this.url = post.getUrl();
        }
        if(post.getCaption() != null) {
            this.caption = post.getCaption();
        }
    }
}
