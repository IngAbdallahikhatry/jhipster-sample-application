package mr.iscae;

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
            .importPackages("mr.iscae");

        noClasses()
            .that()
            .resideInAnyPackage("mr.iscae.service..")
            .or()
            .resideInAnyPackage("mr.iscae.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..mr.iscae.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
