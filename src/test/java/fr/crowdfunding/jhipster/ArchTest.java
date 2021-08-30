package fr.crowdfunding.jhipster;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("fr.crowdfunding.jhipster");

        noClasses()
            .that()
            .resideInAnyPackage("fr.crowdfunding.jhipster.service..")
            .or()
            .resideInAnyPackage("fr.crowdfunding.jhipster.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..fr.crowdfunding.jhipster.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
