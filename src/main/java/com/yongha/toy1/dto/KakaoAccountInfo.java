package com.yongha.toy1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoAccountInfo implements AccountInfo {
    @JsonProperty("id")
    Long id; // 회원번호	O
    @JsonProperty("kakao_account")
    KakaoAccount kakaoAccount; // 카카오계정 정보

    @Override
    public String getName() {
        return kakaoAccount.getProfile().getNickname();
    }

    @Override
    public String getThumbnailUri() {
        return kakaoAccount.getProfile().getThumbnailUri();
    }

    @Getter
    @Setter
    @ToString
    public static class KakaoAccount {
        @JsonProperty("profile")
        private Profile profile;

        @Getter
        @Setter
        @ToString
        public static class Profile {
            @JsonProperty("nickname")
            private String nickname;
            @JsonProperty("thumbnail_image_url")
            private String thumbnailUri;
        }
    }
}
