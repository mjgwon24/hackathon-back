package tour_recommend.tour_recommend_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tour_recommend.tour_recommend_back.dto.ReviewPostDto.CreateReviewPostRequest;
import tour_recommend.tour_recommend_back.dto.ReviewPostDto.ReviewPostResponse;
import tour_recommend.tour_recommend_back.entity.ReviewPost;
import tour_recommend.tour_recommend_back.repository.ReviewPostRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewPostService {
    private final ReviewPostRepository reviewPostRepository;

    // 후기 등록
    public ReviewPostResponse createReviewPost(CreateReviewPostRequest createReviewPostRequest) {
        ReviewPost reviewPost = CreateReviewPostRequest.builder()
                .title(createReviewPostRequest.title())
                .category(createReviewPostRequest.category())
                .contents(createReviewPostRequest.contents())
                .imagePathList(createReviewPostRequest.imagePathList())
                .build()
                .toEntity();

        ReviewPost reviewPostPs = reviewPostRepository.save(reviewPost);

        return ReviewPostResponse.builder()
                .id(reviewPostPs.getId())
                .title(reviewPostPs.getTitle())
                .category(reviewPostPs.getCategory())
                .contents(reviewPostPs.getContents())
                .imagePathList(reviewPostPs.getImagePathList())
                .createAt(reviewPostPs.getCreatedAt())
                .updateAt(reviewPostPs.getUpdatedAt())
                .likeCount(reviewPostPs.getLikeCount())
                .build();
    }

    // 모든 후기 조회 - createdAt 기준 최신순 반환
    public List<ReviewPostResponse> getAllReviewPosts() {
        return reviewPostRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(reviewPost -> ReviewPostResponse.builder()
                        .id(reviewPost.getId())
                        .title(reviewPost.getTitle())
                        .category(reviewPost.getCategory())
                        .contents(reviewPost.getContents())
                        .imagePathList(reviewPost.getImagePathList())
                        .createAt(reviewPost.getCreatedAt())
                        .updateAt(reviewPost.getUpdatedAt())
                        .likeCount(reviewPost.getLikeCount())
                        .build())
                .toList();
    }
}
