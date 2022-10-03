package com.borishop;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootTest
class BorishopApplicationTests {

	@MockBean
	JPAQueryFactory jpaQueryFactory;

	@Test
	void contextLoads() {
	}

}
