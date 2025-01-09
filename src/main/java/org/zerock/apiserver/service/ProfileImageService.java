package org.zerock.apiserver.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.apiserver.domain.Member;
import org.zerock.apiserver.domain.ProfileImage;
import org.zerock.apiserver.dto.ProfileImageDTO;

@Transactional
public interface ProfileImageService {

    ProfileImageDTO get(Long ino);

    ProfileImageDTO getByMno(Long mno);

    boolean existsByMno(Long mno);

    Long register(ProfileImageDTO profileImageDTO);

    void remove(Long ino);

    ProfileImage dtoToEntity(ProfileImageDTO profileImageDTO);

    default ProfileImageDTO entityToDTO(ProfileImage profileImage, Member member) {

        if (profileImage.getFileName() == null) {
            throw new NullPointerException();
        }

        return ProfileImageDTO.builder()
                .pino(profileImage.getPino())
                .fileName(profileImage.getFileName())
                .mno(member.getMno())
                .build();
    }

}
