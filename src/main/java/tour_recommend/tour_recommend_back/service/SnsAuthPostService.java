package tour_recommend.tour_recommend_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tour_recommend.tour_recommend_back.dto.sns_auth.SnsAuthPostDto.FetchSnsAuthPostsResponse.FetchedSnsAuthPost;
import tour_recommend.tour_recommend_back.dto.sns_auth.SnsAuthPostDto.FetchSnsAuthPostsResponse;
import tour_recommend.tour_recommend_back.dto.sns_auth.SnsAuthPostDto.FetchSnsAuthPostResponse;
import tour_recommend.tour_recommend_back.dto.sns_auth.SnsAuthPostDto.CreateSnsAuthPostRequest;
import tour_recommend.tour_recommend_back.dto.sns_auth.SnsAuthPostDto.CreateSnsAuthPostResponse;
import tour_recommend.tour_recommend_back.entity.sns_auth.SnsAuthPost;
import tour_recommend.tour_recommend_back.repository.SnsAuthPostRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SnsAuthPostService {
    private final SnsAuthPostRepository snsAuthPostRepository;

    public CreateSnsAuthPostResponse createSnsAuthPost(CreateSnsAuthPostRequest createSnsAuthPostRequest) {
        SnsAuthPost snsAuthPost = CreateSnsAuthPostRequest.builder()
                .postType(createSnsAuthPostRequest.postType())
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
                .postType(snsAuthPostPs.getPostType())
                .snsUserName(snsAuthPostPs.getSnsUserName())
                .phoneNumber(snsAuthPostPs.getPhoneNumber())
                .email(snsAuthPostPs.getEmail())
                .title(snsAuthPostPs.getTitle())
                .contents(snsAuthPostPs.getContents())
                .imagePathList(snsAuthPostPs.getImagePathList())
                .createAt(snsAuthPostPs.getCreatedAt())
                .updateAt(snsAuthPostPs.getUpdatedAt())
                .build();
    }

    public FetchSnsAuthPostResponse fetchSnsAuthPost(Long postId) {
        SnsAuthPost snsAuthPostPs = snsAuthPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("postId에 해당하는 SNS 인증 게시글이 존재하지 않습니다."));

        return FetchSnsAuthPostResponse.builder()
                .id(snsAuthPostPs.getId())
                .postType(snsAuthPostPs.getPostType())
                .snsUserName(snsAuthPostPs.getSnsUserName())
                .phoneNumber(snsAuthPostPs.getPhoneNumber())
                .email(snsAuthPostPs.getEmail())
                .title(snsAuthPostPs.getTitle())
                .contents(snsAuthPostPs.getContents())
                .imagePathList(snsAuthPostPs.getImagePathList())
                .createAt(snsAuthPostPs.getCreatedAt())
                .updateAt(snsAuthPostPs.getUpdatedAt())
                .build();
    }

    public FetchSnsAuthPostsResponse fetchSnsAuthPosts(int pageNumber, int size) {
        // Sort 객체를 생성하여 정렬 기준을 설정합니다.
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        // 페이지 번호와 페이지 크기를 사용하여 PageRequest 객체를 생성합니다.
        PageRequest pageRequest = PageRequest.of(pageNumber, size, sort);

        Page<SnsAuthPost> fetchSnsAuthPosts = snsAuthPostRepository.findAll(pageRequest);

        List<FetchedSnsAuthPost> snsAuthPosts = fetchSnsAuthPosts.get()
                .map(snsAuthPost -> FetchedSnsAuthPost.builder()
                        .id(snsAuthPost.getId())
                        .postType(snsAuthPost.getPostType())
                        .title(snsAuthPost.getTitle())
                        .contents(snsAuthPost.getContents())
                        .createdAt(snsAuthPost.getCreatedAt())
                        .updatedAt(snsAuthPost.getUpdatedAt())
                        .build())
                .toList();

        return FetchSnsAuthPostsResponse.builder()
                .snsAuthPosts(snsAuthPosts)
                .currentPage(fetchSnsAuthPosts.getNumber())
                .totalPages(fetchSnsAuthPosts.getTotalPages())
                .totalElements(fetchSnsAuthPosts.getTotalElements())
                .build();
    }

    public FetchSnsAuthPostsResponse fetchSnsAuthPostsByPostType(String postType, int pageNumber, int size) {
        // Sort 객체를 생성하여 정렬 기준을 설정합니다.
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        // 페이지 번호와 페이지 크기를 사용하여 PageRequest 객체를 생성합니다.
        PageRequest pageRequest = PageRequest.of(pageNumber, size, sort);

        Page<SnsAuthPost> fetchedSnsAuthPostsByPostType = snsAuthPostRepository.findByPostType(postType, pageRequest);

        List<FetchedSnsAuthPost> snsAuthPostsByPostType = fetchedSnsAuthPostsByPostType.get()
                .map(snsAuthPostByPostType -> FetchedSnsAuthPost.builder()
                        .id(snsAuthPostByPostType.getId())
                        .postType(snsAuthPostByPostType.getPostType())
                        .title(snsAuthPostByPostType.getTitle())
                        .contents(snsAuthPostByPostType.getContents())
                        .createdAt(snsAuthPostByPostType.getCreatedAt())
                        .updatedAt(snsAuthPostByPostType.getUpdatedAt())
                        .build())
                .toList();

        return FetchSnsAuthPostsResponse.builder()
                .snsAuthPosts(snsAuthPostsByPostType)
                .currentPage(fetchedSnsAuthPostsByPostType.getNumber())
                .totalPages(fetchedSnsAuthPostsByPostType.getTotalPages())
                .totalElements(fetchedSnsAuthPostsByPostType.getTotalElements())
                .build();
    }
}
