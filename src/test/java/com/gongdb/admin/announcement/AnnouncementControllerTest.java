package com.gongdb.admin.announcement;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gongdb.admin.announcement.dto.request.AnnouncementInputFormDto;
import com.gongdb.admin.announcement.dto.request.AnnouncementSequenceInputDto;
import com.gongdb.admin.announcement.dto.request.LanguageScoreInputDto;
import com.gongdb.admin.announcement.entity.Announcement;
import com.gongdb.admin.announcement.entity.AnnouncementSequence;
import com.gongdb.admin.announcement.service.AnnouncementCreationService;
import com.gongdb.admin.announcement.service.AnnouncementSequenceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
public class AnnouncementControllerTest {
    
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private AnnouncementCreationService announcementCreationService;
    @Autowired private AnnouncementSequenceService announcementSequenceService;

    private static final String ROOT_END_POINT = "/api";

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation).operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint())) 
            .build();
    }

    @Test
    public void getAnnouncementsTest() throws Exception {
        AnnouncementSequenceInputDto announcementSequenceInputDto =
            AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .link("link")
            .files(List.of()).build();
        AnnouncementSequence sequence =
            announcementSequenceService.create(announcementSequenceInputDto);

        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
            .certificates(List.of("certificate1", "certificates2"))
            .departments(List.of("department1", "department2"))
            .districtName("districtName")
            .headCount("0")
            .languageScores(getLanguageScoreInputDtoList(List.of("languageScore0", "languageScore2")))
            .notes(List.of("note1", "note2"))
            .positionName("positionName")
            .rank("rank")
            .recruitLevel("recruitLevel")
            .recruitType("recruitType")
            .subjects(List.of("subject1", "subject2"))
            .workingType("workingType").build();
        announcementCreationService.create(sequence.getId(), announcementInputFormDto);

        this.mockMvc
            .perform(
                get(ROOT_END_POINT + "/announcement")
                .param("page", "0")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "announcement-get-page",
                requestParameters(parameterWithName("page").description("조회할 페이지").optional()),
                responseFields(
                    fieldWithPath("content").description("공고 리스트"),
                    fieldWithPath("content[].id").description("공고 ID"),
                    fieldWithPath("content[].position.positionId").description("직군 ID"),
                    fieldWithPath("content[].position.positionName").description("직군 이름"),
                    fieldWithPath("content[].sequence.id").description("차수 ID"),
                    fieldWithPath("content[].sequence.company.id").description("회사 ID"),
                    fieldWithPath("content[].sequence.company.name").description("회사 이름"),
                    fieldWithPath("content[].sequence.sequence").description("차수 이름"),
                    fieldWithPath("content[].sequence.receiptStartTimestamp").description("접수 시작일"),
                    fieldWithPath("content[].sequence.receiptEndTimestamp").description("접수 종료일"),
                    fieldWithPath("content[].sequence.link").description("공고 링크"),
                    fieldWithPath("content[].certificates").description("자격증"),
                    fieldWithPath("content[].departments").description("학과"),
                    fieldWithPath("content[].subjects").description("과목"),
                    fieldWithPath("content[].subjects[].subjectId").description("과목 ID"),
                    fieldWithPath("content[].subjects[].subjectName").description("과목명"),
                    fieldWithPath("content[].languageScores").description("어학점수"),
                    fieldWithPath("content[].languageScores[].languageId").description("어학 ID"),
                    fieldWithPath("content[].languageScores[].languageName").description("어학 이름"),
                    fieldWithPath("content[].languageScores[].score").description("어학 자격 점수"),
                    fieldWithPath("content[].languageScores[].perfectScore").description("어학 최고 점수"),
                    fieldWithPath("content[].notes").description("기타사항"),
                    fieldWithPath("content[].recruitType").description("채용 유형"),
                    fieldWithPath("content[].recruitLevel").description("채용 수준"),
                    fieldWithPath("content[].workingType").description("근무 유형"),
                    fieldWithPath("content[].districtName").description("지역 이름"),
                    fieldWithPath("content[].headCount").description("채용 인원"),
                    fieldWithPath("content[].rank").description("직급"),
                    fieldWithPath("content[].createdTimestamp").description("생성된 시각"),
                    fieldWithPath("pageable").description("페이지 관련 정보"),
                    fieldWithPath("pageable.sort").description("페이지 정렬 정보"),
                    fieldWithPath("pageable.sort.sorted").description("페이지 정렬 여부"),
                    fieldWithPath("pageable.sort.unsorted").description("페이지 미정렬 여부"),
                    fieldWithPath("pageable.sort.empty").description("페이지가 비었는지 여부"),
                    fieldWithPath("pageable.offset").description("페이지 오프셋"),
                    fieldWithPath("pageable.pageSize").description("한 페이지 당 크기"),
                    fieldWithPath("pageable.pageNumber").description("현재 페이지"),
                    fieldWithPath("pageable.unpaged").description("페이징 미사용 여부"),
                    fieldWithPath("pageable.paged").description("페이징 사용 여부"),
                    fieldWithPath("last").description("마지막 페이지 여부"),
                    fieldWithPath("totalElements").description("모든 데이터의 개수"),
                    fieldWithPath("totalPages").description("총 페이지 수"),
                    fieldWithPath("size").description("한 페이지 당 크기"),
                    fieldWithPath("number").description("현재 페이지"),
                    fieldWithPath("sort.sorted").description("페이지 정렬 여부"),
                    fieldWithPath("sort.unsorted").description("페이지 미정렬 여부"),
                    fieldWithPath("sort.empty").description("페이지가 비었는지 여부"),
                    fieldWithPath("first").description("첫 페이지 여부"),
                    fieldWithPath("numberOfElements").description("모든 데이터의 개수"),
                    fieldWithPath("empty").description("페이지가 비었는지 여부")
                )
            ));
    }

    @Test
    public void getAnnouncementTest() throws Exception {
        AnnouncementSequenceInputDto announcementSequenceInputDto =
            AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .link("link")
            .files(List.of()).build();
        AnnouncementSequence sequence =
            announcementSequenceService.create(announcementSequenceInputDto);

        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
            .certificates(List.of("certificate1", "certificates2"))
            .departments(List.of("department1", "department2"))
            .districtName("districtName")
            .headCount("0")
            .languageScores(getLanguageScoreInputDtoList(List.of("languageScore0", "languageScore2")))
            .notes(List.of("note1", "note2"))
            .positionName("positionName")
            .rank("rank")
            .recruitLevel("recruitLevel")
            .recruitType("recruitType")
            .subjects(List.of("subject1", "subject2"))
            .workingType("workingType").build();
        Announcement announcement = announcementCreationService.create(sequence.getId(), announcementInputFormDto);

        this.mockMvc
            .perform(
                RestDocumentationRequestBuilders.get(
                    ROOT_END_POINT + "/announcement/{id}", announcement.getId()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sequence.company.name").value("companyName"))
            .andDo(document(
                "announcement-get",
                pathParameters(parameterWithName("id").description("공고 ID")),
                responseFields(
                    fieldWithPath("id").description("공고 ID"),
                    fieldWithPath("position.positionId").description("직군 ID"),
                    fieldWithPath("position.positionName").description("직군 이름"),
                    fieldWithPath("sequence.id").description("차수 ID"),
                    fieldWithPath("sequence.company.id").description("회사 ID"),
                    fieldWithPath("sequence.company.name").description("회사 이름"),
                    fieldWithPath("sequence.sequence").description("차수 이름"),
                    fieldWithPath("sequence.receiptStartTimestamp").description("접수 시작일"),
                    fieldWithPath("sequence.receiptEndTimestamp").description("접수 종료일"),
                    fieldWithPath("sequence.link").description("공고 링크"),
                    fieldWithPath("certificates").description("자격증"),
                    fieldWithPath("departments").description("학과"),
                    fieldWithPath("subjects").description("과목"),
                    fieldWithPath("subjects[].subjectId").description("과목 ID"),
                    fieldWithPath("subjects[].subjectName").description("과목명"),
                    fieldWithPath("languageScores").description("어학점수"),
                    fieldWithPath("languageScores[].languageId").description("어학 ID"),
                    fieldWithPath("languageScores[].languageName").description("어학 이름"),
                    fieldWithPath("languageScores[].score").description("어학 자격 점수"),
                    fieldWithPath("languageScores[].perfectScore").description("어학 최고 점수"),
                    fieldWithPath("notes").description("기타사항"),
                    fieldWithPath("recruitType").description("채용 유형"),
                    fieldWithPath("recruitLevel").description("채용 수준"),
                    fieldWithPath("workingType").description("근무 유형"),
                    fieldWithPath("districtName").description("지역 이름"),
                    fieldWithPath("headCount").description("채용 인원"),
                    fieldWithPath("rank").description("직급"),
                    fieldWithPath("createdTimestamp").description("생성된 시각")
                )
            ));
    }

    @Test
    public void createAnnouncementTest() throws Exception {
        AnnouncementSequenceInputDto announcementSequenceInputDto =
            AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .link("link")
            .files(List.of()).build();
        AnnouncementSequence sequence =
            announcementSequenceService.create(announcementSequenceInputDto);

        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
            .certificates(List.of("certificate1", "certificates2"))
            .departments(List.of("department1", "department2"))
            .districtName("districtName")
            .headCount("0")
            .languageScores(getLanguageScoreInputDtoList(List.of("languageScore0", "languageScore2")))
            .notes(List.of("note1", "note2"))
            .positionName("positionName")
            .rank("rank")
            .recruitLevel("recruitLevel")
            .recruitType("recruitType")
            .subjects(List.of("subject1", "subject2"))
            .workingType("workingType").build();

        String content = convertToNonNullValueJsonString(announcementInputFormDto);

        this.mockMvc
            .perform(
                RestDocumentationRequestBuilders.post(
                    ROOT_END_POINT + "/sequence/{sequenceId}/announcement", sequence.getId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "announcement-create",
                pathParameters(parameterWithName("sequenceId").description("차수 ID")),
                requestFields(
                    fieldWithPath("positionName").description("직무명"),
                    fieldWithPath("recruitType").description("채용구분"),
                    fieldWithPath("recruitLevel").description("채용수준"),
                    fieldWithPath("workingType").description("근무형태"),
                    fieldWithPath("districtName").description("지역명"),
                    fieldWithPath("headCount").description("인원수"),
                    fieldWithPath("rank").description("직급"),
                    fieldWithPath("certificates").description("자격증").optional(),
                    fieldWithPath("departments").description("학과").optional(),
                    fieldWithPath("subjects").description("과목").optional(),
                    fieldWithPath("languageScores").description("어학점수").optional(),
                    fieldWithPath("languageScores[].name").description("어학명"),
                    fieldWithPath("languageScores[].score").description("어학 자격 점수"),
                    fieldWithPath("languageScores[].perfectScore").description("어학 최고 점수").optional(),
                    fieldWithPath("notes").description("기타사항").optional()
                ),
                responseFields(
                    fieldWithPath("timestamp").description("응답 시각"),
                    fieldWithPath("message").description("응답 메시지")
                )
            ));
    }

    @Test
    public void createBadRequestAnnouncementTest() throws Exception {
        AnnouncementSequenceInputDto announcementSequenceInputDto =
            AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .link("link")
            .files(List.of()).build();
        AnnouncementSequence sequence =
            announcementSequenceService.create(announcementSequenceInputDto);

        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
            .certificates(List.of("certificate1", "certificates2"))
            .departments(List.of("department1", "department2"))
            .districtName("districtName")
            .headCount("0")
            .languageScores(getLanguageScoreInputDtoList(List.of("languageScore0", "languageScore2")))
            .notes(List.of("note1", "note2"))
            // .positionName("positionName")  // suppose bad request
            .rank("rank")
            .recruitLevel("recruitLevel")
            .recruitType("recruitType")
            .subjects(List.of("subject1", "subject2"))
            .workingType("workingType").build();

        String content = convertToNonNullValueJsonString(announcementInputFormDto);
        
        this.mockMvc
            .perform(
                post(ROOT_END_POINT + "/sequence/{sequenceId}/announcement", sequence.getId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getRecentAnnouncementTest() throws Exception {
        AnnouncementSequenceInputDto announcementSequenceInputDto =
            AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .link("link")
            .files(List.of()).build();
        AnnouncementSequence sequence =
            announcementSequenceService.create(announcementSequenceInputDto);

        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
            .certificates(List.of("certificate1", "certificates2"))
            .departments(List.of("department1", "department2"))
            .districtName("districtName")
            .headCount("0")
            .languageScores(getLanguageScoreInputDtoList(List.of("languageScore0", "languageScore2")))
            .notes(List.of("note1", "note2"))
            .positionName("positionName")
            .rank("rank")
            .recruitLevel("recruitLevel")
            .recruitType("recruitType")
            .subjects(List.of("subject1", "subject2"))
            .workingType("workingType").build();
        announcementCreationService.create(sequence.getId(), announcementInputFormDto);

        this.mockMvc
            .perform(get(ROOT_END_POINT + "/announcement/recent"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sequence.company.name").value("companyName"))
            .andDo(document(
                "announcement-recent",
                responseFields(
                    fieldWithPath("id").description("공고 ID"),
                    fieldWithPath("position.positionId").description("직군 ID"),
                    fieldWithPath("position.positionName").description("직군 이름"),
                    fieldWithPath("sequence.id").description("차수 ID"),
                    fieldWithPath("sequence.company.id").description("회사 ID"),
                    fieldWithPath("sequence.company.name").description("회사 이름"),
                    fieldWithPath("sequence.sequence").description("차수 이름"),
                    fieldWithPath("sequence.receiptStartTimestamp").description("접수 시작일"),
                    fieldWithPath("sequence.receiptEndTimestamp").description("접수 종료일"),
                    fieldWithPath("sequence.link").description("공고 링크"),
                    fieldWithPath("certificates").description("자격증"),
                    fieldWithPath("departments").description("학과"),
                    fieldWithPath("subjects").description("과목"),
                    fieldWithPath("subjects[].subjectId").description("과목 ID"),
                    fieldWithPath("subjects[].subjectName").description("과목명"),
                    fieldWithPath("languageScores").description("어학점수"),
                    fieldWithPath("languageScores[].languageId").description("어학 ID"),
                    fieldWithPath("languageScores[].languageName").description("어학 이름"),
                    fieldWithPath("languageScores[].score").description("어학 자격 점수"),
                    fieldWithPath("languageScores[].perfectScore").description("어학 최고 점수"),
                    fieldWithPath("notes").description("기타사항"),
                    fieldWithPath("recruitType").description("채용 유형"),
                    fieldWithPath("recruitLevel").description("채용 수준"),
                    fieldWithPath("workingType").description("근무 유형"),
                    fieldWithPath("districtName").description("지역 이름"),
                    fieldWithPath("headCount").description("채용 인원"),
                    fieldWithPath("rank").description("직급"),
                    fieldWithPath("createdTimestamp").description("생성된 시각")
                )
            ));
    }

    @Test
    public void updateAnnouncementTest() throws Exception {
        AnnouncementSequenceInputDto announcementSequenceInputDto =
            AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .link("link")
            .files(List.of()).build();
        AnnouncementSequence sequence =
            announcementSequenceService.create(announcementSequenceInputDto);

        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
            .certificates(List.of("certificate1", "certificates2"))
            .departments(List.of("department1", "department2"))
            .districtName("districtName")
            .headCount("0")
            .languageScores(getLanguageScoreInputDtoList(List.of("languageScore0", "languageScore2")))
            .notes(List.of("note1", "note2"))
            .positionName("positionName")
            .rank("rank")
            .recruitLevel("recruitLevel")
            .recruitType("recruitType")
            .subjects(List.of("subject1", "subject2"))
            .workingType("workingType").build();
        Announcement announcement =
            announcementCreationService.create(sequence.getId(), announcementInputFormDto);

        String content = convertToNonNullValueJsonString(announcementInputFormDto);

        this.mockMvc
            .perform(
                RestDocumentationRequestBuilders.put(
                    ROOT_END_POINT + "/announcement/{id}", announcement.getId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "announcement-update",
                pathParameters(parameterWithName("id").description("공고 ID")),
                requestFields(
                    fieldWithPath("positionName").description("직무명"),
                    fieldWithPath("recruitType").description("채용구분"),
                    fieldWithPath("recruitLevel").description("채용수준"),
                    fieldWithPath("workingType").description("근무형태"),
                    fieldWithPath("districtName").description("지역명"),
                    fieldWithPath("headCount").description("인원수"),
                    fieldWithPath("rank").description("직급"),
                    fieldWithPath("certificates").description("자격증").optional(),
                    fieldWithPath("departments").description("학과").optional(),
                    fieldWithPath("subjects").description("과목").optional(),
                    fieldWithPath("languageScores").description("어학점수").optional(),
                    fieldWithPath("languageScores[].name").description("어학명"),
                    fieldWithPath("languageScores[].score").description("어학 자격 점수"),
                    fieldWithPath("languageScores[].perfectScore").description("어학 최고 점수").optional(),
                    fieldWithPath("notes").description("기타사항").optional()
                ),
                responseFields(
                    fieldWithPath("timestamp").description("응답 시각"),
                    fieldWithPath("message").description("응답 메시지")
                )
            ));
    }

    @Test
    public void deleteAnnouncementTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "files", "test.txt", MediaType.TEXT_PLAIN_VALUE, "test".getBytes());
        AnnouncementSequenceInputDto announcementSequenceInputDto =
            AnnouncementSequenceInputDto.builder()
            .companyName("companyName")
            .sequence("sequence")
            .receiptStartTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .receiptEndTimestamp(LocalDateTime.of(2021, 7, 19, 0, 0))
            .link("link")
            .files(List.of(file)).build();
        AnnouncementSequence sequence =
            announcementSequenceService.create(announcementSequenceInputDto);

        AnnouncementInputFormDto announcementInputFormDto = 
            AnnouncementInputFormDto.builder()
            .certificates(List.of("certificate1", "certificates2"))
            .departments(List.of("department1", "department2"))
            .districtName("districtName")
            .headCount("0")
            .languageScores(getLanguageScoreInputDtoList(List.of("languageScore0", "languageScore2")))
            .notes(List.of("note1", "note2"))
            .positionName("positionName")
            .rank("rank")
            .recruitLevel("recruitLevel")
            .recruitType("recruitType")
            .subjects(List.of("subject1", "subject2"))
            .workingType("workingType").build();

        Announcement announcement =
            announcementCreationService.create(sequence.getId(), announcementInputFormDto);

        this.mockMvc
            .perform(
                RestDocumentationRequestBuilders.delete(
                    ROOT_END_POINT + "/announcement/{id}", announcement.getId()))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "announcement-delete",
                pathParameters(parameterWithName("id").description("공고 ID")),
                responseFields(
                    fieldWithPath("timestamp").description("응답 시각"),
                    fieldWithPath("message").description("응답 메시지")
                )
            ));
    }

    private String convertToNonNullValueJsonString(Object value) throws JsonProcessingException {
        return objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(value);
    }

    private List<LanguageScoreInputDto> getLanguageScoreInputDtoList(List<String> languageScores) {
        return languageScores.stream()
            .map(each -> LanguageScoreInputDto.builder()
                .name(each)
                .score(each + " score")
                .perfectScore(each + "perfectScore")
                .build())
            .collect(Collectors.toList());
    }
}
