package com.devmountain.photocollect.repositories;

import com.devmountain.photocollect.entities.Post;
import com.devmountain.photocollect.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserEquals(User user);
}


