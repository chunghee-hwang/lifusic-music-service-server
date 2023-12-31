package com.chung.lifusic.musicservice.dto;

import lombok.*;

@Data
public class FileDeleteResponseDto {
    private boolean isSuccess;
    private Long requestUserId; // 요청한 사람 아이디
    private Content content;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Content {
        private Long musicFileId; // 음악 파일 아이디
        private Long thumbnailFileId; // 썸네일 파일 아이디
    }
}
