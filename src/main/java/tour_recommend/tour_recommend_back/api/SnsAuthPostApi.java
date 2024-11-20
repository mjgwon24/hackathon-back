package tour_recommend.tour_recommend_back.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tour_recommend.tour_recommend_back.dto.ResponseDto;
import tour_recommend.tour_recommend_back.dto.ResponseDto.Status;
import tour_recommend.tour_recommend_back.dto.SnsAuthPostDto.CreateSnsAuthPostRequest;
import tour_recommend.tour_recommend_back.dto.SnsAuthPostDto.CreateSnsAuthPostResponse;
import tour_recommend.tour_recommend_back.service.SnsAuthPostService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sns-auth")
public class SnsAuthPostApi {
    private final SnsAuthPostService snsAuthPostService;

    @PostMapping("/posts")
    public ResponseEntity<ResponseDto<CreateSnsAuthPostResponse>> createSnsAuthPost(@RequestBody CreateSnsAuthPostRequest createSnsAuthPostRequest) {

        CreateSnsAuthPostResponse createSnsAuthPostResponse = snsAuthPostService.createSnsAuthPost(createSnsAuthPostRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "SNS 인증 게시글 등록 성공", createSnsAuthPostResponse),
                HttpStatus.OK
        );
    }
}
