package com.clinica_hipocrates.appointment_service.model;

import jakarta.persistence.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModelsTest {

    @Test
    void allEntitiesShouldHaveProperNaming() {
        List<Class<?>> entities = List.of(Appointment.class, CachedUser.class, Feedback.class, Schedule.class, Survey.class);
        List<Class<?>> tables = List.of(Appointment.class, CachedUser.class, Feedback.class, Schedule.class, Survey.class);

        for (Class<?> entityClass : entities) {
            // Check @Entity annotation
            assertTrue(entityClass.isAnnotationPresent(Entity.class), entityClass.getSimpleName() + " must be annotated with @Entity");

            // Check @Table annotation
            if (tables.contains(entityClass)) {
                assertTrue(entityClass.isAnnotationPresent(Table.class),entityClass.getSimpleName() + " must be annotated with @Table");
                Table table = entityClass.getAnnotation(Table.class);
                assertTrue(table.name().matches("[a-z_]+"), "Table name '" + table.name() + "' must be lowercase with underscores");
            }

            // Check @Column annotations
            for (Field field : entityClass.getDeclaredFields()) {
                // Transients
                if (Modifier.isStatic(field.getModifiers()) || field.isAnnotationPresent(Transient.class)) { continue; }

                // ManyToMany -- Check JoinTables names
                if (field.isAnnotationPresent(ManyToMany.class)) {
                    JoinTable joinTable = field.getAnnotation(JoinTable.class);
                    assertNotNull(joinTable, entityClass.getSimpleName() + "." + field.getName() + " must be annotated with @JoinTable");

                    assertTrue(joinTable.name().matches("[a-z_]+"),
                            "JoinTable name '" + joinTable.name() + "' must be lowercase with underscores");

                    // Optional: check joinColumns and inverseJoinColumns
                    for (JoinColumn jc : joinTable.joinColumns()) {
                        assertTrue(jc.name().matches("[a-z_]+"),
                                "JoinColumn name '" + jc.name() + "' must be lowercase with underscores");
                    }

                    for (JoinColumn ijc : joinTable.inverseJoinColumns()) {
                        assertTrue(ijc.name().matches("[a-z_]+"),
                                "InverseJoinColumn name '" + ijc.name() + "' must be lowercase with underscores");
                    }
                }

                // Skip Joins, OneToMany, ManyToOne and ManyToMany from Column checking
                if (field.isAnnotationPresent(JoinColumn.class) || field.isAnnotationPresent(OneToMany.class) ||
                    field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(ManyToMany.class)) { continue; }

                // Check @Column(name=) presence and format
                Column column = field.getAnnotation(Column.class);
                assertNotNull(column, entityClass.getSimpleName() + "." + field.getName() + " must have @Column(name = ...)");
                assertTrue(column.name().matches("[a-z_]+"),
                        entityClass.getSimpleName() + "." + field.getName() + ": Column name '" + column.name() + "' must be lowercase with underscores");
            }
        }
    }

    private void verifyEqualsAndHashCode(Class<?> checkClass) {
        EqualsVerifier.forClass(checkClass)
                .withRedefinedSuperclass()
                .verify();
    }

    // Models
    @Test
    void Appointment_equalsAndHashCode_shouldBeValid() {
        verifyEqualsAndHashCode(Appointment.class);
    }

    @Test
    void CachedUser_equalsAndHashCode_shouldBeValid() {
        verifyEqualsAndHashCode(CachedUser.class);
    }
    @Test
    void Feedback_equalsAndHashCode_shouldBeValid() {
        verifyEqualsAndHashCode(Feedback.class);
    }

    @Test
    void Schedule_equalsAndHashCode_shouldBeValid() {
        verifyEqualsAndHashCode(Schedule.class);
    }

    @Test
    void Survey_equalsAndHashCode_shouldBeValid() {
        verifyEqualsAndHashCode(Survey.class);
    }
}
