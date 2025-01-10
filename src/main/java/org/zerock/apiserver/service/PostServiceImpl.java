package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.domain.Post;
import org.zerock.apiserver.domain.Member; // Member import 추가
import org.zerock.apiserver.dto.PostDTO;
import org.zerock.apiserver.repository.PostRepository;
import org.zerock.apiserver.repository.MemberRepository; // MemberRepository import 추가
import org.zerock.apiserver.util.CustomServiceException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository; // MemberRepository 주입

    @Override
    public PostDTO get(Long pno) {
        Post post = postRepository.findById(pno).orElseThrow(() -> new CustomServiceException("NOT_EXIST_POST"));
        return entityToDTO(post);
    }

    @Override
    public Long register(PostDTO postDTO) {
        // 회원 정보를 가져옵니다.
        Member member = memberRepository.findById(postDTO.getMno())
                .orElseThrow(() -> new CustomServiceException("NOT_EXIST_MEMBER"));

        // Post 객체를 생성할 때 member를 설정합니다.
        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .postType(postDTO.getPostType())
                .member(member) // member 설정
                .created(LocalDateTime.now())
                .build();

        postRepository.save(post);
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
                .created(LocalDateTime.now())
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
