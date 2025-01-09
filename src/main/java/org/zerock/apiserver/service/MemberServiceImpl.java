package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.domain.Member;
import org.zerock.apiserver.domain.ProfileImage;
import org.zerock.apiserver.dto.MemberDTO;
import org.zerock.apiserver.repository.MemberRepository;
import org.zerock.apiserver.util.CustomServiceException;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final ProfileImageService profileImageService;

    @Override
    public MemberDTO get(Long mno) {
        Object result = memberRepository.getMemberByMno(mno);
        if (result == null) {
            throw new CustomServiceException("NOT_EXIST_MEMBER");
        }

        Object[] arr = (Object[]) result;
        return entityToDTO((Member) arr[0], (ProfileImage) arr[1]);
    }

    @Override
    public Long getMno(String email) {
        Optional<Member> result = Optional.ofNullable(memberRepository.findByEmail(email));
        Member member = result.orElseThrow(() -> new CustomServiceException("NO_EMAIL_EXISTS"));
        return member.getMno();
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public Long register(MemberDTO memberDTO) throws CustomServiceException {
        if (existsByEmail(memberDTO.getEmail())) {
            throw new CustomServiceException("EMAIL_ALREADY_EXISTS");
        }
        Member member = memberRepository.save(dtoToEntity(memberDTO));
        return member.getMno();
    }

    @Override
    public void modify(MemberDTO memberDTO) throws CustomServiceException {
        Member member = memberRepository.findById(memberDTO.getMno()).orElseThrow(() -> new CustomServiceException("NOT_EXIST_MEMBER"));

        log.info("memberDTO: " + memberDTO);

        if (memberDTO.getEmail() != null && !memberDTO.getEmail().isEmpty()) {
            if (!member.getEmail().equals(memberDTO.getEmail()) && existsByEmail(memberDTO.getEmail())) {
                throw new CustomServiceException("EMAIL_ALREADY_EXISTS");
            }
            try {
                member.changeEmail(memberDTO.getEmail());
            } catch (IllegalArgumentException e) {
                throw new CustomServiceException("INVALID_EMAIL");
            }
        }

        if (memberDTO.getPassword() != null && !memberDTO.getPassword().isEmpty()) {
            member.changePassword(passwordEncoder.encode(memberDTO.getPassword()));
        }

        if (memberDTO.getNickname() != null && !memberDTO.getNickname().isEmpty()) {
            member.changeNickname(memberDTO.getNickname());
        }

        if (memberDTO.getPino() != null && memberDTO.getPino() != 0L) {
            ProfileImage profileImage = profileImageService.dtoToEntity(profileImageService.get(memberDTO.getPino()));
            member.changeProfileImage(profileImage);
        } else {
            member.changeProfileImage(null);
        }

        memberRepository.save(member);
    }

    @Override
    public void remove(Long mno) {
        if (!memberRepository.existsById(mno)) {
            throw new CustomServiceException("NOT_EXIST_MEMBER");
        }
        memberRepository.deleteById(mno);
    }

    @Override
    public void checkPassword(Long mno, String password) {
        Member member = memberRepository.findById(mno).orElseThrow(() -> new CustomServiceException("NOT_EXIST_MEMBER"));
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomServiceException("INVALID_PASSWORD");
        }
    }

    public Member dtoToEntity(MemberDTO memberDTO) {

        ProfileImage profileImage = memberDTO.getPino() != null ? profileImageService.dtoToEntity(profileImageService.get(memberDTO.getPino())) : null;
        if (memberDTO.getPino() != null && profileImage == null) {
            throw new CustomServiceException("PROFILE_IMAGE_NOT_FOUND");
        }

        return Member.builder()
                .mno(memberDTO.getMno())
                .email(memberDTO.getEmail())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .nickname(memberDTO.getNickname())
                .profileImage(profileImage)
                .social(memberDTO.isSocial())
                .memberRole(memberDTO.getRole())
                .build();
    }

}
