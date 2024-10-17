package nl.gerimedica.assessment.csv.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "data_record")
public class DataRecord {

    private String source;
    private String codeListCode;
    @Id
    private String code;
    private String displayValue;
    private String longDescription;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String sortingPriority;

    public DataRecord() {
    }

    public DataRecord(String source, String codeListCode, String code, String displayValue, String longDescription, LocalDate fromDate, LocalDate toDate, String sortingPriority) {
        this.source = source;
        this.codeListCode = codeListCode;
        this.code = code;
        this.displayValue = displayValue;
        this.longDescription = longDescription;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.sortingPriority = sortingPriority;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCodeListCode() {
        return codeListCode;
    }

    public void setCodeListCode(String codeListCode) {
        this.codeListCode = codeListCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getSortingPriority() {
        return sortingPriority;
    }

    public void setSortingPriority(String sourceSystem) {
        this.sortingPriority = sourceSystem;
    }
}
