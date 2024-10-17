package nl.gerimedica.assessment.csv.impl.record;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public record DataRecord(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id, String source,
                         String codeListCode, String code, String displayValue, String longDescription,
                         LocalDate fromDate, LocalDate toDate, String sortingPriority) {
}
