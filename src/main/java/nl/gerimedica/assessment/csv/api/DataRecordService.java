package nl.gerimedica.assessment.csv.api;

import nl.gerimedica.assessment.csv.impl.record.DataRecord;

import java.io.File;
import java.util.List;

public interface DataRecordService {

    public void uploadData(File csvFile);
    public List<DataRecord> fetchAllData();
    public List<DataRecord> fetchByCode(String code);
    public void deleteAllData();
}
