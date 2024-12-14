package com.example.lab2.service.post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.lab2.controller.specifications.PostFilter;
import com.example.lab2.controller.specifications.PostSpecifications;
import com.example.lab2.entity.post.Post;
import com.example.lab2.entity.post.Post.PostStatus;
import com.example.lab2.repository.PostRepository;

import org.springframework.core.io.Resource;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final PostUpdater postUpdater;
    private final PostCreator postCreator;
    private final PhotosHandler photosHandler;

    @Transactional(rollbackFor = Exception.class)
    public Optional<Post> createPost(Post post, MultipartFile photo) {
        var imageKey = savePostPhoto(postRepository.count(), photo);

        if (imageKey.isEmpty())
            return Optional.ofNullable(null);

        post.setImageKey(imageKey.get());
        
        return postCreator.createPost(post);
    }

    public Optional<Post> updatePost(Post post) {
        return postUpdater.updatePost(post);
    }

    public boolean setPostStatus(Long postId, PostStatus postStatus) {
        return postUpdater.isUpdateablePostStatus(postId, postStatus);
    }

    public List<Post> getPostList(Long id) {
        return (List<Post>) postRepository.findAllByHousingOwnerId(id);
    }

    public boolean deletePost(Long postId) {
        Optional<Post> getResult = postRepository.findById(postId);  

        if(getResult.isEmpty())
            return false;

        Post post = getResult.get();

        if (postUpdater.postHasCurrentBooking(post.getId()))
            return false;     
        if (post.getScore() == Post.POST_NOT_SCORED) {
            postRepository.deleteById(postId);
            return true;
        }

        post.setPostStatus(PostStatus.ON_DELETE);
        postRepository.save(post);
        return true;
    }

    public List<Post> getPage(PostFilter filter, Pageable pageable) {
        return postRepository.findAll(PostSpecifications.fromFilter(filter), pageable).getContent();
    }

    @Scheduled(fixedDelay = 5, initialDelay = 1, timeUnit = TimeUnit.MINUTES) 
    public void updatePostsScore() {
        List<Post> all = findAll();

        for (Post post : all) {
            postUpdater.updatePostScore(post.getId());
        }
    }

    public Optional<Resource> getPostPhoto(String imageKey) {
        return photosHandler.getPostPhoto(imageKey);
    }

    public Optional<String> savePostPhoto(Long uniqueNumb, MultipartFile fileToSaves) {
        return photosHandler.savePostPhoto(uniqueNumb, fileToSaves);
    }

    /////////////////////////////////////////////////
    /// Test
    public List<Post> findAll() {
        return (List<Post>) postRepository.findAll();
    }

    public Optional<Post> findPost(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> findAll(PostFilter filter) {
        return postRepository.findAll(PostSpecifications.fromFilter(filter));
    }
}
