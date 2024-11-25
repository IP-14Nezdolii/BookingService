package com.example.lab.service.post;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.lab.entity.post.Post;
import com.example.lab.repository.interfaces.PostRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostCreator {
    private final PostRepository postRepository;

    Optional<Post> createPost(Post post) {
        if (isDatesConsistencly(post) && 
            isHousingConsistencly(post)) {
            return postRepository.save(post);
        }

        return Optional.ofNullable(null);
    } 

    boolean isDatesConsistencly(Post post) {
        var currentDate = LocalDate.now();
        var minDate = post.getMinDate();
        var maxDate = post.getMaxDate();

        return  ChronoUnit.DAYS.between(minDate, maxDate) > 0 &&
                currentDate.compareTo(minDate) <= 0;
    }

    boolean isAddressConsistencly(Post post) {
        //to do
        return true;
    }

    boolean isHousingConsistencly(Post post) {
        var housing = post.getHousing();

        return housing.getTotalArea() > 0 && isAddressConsistencly(post);
    }
}
