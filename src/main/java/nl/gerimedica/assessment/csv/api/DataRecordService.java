package nl.gerimedica.assessment.csv.api;

import nl.gerimedica.assessment.csv.impl.record.DataRecord;

import java.io.File;
import java.util.List;

public interface DataRecordService {

    void uploadData(File csvFile);

    List<DataRecord> fetchAllData();

    List<DataRecord> fetchByCode(String code);

    void deleteAllData();
}
