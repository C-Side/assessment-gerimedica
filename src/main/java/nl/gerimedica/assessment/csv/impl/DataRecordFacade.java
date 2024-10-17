package nl.gerimedica.assessment.csv.impl;

import io.micrometer.common.util.StringUtils;
import nl.gerimedica.assessment.common.ResourceNotFoundException;
import nl.gerimedica.assessment.csv.api.DataRecordService;
import nl.gerimedica.assessment.csv.db.DataRecordRepository;
import nl.gerimedica.assessment.csv.db.entity.DataRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataRecordFacade implements DataRecordService {

    @Autowired
    private DataRecordRepository dataRecordRepository;

    @Override
    public void uploadDataRecords(File csvFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            List<DataRecord> records = reader.lines().skip(1)
                    .map(line -> {
                        String[] fields = line.split(",");

                        LocalDate fromDate = generateLocalDateFromString(fields[5]);
                        LocalDate toDate = generateLocalDateFromString(fields[6]);

                        return new DataRecord(fields[0].replace("\"", ""),
                                fields[1].replace("\"", ""),
                                fields[2].replace("\"", ""),
                                fields[3].replace("\"", ""),
                                fields[4].replace("\"", ""),
                                fromDate,
                                toDate,
                                fields[7].replace("\"", ""));
                    })
                    .collect(Collectors.toList());

            dataRecordRepository.saveAll(records);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file", e);
        }
    }

    @Override
    public List<DataRecord> fetchDataRecords() {
        return dataRecordRepository.findAll();
    }

    @Override
    public DataRecord fetchDataRecordByCode(String code) throws ResourceNotFoundException {
        DataRecord dataRecordByCode = dataRecordRepository.findByCode(code);
        if (dataRecordByCode == null) {
            throw new ResourceNotFoundException("No record found for code " + code + ".");
        } else {
            return dataRecordByCode;
        }
    }

    @Override
    public void deleteAllDataRecords() {
        dataRecordRepository.deleteAll();
    }

    private LocalDate generateLocalDateFromString(String stringFromFile) {
        stringFromFile = stringFromFile.replace("\"", "");
        return StringUtils.isEmpty(stringFromFile) ? null : LocalDate.parse(stringFromFile, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
