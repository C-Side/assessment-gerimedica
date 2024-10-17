package nl.gerimedica.assessment.csv.impl;

import io.micrometer.common.util.StringUtils;
import nl.gerimedica.assessment.common.FileParsingException;
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
    public void uploadDataRecords(File csvFile) throws RuntimeException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            List<DataRecord> records = reader.lines().skip(1)
                    .map(line -> {
                        String[] fields = line.split(",");

                        LocalDate fromDate = generateLocalDateFromString(fields[5]);
                        LocalDate toDate = generateLocalDateFromString(fields[6]);

                        return new DataRecord(cleanField(fields[0]),
                                cleanField(fields[1]),
                                cleanField(fields[2]),
                                cleanField(fields[3]),
                                cleanField(fields[4]),
                                fromDate,
                                toDate,
                                cleanField(fields[7])
                        );
                    })
                    .collect(Collectors.toList());

            dataRecordRepository.saveAll(records);
        } catch (Exception e) {
            throw new FileParsingException("Failed to parse CSV file", e);
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

    private String cleanField(String dataRecordField) {
        return dataRecordField.replace("\"", "");
    }

    private LocalDate generateLocalDateFromString(String stringFromFile) {
        stringFromFile = stringFromFile.replace("\"", "");
        return StringUtils.isEmpty(stringFromFile) ? null : LocalDate.parse(stringFromFile, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
