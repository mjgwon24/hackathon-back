package tour_recommend.tour_recommend_back.dto.sns_auth;

import lombok.Builder;
import tour_recommend.tour_recommend_back.entity.sns_auth.SnsAuthPost;

import java.time.LocalDateTime;
import java.util.List;

public record SnsAuthPostDto () {
    @Builder
    public record CreateSnsAuthPostRequest(
            String postType,
            String snsUserName,
            String phoneNumber,
            String email,
            String title,
            String contents,
            List<String> imagePathList
    ) {
        public SnsAuthPost toEntity() {
            return SnsAuthPost.builder()
                    .postType(this.postType)
                    .snsUserName(this.snsUserName)
                    .phoneNumber(this.phoneNumber)
                    .email(this.email)
                    .title(this.title)
                    .contents(this.contents)
                    .imagePathList(this.imagePathList)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
        }
    }

    @Builder
    public record CreateSnsAuthPostResponse(
            Long id,
            String postType,
            String snsUserName,
            String phoneNumber,
            String email,
            String title,
            String contents,
            List<String> imagePathList,
            LocalDateTime createAt,
            LocalDateTime updateAt
    ) {}

    @Builder
    public record FetchSnsAuthPostResponse(
            Long id,
            String postType,
            String snsUserName,
            String phoneNumber,
            String email,
            String title,
            String contents,
            List<String> imagePathList,
            LocalDateTime createAt,
            LocalDateTime updateAt
    ) {}

    @Builder
    public record FetchSnsAuthPostsResponse(
            List<FetchedSnsAuthPost> snsAuthPosts,
            int currentPage,
            int totalPages,
            Long totalElements
    ) {
        @Builder
        public record FetchedSnsAuthPost(
            Long id,
            String postType,
            String title,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
        ) {}
    }
}
