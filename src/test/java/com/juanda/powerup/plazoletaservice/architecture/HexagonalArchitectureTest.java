package com.juanda.powerup.plazoletaservice.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

class HexagonalArchitectureTest {
    private static final JavaClasses CLASSES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.juanda.powerup.plazoletaservice");

    @Test
    void shouldKeepDomainIndependent() {
        noClasses().that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "..application..", "..infrastructure..", "org.springframework..",
                        "jakarta..", "feign..", "lombok..")
                .check(CLASSES);
    }

    @Test
    void shouldKeepApplicationIndependentOfInfrastructure() {
        noClasses().that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAPackage("..infrastructure..")
                .check(CLASSES);
    }

    @Test
    void shouldKeepHandlersCommandsAndMappersIndependentOfSpring() {
        noClasses().that().resideInAnyPackage(
                        "..application.handler..", "..application.command..", "..application.mapper..")
                .should().dependOnClassesThat().resideInAPackage("org.springframework..")
                .check(CLASSES);
    }

    @Test
    void shouldHaveNoCyclesBetweenLayers() {
        slices().matching("com.juanda.powerup.plazoletaservice.(*)..").should().beFreeOfCycles().check(CLASSES);
    }
}
