package com.fintrack.fin.budget;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BudgetController.class)
@AutoConfigureMockMvc(addFilters = false)
class BudgetControllerTest {

    @MockitoBean
    private BudgetService budgetService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCheck() throws Exception {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("userId", "10");
        requestParams.add("year", "2026");
        requestParams.add("month", "5");
        BudgetAlert budgetAlert = new BudgetAlert("shoping", BigDecimal.valueOf(100.0), BigDecimal.valueOf(120.50));

        List<BudgetAlert> budgetAlerts = List.of(budgetAlert);

        when(budgetService.checkBudget(anyLong(), any(YearMonth.class))).thenReturn(budgetAlerts);
        mockMvc.perform(get("/api/budgets/check").queryParams(requestParams))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
        verify(budgetService).checkBudget(anyLong(), any(YearMonth.class));
    }

}