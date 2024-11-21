package tour_recommend.tour_recommend_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tour_recommend.tour_recommend_back.dto.ReviewPostDto.CreateReviewPostRequest;
import tour_recommend.tour_recommend_back.dto.ReviewPostDto.CreateReviewPostResponse;
import tour_recommend.tour_recommend_back.entity.ReviewPost;
import tour_recommend.tour_recommend_back.repository.ReviewPostRepository;

@RequiredArgsConstructor
@Service
public class ReviewPostService {
    private final ReviewPostRepository reviewPostRepository;

    public CreateReviewPostResponse createReviewPost(CreateReviewPostRequest createReviewPostRequest) {
        ReviewPost reviewPost = CreateReviewPostRequest.builder()
                .title(createReviewPostRequest.title())
                .category(createReviewPostRequest.category())
                .contents(createReviewPostRequest.contents())
                .imagePathList(createReviewPostRequest.imagePathList())
                .build()
                .toEntity();

        ReviewPost reviewPostPs = reviewPostRepository.save(reviewPost);

        return CreateReviewPostResponse.builder()
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
}
