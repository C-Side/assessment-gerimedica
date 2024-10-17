package nl.gerimedica.assessment.csv.impl;

import io.micrometer.common.util.StringUtils;
import nl.gerimedica.assessment.common.ResourceNotFoundException;
import nl.gerimedica.assessment.csv.api.DataRecordService;
import nl.gerimedica.assessment.csv.db.DataRepository;
import nl.gerimedica.assessment.csv.impl.record.DataRecord;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


public class DataRecordFacade implements DataRecordService {

    @Autowired
    private DataRepository dataRepository;

    @Override
    public void uploadData(File csvFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            List<DataRecord> records = reader.lines().skip(1)
                    .map(line -> {
                        String[] fields = line.split(",");

                        LocalDate fromDate = generateLocalDateFromString(fields[5]);
                        LocalDate toDate = generateLocalDateFromString(fields[6]);

                        return new DataRecord(0,
                                fields[0],
                                fields[1],
                                fields[2],
                                fields[3],
                                fields[4],
                                fromDate,
                                toDate,
                                fields[7]);
                    })
                    .collect(Collectors.toList());

            dataRepository.saveAll(records);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file", e);
        }
    }

    @Override
    public List<DataRecord> fetchAllData() {
        return dataRepository.findAll();
    }

    @Override
    public List<DataRecord> fetchByCode(String code) throws ResourceNotFoundException {
        List<DataRecord> dataRecordsByCode = dataRepository.findByCode(code);
        if (dataRecordsByCode.isEmpty()) {
            throw new ResourceNotFoundException("No records found for code " + code + ".");
        } else {
            return dataRecordsByCode;
        }
    }

    @Override
    public void deleteAllData() {
        dataRepository.deleteAll();
    }

    private LocalDate generateLocalDateFromString(String stringFromFile) {
        stringFromFile = stringFromFile.replace("\"", "");
        return StringUtils.isEmpty(stringFromFile) ? null : LocalDate.parse(stringFromFile, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
