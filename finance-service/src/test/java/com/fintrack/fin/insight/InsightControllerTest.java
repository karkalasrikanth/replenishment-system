package com.fintrack.fin.insight;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InsightController.class)
@AutoConfigureMockMvc(addFilters = false)
class InsightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InsightService insightService;


    @Test
    void testCreate() throws Exception {
        Insight insight = new Insight();
        insight.setId(1L);
        insight.setUserId(100L);
        insight.setQuery("test-query");
        insight.setResponse("test-response");
        insight.setCreatedTs(LocalDateTime.now());

        when(insightService.saveInsight(insight.getUserId(), insight.getQuery(), insight.getResponse())).thenReturn(insight);

        mockMvc.perform(post("/api/insights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "userId": "100",
                                    "query": "test-query",
                                    "response": "test-response"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(100L));

        verify(insightService).saveInsight(insight.getUserId(), insight.getQuery(), insight.getResponse());

    }

    @Test
    void testGetByUser() throws Exception {
        Insight insight = new Insight();
        insight.setId(1L);
        insight.setUserId(100L);
        insight.setQuery("test-query");
        insight.setResponse("test-response");
        insight.setCreatedTs(LocalDateTime.now());

        when(insightService.getUserInsights(insight.getUserId())).thenReturn(List.of(insight));

        mockMvc.perform(get("/api/insights/{userId}", insight.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L));
        verify(insightService).getUserInsights(insight.getUserId());

    }
}