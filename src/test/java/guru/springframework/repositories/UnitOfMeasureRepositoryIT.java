package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByUom() {
        Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findByUom("Cup");

        assertEquals("Cup", uom.get().getUom());
    }

    @Test
    void findByTeaspoon() {
        Optional<UnitOfMeasure> uom1 = unitOfMeasureRepository.findByUom("Teaspoon");
        assertEquals("Teaspoon", uom1.get().getUom());
    }
}