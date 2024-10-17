package nl.gerimedica.assessment.csv.impl;

import nl.gerimedica.assessment.csv.api.DataRecordService;
import nl.gerimedica.assessment.csv.impl.record.DataRecord;

import java.io.File;
import java.util.List;

public class DataRecordFacade implements DataRecordService {

    @Override
    public void uploadData(File csvFile) {

    }

    @Override
    public List<DataRecord> fetchAllData() {
        return List.of();
    }

    @Override
    public List<DataRecord> fetchByCode(String code) {
        return List.of();
    }

    @Override
    public void deleteAllData() {

    }
}
