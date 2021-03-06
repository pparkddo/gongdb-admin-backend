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
        MockMultipartFile file = new MockMultipartFile(
            "files",
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "File content~".getBytes());

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
                requestParameters(parameterWithName("page").description("????????? ?????????").optional()),
                responseFields(
                    fieldWithPath("content").description("?????? ?????????"),
                    fieldWithPath("content[].id").description("?????? ID"),
                    fieldWithPath("content[].position.positionId").description("?????? ID"),
                    fieldWithPath("content[].position.positionName").description("?????? ??????"),
                    fieldWithPath("content[].sequence.id").description("?????? ID"),
                    fieldWithPath("content[].sequence.company.id").description("?????? ID"),
                    fieldWithPath("content[].sequence.company.name").description("?????? ??????"),
                    fieldWithPath("content[].sequence.sequence").description("?????? ??????"),
                    fieldWithPath("content[].sequence.receiptStartTimestamp").description("?????? ?????????"),
                    fieldWithPath("content[].sequence.receiptEndTimestamp").description("?????? ?????????"),
                    fieldWithPath("content[].sequence.link").description("?????? ??????"),
                    fieldWithPath("content[].sequence.files[]").description("?????? ?????? ??????"),
                    fieldWithPath("content[].sequence.files[].id").description("?????? ?????? ?????? ID"),
                    fieldWithPath("content[].sequence.files[].fileName").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("content[].sequence.createdTimestamp").description("?????? ?????? ??????"),
                    fieldWithPath("content[].certificates").description("?????????"),
                    fieldWithPath("content[].departments").description("??????"),
                    fieldWithPath("content[].subjects").description("??????"),
                    fieldWithPath("content[].subjects[].subjectId").description("?????? ID"),
                    fieldWithPath("content[].subjects[].subjectName").description("?????????"),
                    fieldWithPath("content[].languageScores").description("????????????"),
                    fieldWithPath("content[].languageScores[].languageId").description("?????? ID"),
                    fieldWithPath("content[].languageScores[].languageName").description("?????? ??????"),
                    fieldWithPath("content[].languageScores[].score").description("?????? ?????? ??????"),
                    fieldWithPath("content[].languageScores[].perfectScore").description("?????? ?????? ??????"),
                    fieldWithPath("content[].notes").description("????????????"),
                    fieldWithPath("content[].recruitType").description("?????? ??????"),
                    fieldWithPath("content[].recruitLevel").description("?????? ??????"),
                    fieldWithPath("content[].workingType").description("?????? ??????"),
                    fieldWithPath("content[].districtName").description("?????? ??????"),
                    fieldWithPath("content[].headCount").description("?????? ??????"),
                    fieldWithPath("content[].rank").description("??????"),
                    fieldWithPath("content[].createdTimestamp").description("????????? ??????"),
                    fieldWithPath("pageable").description("????????? ?????? ??????"),
                    fieldWithPath("pageable.sort").description("????????? ?????? ??????"),
                    fieldWithPath("pageable.sort.sorted").description("????????? ?????? ??????"),
                    fieldWithPath("pageable.sort.unsorted").description("????????? ????????? ??????"),
                    fieldWithPath("pageable.sort.empty").description("???????????? ???????????? ??????"),
                    fieldWithPath("pageable.offset").description("????????? ?????????"),
                    fieldWithPath("pageable.pageSize").description("??? ????????? ??? ??????"),
                    fieldWithPath("pageable.pageNumber").description("?????? ?????????"),
                    fieldWithPath("pageable.unpaged").description("????????? ????????? ??????"),
                    fieldWithPath("pageable.paged").description("????????? ?????? ??????"),
                    fieldWithPath("last").description("????????? ????????? ??????"),
                    fieldWithPath("totalElements").description("?????? ???????????? ??????"),
                    fieldWithPath("totalPages").description("??? ????????? ???"),
                    fieldWithPath("size").description("??? ????????? ??? ??????"),
                    fieldWithPath("number").description("?????? ?????????"),
                    fieldWithPath("sort.sorted").description("????????? ?????? ??????"),
                    fieldWithPath("sort.unsorted").description("????????? ????????? ??????"),
                    fieldWithPath("sort.empty").description("???????????? ???????????? ??????"),
                    fieldWithPath("first").description("??? ????????? ??????"),
                    fieldWithPath("numberOfElements").description("?????? ???????????? ??????"),
                    fieldWithPath("empty").description("???????????? ???????????? ??????")
                )
            ));
    }

    @Test
    public void getAnnouncementTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "files",
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "File content~".getBytes());

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
                pathParameters(parameterWithName("id").description("?????? ID")),
                responseFields(
                    fieldWithPath("id").description("?????? ID"),
                    fieldWithPath("position.positionId").description("?????? ID"),
                    fieldWithPath("position.positionName").description("?????? ??????"),
                    fieldWithPath("sequence.id").description("?????? ID"),
                    fieldWithPath("sequence.company.id").description("?????? ID"),
                    fieldWithPath("sequence.company.name").description("?????? ??????"),
                    fieldWithPath("sequence.sequence").description("?????? ??????"),
                    fieldWithPath("sequence.receiptStartTimestamp").description("?????? ?????????"),
                    fieldWithPath("sequence.receiptEndTimestamp").description("?????? ?????????"),
                    fieldWithPath("sequence.link").description("?????? ??????"),
                    fieldWithPath("sequence.files[]").description("?????? ?????? ??????"),
                    fieldWithPath("sequence.files[].id").description("?????? ?????? ?????? ID"),
                    fieldWithPath("sequence.files[].fileName").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("sequence.createdTimestamp").description("?????? ?????? ??????"),
                    fieldWithPath("certificates").description("?????????"),
                    fieldWithPath("departments").description("??????"),
                    fieldWithPath("subjects").description("??????"),
                    fieldWithPath("subjects[].subjectId").description("?????? ID"),
                    fieldWithPath("subjects[].subjectName").description("?????????"),
                    fieldWithPath("languageScores").description("????????????"),
                    fieldWithPath("languageScores[].languageId").description("?????? ID"),
                    fieldWithPath("languageScores[].languageName").description("?????? ??????"),
                    fieldWithPath("languageScores[].score").description("?????? ?????? ??????"),
                    fieldWithPath("languageScores[].perfectScore").description("?????? ?????? ??????"),
                    fieldWithPath("notes").description("????????????"),
                    fieldWithPath("recruitType").description("?????? ??????"),
                    fieldWithPath("recruitLevel").description("?????? ??????"),
                    fieldWithPath("workingType").description("?????? ??????"),
                    fieldWithPath("districtName").description("?????? ??????"),
                    fieldWithPath("headCount").description("?????? ??????"),
                    fieldWithPath("rank").description("??????"),
                    fieldWithPath("createdTimestamp").description("????????? ??????")
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
                pathParameters(parameterWithName("sequenceId").description("?????? ID")),
                requestFields(
                    fieldWithPath("positionName").description("?????????"),
                    fieldWithPath("recruitType").description("????????????"),
                    fieldWithPath("recruitLevel").description("????????????"),
                    fieldWithPath("workingType").description("????????????"),
                    fieldWithPath("districtName").description("?????????"),
                    fieldWithPath("headCount").description("?????????"),
                    fieldWithPath("rank").description("??????"),
                    fieldWithPath("certificates").description("?????????").optional(),
                    fieldWithPath("departments").description("??????").optional(),
                    fieldWithPath("subjects").description("??????").optional(),
                    fieldWithPath("languageScores").description("????????????").optional(),
                    fieldWithPath("languageScores[].name").description("?????????"),
                    fieldWithPath("languageScores[].score").description("?????? ?????? ??????"),
                    fieldWithPath("languageScores[].perfectScore").description("?????? ?????? ??????").optional(),
                    fieldWithPath("notes").description("????????????").optional()
                ),
                responseFields(
                    fieldWithPath("timestamp").description("?????? ??????"),
                    fieldWithPath("message").description("?????? ?????????")
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
        MockMultipartFile file = new MockMultipartFile(
            "files",
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "File content~".getBytes());

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
        announcementCreationService.create(sequence.getId(), announcementInputFormDto);

        this.mockMvc
            .perform(get(ROOT_END_POINT + "/announcement/recent"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sequence.company.name").value("companyName"))
            .andDo(document(
                "announcement-recent",
                responseFields(
                    fieldWithPath("id").description("?????? ID"),
                    fieldWithPath("position.positionId").description("?????? ID"),
                    fieldWithPath("position.positionName").description("?????? ??????"),
                    fieldWithPath("sequence.id").description("?????? ID"),
                    fieldWithPath("sequence.company.id").description("?????? ID"),
                    fieldWithPath("sequence.company.name").description("?????? ??????"),
                    fieldWithPath("sequence.sequence").description("?????? ??????"),
                    fieldWithPath("sequence.receiptStartTimestamp").description("?????? ?????????"),
                    fieldWithPath("sequence.receiptEndTimestamp").description("?????? ?????????"),
                    fieldWithPath("sequence.link").description("?????? ??????"),
                    fieldWithPath("sequence.files[]").description("?????? ?????? ??????"),
                    fieldWithPath("sequence.files[].id").description("?????? ?????? ?????? ID"),
                    fieldWithPath("sequence.files[].fileName").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("sequence.createdTimestamp").description("?????? ?????? ??????"),
                    fieldWithPath("certificates").description("?????????"),
                    fieldWithPath("departments").description("??????"),
                    fieldWithPath("subjects").description("??????"),
                    fieldWithPath("subjects[].subjectId").description("?????? ID"),
                    fieldWithPath("subjects[].subjectName").description("?????????"),
                    fieldWithPath("languageScores").description("????????????"),
                    fieldWithPath("languageScores[].languageId").description("?????? ID"),
                    fieldWithPath("languageScores[].languageName").description("?????? ??????"),
                    fieldWithPath("languageScores[].score").description("?????? ?????? ??????"),
                    fieldWithPath("languageScores[].perfectScore").description("?????? ?????? ??????"),
                    fieldWithPath("notes").description("????????????"),
                    fieldWithPath("recruitType").description("?????? ??????"),
                    fieldWithPath("recruitLevel").description("?????? ??????"),
                    fieldWithPath("workingType").description("?????? ??????"),
                    fieldWithPath("districtName").description("?????? ??????"),
                    fieldWithPath("headCount").description("?????? ??????"),
                    fieldWithPath("rank").description("??????"),
                    fieldWithPath("createdTimestamp").description("????????? ??????")
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
                pathParameters(parameterWithName("id").description("?????? ID")),
                requestFields(
                    fieldWithPath("positionName").description("?????????"),
                    fieldWithPath("recruitType").description("????????????"),
                    fieldWithPath("recruitLevel").description("????????????"),
                    fieldWithPath("workingType").description("????????????"),
                    fieldWithPath("districtName").description("?????????"),
                    fieldWithPath("headCount").description("?????????"),
                    fieldWithPath("rank").description("??????"),
                    fieldWithPath("certificates").description("?????????").optional(),
                    fieldWithPath("departments").description("??????").optional(),
                    fieldWithPath("subjects").description("??????").optional(),
                    fieldWithPath("languageScores").description("????????????").optional(),
                    fieldWithPath("languageScores[].name").description("?????????"),
                    fieldWithPath("languageScores[].score").description("?????? ?????? ??????"),
                    fieldWithPath("languageScores[].perfectScore").description("?????? ?????? ??????").optional(),
                    fieldWithPath("notes").description("????????????").optional()
                ),
                responseFields(
                    fieldWithPath("timestamp").description("?????? ??????"),
                    fieldWithPath("message").description("?????? ?????????")
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
                pathParameters(parameterWithName("id").description("?????? ID")),
                responseFields(
                    fieldWithPath("timestamp").description("?????? ??????"),
                    fieldWithPath("message").description("?????? ?????????")
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
