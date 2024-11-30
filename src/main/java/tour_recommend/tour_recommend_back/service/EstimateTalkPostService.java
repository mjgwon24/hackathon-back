package tour_recommend.tour_recommend_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkPostDto.FetchEstimateTalkPostsResponse;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkPostDto.FetchEstimateTalkPostResponse;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkPostDto.FetchEstimateTalkPostsResponse.FetchedEstimateTalkPost;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkPostDto.CreateEstimateTalkPostResponse;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkPostDto.CreateEstimateTalkPostRequest;
import tour_recommend.tour_recommend_back.entity.estimate_talk.EstimateTalkPost;
import tour_recommend.tour_recommend_back.repository.EstimateTalkPostRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EstimateTalkPostService {
    private final EstimateTalkPostRepository estimateTalkPostRepository;

    // 견적톡 등록
    public CreateEstimateTalkPostResponse createEstimateTalkPost(CreateEstimateTalkPostRequest createEstimateTalkPostRequest) {
        EstimateTalkPost estimateTalkPost = CreateEstimateTalkPostRequest.builder()
                .phoneNumber(createEstimateTalkPostRequest.phoneNumber())
                .email(createEstimateTalkPostRequest.email())
                .title(createEstimateTalkPostRequest.title())
                .contents(createEstimateTalkPostRequest.contents())
                .imagePathList(createEstimateTalkPostRequest.imagePathList())
                .build()
                .toEntity();

        EstimateTalkPost estimateTalkPostPs = estimateTalkPostRepository.save(estimateTalkPost);

        return CreateEstimateTalkPostResponse.builder()
                .id(estimateTalkPostPs.getId())
                .phoneNumber(estimateTalkPostPs.getPhoneNumber())
                .email(estimateTalkPostPs.getEmail())
                .title(estimateTalkPostPs.getTitle())
                .contents(estimateTalkPostPs.getContents())
                .imagePathList(estimateTalkPostPs.getImagePathList())
                .createAt(estimateTalkPostPs.getCreatedAt())
                .updateAt(estimateTalkPostPs.getUpdatedAt())
                .build();
    }

    // 견적톡 조회
    public FetchEstimateTalkPostResponse fetchEstimateTalkPost(Long postId) {
        EstimateTalkPost estimateTalkPostPs = estimateTalkPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("postId에 해당하는 견적톡 게시글이 존재하지 않습니다."));

        return FetchEstimateTalkPostResponse.builder()
                .id(estimateTalkPostPs.getId())
                .phoneNumber(estimateTalkPostPs.getPhoneNumber())
                .email(estimateTalkPostPs.getEmail())
                .title(estimateTalkPostPs.getTitle())
                .contents(estimateTalkPostPs.getContents())
                .imagePathList(estimateTalkPostPs.getImagePathList())
                .createAt(estimateTalkPostPs.getCreatedAt())
                .updateAt(estimateTalkPostPs.getUpdatedAt())
                .build();
    }

    // 견적톡 리스트 조회
    public FetchEstimateTalkPostsResponse fetchEstimateTalkPosts(int pageNumber, int size) {
        // Sort 객체를 생성하여 정렬 기준 설정
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        // 페이지 번호화 페이지 크기를 사용하여 PageRequest 객체 생성
        PageRequest pageRequest = PageRequest.of(pageNumber, size, sort);

        Page<EstimateTalkPost> estimateTalkPostPage = estimateTalkPostRepository.findAll(pageRequest);

        List<FetchedEstimateTalkPost> estimateTalkPosts = estimateTalkPostPage.get()
                .map(estimateTalkPost -> FetchedEstimateTalkPost.builder()
                        .id(estimateTalkPost.getId())
                        .phoneNumber(estimateTalkPost.getPhoneNumber())
                        .email(estimateTalkPost.getEmail())
                        .title(estimateTalkPost.getTitle())
                        .contents(estimateTalkPost.getContents())
                        .createAt(estimateTalkPost.getCreatedAt())
                        .updateAt(estimateTalkPost.getUpdatedAt())
                        .build())
                .toList();

        return FetchEstimateTalkPostsResponse.builder()
                .estimateTalkPostList(estimateTalkPosts)
                .currentPage(estimateTalkPostPage.getNumber())
                .totalPage(estimateTalkPostPage.getTotalPages())
                .totalElements(estimateTalkPostPage.getTotalElements())
                .build();
    }
}
