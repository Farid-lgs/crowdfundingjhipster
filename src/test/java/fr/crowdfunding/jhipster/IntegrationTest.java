package fr.crowdfunding.jhipster;

import fr.crowdfunding.jhipster.CrowdFundingJHipsterApp;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = CrowdFundingJHipsterApp.class)
public @interface IntegrationTest {
}
