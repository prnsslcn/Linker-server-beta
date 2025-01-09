package org.zerock.apiserver.service;

import org.zerock.apiserver.dto.MemberDTO;
import org.zerock.apiserver.dto.PostDTO;
import org.zerock.apiserver.dto.CommentDTO;

import java.util.List;

public interface AdminService {
    List<MemberDTO> getAllMembers();

    void deleteMember(Long mno);

    List<PostDTO> getAllPosts();

    void deletePost(Long pno);

    List<CommentDTO> getAllComments();

    void deleteComment(Long id);
}