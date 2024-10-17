package nl.gerimedica.assessment.csv.api;

import nl.gerimedica.assessment.csv.db.entity.DataRecord;

import java.io.File;
import java.util.List;

public interface DataRecordService {

    void uploadDataRecords(File csvFile);

    List<DataRecord> fetchDataRecords();

    DataRecord fetchDataRecordByCode(String code);

    void deleteAllDataRecords();
}
