package org.zerock.apiserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.zerock.apiserver.dto.MemberDTO;
import org.zerock.apiserver.dto.PostDTO;
import org.zerock.apiserver.dto.CommentDTO;
import org.zerock.apiserver.service.AdminService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/members")
    public List<MemberDTO> getAllMembers() {
        return adminService.getAllMembers();
    }

    @DeleteMapping("/members/{mno}")
    public Map<String, String> deleteMember(@PathVariable("mno") Long mno) {
        adminService.deleteMember(mno);
        return Map.of("RESULT", "SUCCESS");
    }

    @GetMapping("/posts")
    public List<PostDTO> getAllPosts() {
        return adminService.getAllPosts();
    }

    @DeleteMapping("/posts/{pno}")
    public Map<String, String> deletePost(@PathVariable("pno") Long pno) {
        adminService.deletePost(pno);
        return Map.of("RESULT", "SUCCESS");
    }

    @GetMapping("/comments")
    public List<CommentDTO> getAllComments() {
        return adminService.getAllComments();
    }

    @DeleteMapping("/comments/{id}")
    public Map<String, String> deleteComment(@PathVariable("id") Long id) {
        adminService.deleteComment(id);
        return Map.of("RESULT", "SUCCESS");
    }
}