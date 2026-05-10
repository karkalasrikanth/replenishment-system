package com.fintrack.fin.budget;

import com.fintrack.fin.IntegrationTest;
import com.fintrack.fin.config.FlywayTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {"spring.flyway.enabled=true", "spring.flyway.locations=classpath:db/migration", "spring.jpa.hibernate.ddl-auto=none"})
@Import(FlywayTestConfig.class)
@Sql(scripts = "/sql/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class BudgetRepositoryTest extends IntegrationTest {

    @Autowired
    private BudgetRepository budgetRepository;

    @Test
    void testFindByUserId() {

        List<Budget> budgets = budgetRepository.findByUserId(1L);
        assertNotNull(budgets);
        assertEquals(3, budgets.size());
        assertEquals(1L, budgets.get(0).getUserId());
    }

}