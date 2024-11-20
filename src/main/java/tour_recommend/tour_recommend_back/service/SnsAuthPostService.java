package tour_recommend.tour_recommend_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tour_recommend.tour_recommend_back.dto.SnsAuthPostDto.CreateSnsAuthPostRequest;
import tour_recommend.tour_recommend_back.dto.SnsAuthPostDto.CreateSnsAuthPostResponse;
import tour_recommend.tour_recommend_back.entity.SnsAuthPost;
import tour_recommend.tour_recommend_back.repository.SnsAuthPostRepository;

@RequiredArgsConstructor
@Service
public class SnsAuthPostService {
    private final SnsAuthPostRepository snsAuthPostRepository;

    public CreateSnsAuthPostResponse createSnsAuthPost(CreateSnsAuthPostRequest createSnsAuthPostRequest) {
        SnsAuthPost snsAuthPost = CreateSnsAuthPostRequest.builder()
                .snsUserName(createSnsAuthPostRequest.snsUserName())
                .phoneNumber(createSnsAuthPostRequest.phoneNumber())
                .email(createSnsAuthPostRequest.email())
                .title(createSnsAuthPostRequest.title())
                .contents(createSnsAuthPostRequest.contents())
                .imagePathList(createSnsAuthPostRequest.imagePathList())
                .build()
                .toEntity();

        SnsAuthPost snsAuthPostPs = snsAuthPostRepository.save(snsAuthPost);

        return CreateSnsAuthPostResponse.builder()
                .id(snsAuthPostPs.getId())
                .snsUserName(snsAuthPostPs.getSnsUserName())
                .phoneNumber(snsAuthPostPs.getPhoneNumber())
                .email(snsAuthPostPs.getEmail())
                .title(createSnsAuthPostRequest.title())
                .contents(createSnsAuthPostRequest.contents())
                .imagePathList(snsAuthPostPs.getImagePathList())
                .createAt(snsAuthPostPs.getCreatedAt())
                .updateAt(snsAuthPostPs.getUpdatedAt())
                .build();
    }
}
