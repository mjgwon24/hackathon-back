package tour_recommend.tour_recommend_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkCommentDto.FetchEstimateTalkCommentsResponse.FetchedEstimateTalkComment;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkCommentDto.FetchEstimateTalkCommentsResponse;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkCommentDto.CreateEstimateTalkCommentResponse;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkCommentDto.CreateEstimateTalkCommentRequest;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkCommentDto.UpdateEstimateTalkCommentResponse;
import tour_recommend.tour_recommend_back.dto.estimate_talk.EstimateTalkCommentDto.UpdateEstimateTalkCommentRequest;
import tour_recommend.tour_recommend_back.entity.estimate_talk.EstimateTalkComment;
import tour_recommend.tour_recommend_back.entity.estimate_talk.EstimateTalkPost;
import tour_recommend.tour_recommend_back.repository.EstimateTalkCommentRepository;
import tour_recommend.tour_recommend_back.repository.EstimateTalkPostRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EstimateTalkCommentService {
    private final EstimateTalkPostRepository estimateTalkPostRepository;
    private final EstimateTalkCommentRepository estimateTalkCommentRepository;

    // 댓글 생성
    public CreateEstimateTalkCommentResponse createEstimateTalkComment(Long postId, CreateEstimateTalkCommentRequest createEstimateTalkCommentRequest) {
        EstimateTalkPost fetchedEstimateTalkPost = estimateTalkPostRepository.findById(postId).
                orElseThrow(() -> new RuntimeException("postId에 해당하는 게시글이 존재하지 않습니다."));

        EstimateTalkComment estimateTalkComment = createEstimateTalkCommentRequest.toEntity(fetchedEstimateTalkPost);

        EstimateTalkComment estimateTalkCommentPs = estimateTalkCommentRepository.save(estimateTalkComment);

        return CreateEstimateTalkCommentResponse.builder()
                .id(estimateTalkCommentPs.getId())
                .author(estimateTalkCommentPs.getAuthor())
                .contents(estimateTalkCommentPs.getContents())
                .createdAt(estimateTalkCommentPs.getCreatedAt())
                .updatedAt(estimateTalkCommentPs.getUpdatedAt())
                .build();
    }

    // 댓글 업데이트
    public UpdateEstimateTalkCommentResponse updateEstimateTalkComment(Long commentId, UpdateEstimateTalkCommentRequest updateEstimateTalkCommentRequest) {
        EstimateTalkComment fetchedEstimateTalkComment = estimateTalkCommentRepository.findById(commentId).
                orElseThrow(() -> new RuntimeException("commentId에 해당하는 댓글이 존재하지 않습니다."));

        EstimateTalkComment estimateTalkComment = updateEstimateTalkCommentRequest.toEntity(fetchedEstimateTalkComment);

        EstimateTalkComment estimateTalkCommentPs = estimateTalkCommentRepository.save(estimateTalkComment);

        return UpdateEstimateTalkCommentResponse.builder()
                .id(estimateTalkCommentPs.getId())
                .author(estimateTalkCommentPs.getAuthor())
                .contents(estimateTalkCommentPs.getContents())
                .createdAt(estimateTalkCommentPs.getCreatedAt())
                .updatedAt(estimateTalkCommentPs.getUpdatedAt())
                .build();
    }

    // 댓글 리스트 조회
    public FetchEstimateTalkCommentsResponse fetchEstimateTalkComments(Long postId, int pageNumber, int size) {
        // 페이지 번호와 페이지 크기를 사용하여 PageRequest 객체를 생성합니다.
        PageRequest pageRequest = PageRequest.of(pageNumber, size);

        Page<EstimateTalkComment> fetchedEstimateTalkComments = estimateTalkCommentRepository.findByEstimateTalkPostId(postId, pageRequest);

        List<FetchedEstimateTalkComment> estimateTalkComments = fetchedEstimateTalkComments.get()
                .map(estimateTalkComment -> FetchedEstimateTalkComment.builder()
                        .id(estimateTalkComment.getId())
                        .author(estimateTalkComment.getAuthor())
                        .contents(estimateTalkComment.getContents())
                        .build())
                .toList();

        return FetchEstimateTalkCommentsResponse.builder()
                .posts(estimateTalkComments)
                .currentPage(fetchedEstimateTalkComments.getNumber())
                .totalPage(fetchedEstimateTalkComments.getTotalPages())
                .totalElements(fetchedEstimateTalkComments.getTotalElements())
                .build();
    }
}
