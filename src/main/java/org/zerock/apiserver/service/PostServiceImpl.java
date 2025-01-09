package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.domain.Post;
import org.zerock.apiserver.dto.PostDTO;
import org.zerock.apiserver.repository.PostRepository;
import org.zerock.apiserver.util.CustomServiceException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostDTO get(Long pno) {
        Post post = postRepository.findById(pno).orElseThrow(() -> new CustomServiceException("NOT_EXIST_POST"));
        return entityToDTO(post);
    }

    @Override
    public Long register(PostDTO postDTO) {
        Post post = postRepository.save(dtoToEntity(postDTO));
        return post.getPno();
    }

    @Override
    public void modify(PostDTO postDTO) {
        Post post = postRepository.findById(postDTO.getPno()).orElseThrow(() -> new CustomServiceException("NOT_EXIST_POST"));
        post.changeTitle(postDTO.getTitle());
        post.changeContent(postDTO.getContent());
        postRepository.save(post);
    }

    @Override
    public void remove(Long pno) {
        if (!postRepository.existsById(pno)) {
            throw new CustomServiceException("NOT_EXIST_POST");
        }
        postRepository.deleteById(pno);
    }

    @Override
    public List<PostDTO> getPostsByType(String postType) {
        return postRepository.findByPostType(postType).stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Post dtoToEntity(PostDTO postDTO) {
        return Post.builder()
                .pno(postDTO.getPno())
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .postType(postDTO.getPostType())
                .build();
    }

    @Override
    public PostDTO entityToDTO(Post post) {
        return PostDTO.builder()
                .pno(post.getPno())
                .title(post.getTitle())
                .content(post.getContent())
                .postType(post.getPostType())
                .build();
    }
}