package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.domain.Comment;
import org.zerock.apiserver.domain.Member;
import org.zerock.apiserver.domain.Post;
import org.zerock.apiserver.dto.CommentDTO;
import org.zerock.apiserver.repository.CommentRepository;
import org.zerock.apiserver.repository.MemberRepository;
import org.zerock.apiserver.repository.PostRepository;
import org.zerock.apiserver.util.CustomServiceException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Override
    public CommentDTO get(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomServiceException("NOT_EXIST_COMMENT"));
        return entityToDTO(comment);
    }

    @Override
    public Long register(CommentDTO commentDTO) {
        // Member 엔티티를 조회하여 설정
        Member member = memberRepository.findById(commentDTO.getMno())
                .orElseThrow(() -> new CustomServiceException("NOT_EXIST_MEMBER"));
        // Post 엔티티를 조회하여 설정
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new CustomServiceException("NOT_EXIST_POST"));

        Comment comment = dtoToEntity(commentDTO);
        comment.setMember(member);  // member 필드 설정
        comment.setPost(post);
        comment.setCreated(LocalDateTime.now());  // created 필드 설정
        comment = commentRepository.save(comment);
        return comment.getId();
    }

    @Override
    public void modify(CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentDTO.getId()).orElseThrow(() -> new CustomServiceException("NOT_EXIST_COMMENT"));
        comment.changeContent(commentDTO.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void remove(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new CustomServiceException("NOT_EXIST_COMMENT");
        }
        commentRepository.deleteById(id);
    }

    @Override
    public Comment dtoToEntity(CommentDTO commentDTO) {
        return Comment.builder()
                .id(commentDTO.getId())
                .content(commentDTO.getContent())
                .build();
    }

    @Override
    public CommentDTO entityToDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .build();
    }
}