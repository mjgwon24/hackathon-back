package tour_recommend.tour_recommend_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tour_recommend.tour_recommend_back.dto.sale.SaleCommentDto.*;
import tour_recommend.tour_recommend_back.dto.sale.SaleCommentDto.FetchSaleCommentsResponse.FetchedSaleComment;
import tour_recommend.tour_recommend_back.entity.sale.SaleComment;
import tour_recommend.tour_recommend_back.entity.sale.SalePost;
import tour_recommend.tour_recommend_back.repository.SaleCommentRepository;
import tour_recommend.tour_recommend_back.repository.SalePostRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SaleCommentService {
    private final SalePostRepository salePostRepository;
    private final SaleCommentRepository saleCommentRepository;

    public CreateSaleCommentResponse createSaleComment(Long postId,
                                                   CreateSaleCommentRequest createSaleCommentRequest) {
        SalePost fetchedSalePost = salePostRepository.findById(postId).
                orElseThrow(() -> new RuntimeException("postId에 해당하는 게시글이 존재하지 않습니다."));

        SaleComment saleComment = createSaleCommentRequest.toEntity(fetchedSalePost);

        SaleComment saleCommentPs = saleCommentRepository.save(saleComment);

        return CreateSaleCommentResponse.builder()
                .id(saleCommentPs.getId())
                .author(saleCommentPs.getAuthor())
                .contents(saleCommentPs.getContents())
                .createdAt(saleCommentPs.getCreatedAt())
                .updatedAt(saleCommentPs.getUpdatedAt())
                .build();
    }

    public UpdateSaleCommentResponse updateSaleComment(Long commentId, UpdateSaleCommentRequest updateCommentRequest) {
        SaleComment fetchedComment = saleCommentRepository.findById(commentId).
                orElseThrow(() -> new RuntimeException("commentId에 해당하는 댓글이 존재하지 않습니다."));

        SaleComment saleComment = updateCommentRequest.toEntity(fetchedComment);

        SaleComment saleCommentPs = saleCommentRepository.save(saleComment);

        return UpdateSaleCommentResponse.builder()
                .id(saleCommentPs.getId())
                .author(saleCommentPs.getAuthor())
                .contents(saleCommentPs.getContents())
                .createdAt(saleCommentPs.getCreatedAt())
                .updatedAt(saleCommentPs.getUpdatedAt())
                .build();
    }

    public FetchSaleCommentsResponse fetchSaleComments(Long postId, int pageNumber, int size) {
        // Sort 객체를 생성하여 정렬 기준을 설정합니다.
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        // 페이지 번호와 페이지 크기를 사용하여 PageRequest 객체를 생성합니다.
        PageRequest pageRequest = PageRequest.of(pageNumber, size, sort);

        Page<SaleComment> fetchedSaleComments = saleCommentRepository.findBySalePostId(postId, pageRequest);

        List<FetchedSaleComment> saleComments = fetchedSaleComments.get()
                .map(saleComment -> FetchedSaleComment.builder()
                        .id(saleComment.getId())
                        .author(saleComment.getAuthor())
                        .contents(saleComment.getContents())
                        .createdAt(saleComment.getCreatedAt())
                        .updatedAt(saleComment.getUpdatedAt())
                        .build())
                .toList();

        return FetchSaleCommentsResponse.builder()
                .posts(saleComments)
                .currentPage(fetchedSaleComments.getNumber())
                .totalPages(fetchedSaleComments.getTotalPages())
                .totalElements(fetchedSaleComments.getTotalElements())
                .build();
    }
}
