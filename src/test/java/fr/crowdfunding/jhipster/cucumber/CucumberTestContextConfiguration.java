package fr.crowdfunding.jhipster.cucumber;

import fr.crowdfunding.jhipster.CrowdFundingJHipsterApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = CrowdFundingJHipsterApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
