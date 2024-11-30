package tour_recommend.tour_recommend_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tour_recommend.tour_recommend_back.dto.sns_auth.SnsAuthCommentDto.*;
import tour_recommend.tour_recommend_back.dto.sns_auth.SnsAuthCommentDto.FetchSnsAuthCommentsResponse.*;
import tour_recommend.tour_recommend_back.entity.sns_auth.SnsAuthComment;
import tour_recommend.tour_recommend_back.entity.sns_auth.SnsAuthPost;
import tour_recommend.tour_recommend_back.repository.SnsAuthCommentRepository;
import tour_recommend.tour_recommend_back.repository.SnsAuthPostRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SnsAuthCommentService {
    private final SnsAuthPostRepository snsAuthPostRepository;
    private final SnsAuthCommentRepository snsAuthCommentRepository;

    public CreateSnsAuthCommentResponse createSnsAuthComment(Long postId, CreateSnsAuthCommentRequest createSnsAuthCommentRequest) {
        SnsAuthPost fetchedSnsAuthPost = snsAuthPostRepository.findById(postId).
                orElseThrow(() -> new RuntimeException("postId에 해당하는 게시글이 존재하지 않습니다."));

        SnsAuthComment snsAuthComment = createSnsAuthCommentRequest.toEntity(fetchedSnsAuthPost);

        SnsAuthComment snsAuthCommentPs = snsAuthCommentRepository.save(snsAuthComment);

        return CreateSnsAuthCommentResponse.builder()
                .id(snsAuthCommentPs.getId())
                .author(snsAuthCommentPs.getAuthor())
                .contents(snsAuthCommentPs.getContents())
                .createdAt(snsAuthCommentPs.getCreatedAt())
                .updatedAt(snsAuthCommentPs.getUpdatedAt())
                .build();
    }

    public UpdateSnsAuthCommentResponse updateSnsAuthComment(Long commentId, UpdateSnsAuthCommentRequest updateSnsAuthCommentRequest) {
        SnsAuthComment fetchedSnsAuthComment = snsAuthCommentRepository.findById(commentId).
                orElseThrow(() -> new RuntimeException("commentId에 해당하는 댓글이 존재하지 않습니다."));

        SnsAuthComment snsAuthComment = updateSnsAuthCommentRequest.toEntity(fetchedSnsAuthComment);

        SnsAuthComment snsAuthCommentPs = snsAuthCommentRepository.save(snsAuthComment);

        return UpdateSnsAuthCommentResponse.builder()
                .id(snsAuthCommentPs.getId())
                .author(snsAuthCommentPs.getAuthor())
                .contents(snsAuthCommentPs.getContents())
                .createdAt(snsAuthCommentPs.getCreatedAt())
                .updatedAt(snsAuthCommentPs.getUpdatedAt())
                .build();
    }

    public FetchSnsAuthCommentsResponse fetchSnsAuthComments(Long postId, int pageNumber, int size) {
        // 페이지 번호와 페이지 크기를 사용하여 PageRequest 객체를 생성합니다.
        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        Page<SnsAuthComment> fetchedSnsAuthComments = snsAuthCommentRepository.findBySnsAuthPostId(postId, pageRequest);

        List<FetchedSnsAuthComment> snsAuthComments = fetchedSnsAuthComments.get()
                .map(snsAuthComment -> FetchedSnsAuthComment.builder()
                        .id(snsAuthComment.getId())
                        .author(snsAuthComment.getAuthor())
                        .contents(snsAuthComment.getContents())
                        .createdAt(snsAuthComment.getCreatedAt())
                        .updatedAt(snsAuthComment.getUpdatedAt())
                        .build())
                .toList();

        return FetchSnsAuthCommentsResponse.builder()
                .posts(snsAuthComments)
                .currentPage(fetchedSnsAuthComments.getNumber())
                .totalPages(fetchedSnsAuthComments.getTotalPages())
                .totalElements(fetchedSnsAuthComments.getTotalElements())
                .build();
    }
}
