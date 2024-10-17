package nl.gerimedica.assessment.csv.impl.record;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public record DataRecord(@Id long id, String source, String codeListCode, String code, String displayValue, String longDescription, LocalDate fromDate, LocalDate toDate, String sortingPriority) {
}
