package org.yourssuproject.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.yourssuproject.domain.memos.Memos;
import org.yourssuproject.domain.memos.MemosRepository;
import org.yourssuproject.web.dto.MemosSaveRequestDto;
import org.yourssuproject.web.dto.MemosResponseDto;
import org.yourssuproject.web.dto.MemosUpdateRequestDto;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemosApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemosRepository memosRepository;

    @AfterEach
    public void cleanup() throws Exception { memosRepository.deleteAll(); }

    @Test
    public void Memos_POST_Save_Memo() throws Exception {
        String title = "test Title";
        String text = "test Text";

        MemosSaveRequestDto requestDto = MemosSaveRequestDto.builder()
                    .title(title)
                    .text(text)
                    .build();

        String url = "http://localhost:" + port + "/memos";

        ResponseEntity<MemosResponseDto> responseEntity = restTemplate.postForEntity(url, requestDto, MemosResponseDto.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getText()).isEqualTo(text);
    }

    @Test
    public void Memos_PUT_Update_Memo() throws Exception {
        Memos savedMemos = memosRepository.save(Memos.builder()
                .title("title")
                .text("text")
                .build());

        Long updatedId = savedMemos.getId();
        String expectedTitle = "title";
        String expectedText = "text2";

        MemosUpdateRequestDto requestDto = MemosUpdateRequestDto.builder()
                .title(expectedTitle)
                .text(expectedText)
                .build();

        String url = "http://localhost:" + port + "/memos/" + updatedId;

        HttpEntity<MemosUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<MemosResponseDto> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, MemosResponseDto.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getText()).isEqualTo(expectedText);

    }

    @Test
    public void Memos_PUT_Update_Memo_BAD_REQUEST_ID() throws Exception {
        Memos savedMemos = memosRepository.save(Memos.builder()
                .title("title")
                .text("text")
                .build());

        Long updatedId = savedMemos.getId();
        Long invalidID = Long.getLong("13");

        String expectedTitle = "title";
        String expectedText = "";

        MemosUpdateRequestDto requestDto = MemosUpdateRequestDto.builder()
                .title(expectedTitle)
                .text(expectedText)
                .build();

        String url = "http://localhost:" + port + "/memos/" + invalidID;

        HttpEntity<MemosUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<MemosResponseDto> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, MemosResponseDto.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void Memos_DELETE_Delete_Memo() throws Exception {
        Memos savedMemos = memosRepository.save(Memos.builder()
                .title("title")
                .text("text")
                .build());
        Long memoId = savedMemos.getId();

        String url = "http://localhost:" + port + "/memos/" + memoId;

        ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    @Test
    public void Memos_GET_Get_Detail_Memo() throws Exception {
        Memos savedMemos = memosRepository.save(Memos.builder()
                .title("title")
                .text("text")
                .build());
        Long memoId = savedMemos.getId();

        String url = "http://localhost:" + port + "/memos/" + memoId;

        ResponseEntity<MemosResponseDto> responseEntity = restTemplate.getForEntity(url, MemosResponseDto.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(responseEntity.getBody().getText()).isEqualTo(savedMemos.getText());
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(savedMemos.getTitle());
    }

//    @Test
//    public void Memos_Pagination_Default() throws Exception {
//        Memos memo1 = memosRepository.save(Memos.builder()
//                .title("title")
//                .text("text")
//                .build());
//        Memos memo2 = memosRepository.save(Memos.builder()
//                .title("title")
//                .text("text")
//                .build());
//        Memos memo3 = memosRepository.save(Memos.builder()
//                .title("title")
//                .text("text")
//                .build());
//        Memos memo4 = memosRepository.save(Memos.builder()
//                .title("title")
//                .text("text")
//                .build());
//        Memos memo5 = memosRepository.save(Memos.builder()
//                .title("title")
//                .text("text")
//                .build());
//        Memos memo6 = memosRepository.save(Memos.builder()
//                .title("title")
//                .text("text")
//                .build());
//
//        String url = "http://localhost:" + port + "/memos";
//
//        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody().size()).isEqualTo(5);
//    }

//    @Test
//    public void Memos_Pagination() throws Exception {
//        Memos memo1 = memosRepository.save(Memos.builder()
//                .title("title")
//                .text("text")
//                .build());
//        Memos memo2 = memosRepository.save(Memos.builder()
//                .title("title")
//                .text("text")
//                .build());
//        Memos memo3 = memosRepository.save(Memos.builder()
//                .title("title")
//                .text("text")
//                .build());
//        Memos memo4 = memosRepository.save(Memos.builder()
//                .title("title")
//                .text("text")
//                .build());
//        Memos memo5 = memosRepository.save(Memos.builder()
//                .title("title")
//                .text("text")
//                .build());
//        Memos memo6 = memosRepository.save(Memos.builder()
//                .title("title")
//                .text("text")
//                .build());
//
//        String url = "http://localhost:" + port + "/memos?page=" + 1;
//
//        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody().size()).isEqualTo(1);
//    }

    @Test
    public void Memos_GET_Search_Memos() throws Exception {
        LocalDate targetDate = LocalDate.now();
        String stringTargetDate = targetDate.toString();

        Memos memo1 = memosRepository.save(Memos.builder()
                .title("title")
                .text("text")
                .build());
        Memos memo2 = memosRepository.save(Memos.builder()
                .title("title")
                .text("text")
                .build());
        Memos memo3 = memosRepository.save(Memos.builder()
                .title("title")
                .text("text")
                .build());

        String url = "http://localhost:" + port + "/memos" + "?date=" + stringTargetDate;

        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        System.out.println(responseEntity.getBody().size());
    }

}
