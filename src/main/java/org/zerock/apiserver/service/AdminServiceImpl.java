package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.domain.Member;
import org.zerock.apiserver.domain.Post;
import org.zerock.apiserver.domain.Comment;
import org.zerock.apiserver.dto.MemberDTO;
import org.zerock.apiserver.dto.PostDTO;
import org.zerock.apiserver.dto.CommentDTO;
import org.zerock.apiserver.repository.MemberRepository;
import org.zerock.apiserver.repository.PostRepository;
import org.zerock.apiserver.repository.CommentRepository;
import org.zerock.apiserver.util.CustomServiceException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMember(Long mno) {
        if (!memberRepository.existsById(mno)) {
            throw new CustomServiceException("NOT_EXIST_MEMBER");
        }
        memberRepository.deleteById(mno);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePost(Long pno) {
        if (!postRepository.existsById(pno)) {
            throw new CustomServiceException("NOT_EXIST_POST");
        }
        postRepository.deleteById(pno);
    }

    @Override
    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new CustomServiceException("NOT_EXIST_COMMENT");
        }
        commentRepository.deleteById(id);
    }

    private MemberDTO entityToDTO(Member member) {
        return MemberDTO.builder()
                .mno(member.getMno())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .role(member.getRole())
                .build();
    }

    private PostDTO entityToDTO(Post post) {
        return PostDTO.builder()
                .pno(post.getPno())
                .title(post.getTitle())
                .content(post.getContent())
                .postType(post.getPostType())
                .build();
    }

    private CommentDTO entityToDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .build();
    }
}