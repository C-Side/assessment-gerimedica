package nl.gerimedica.assessment.csv.rest;

import nl.gerimedica.assessment.csv.api.DataRecordService;
import nl.gerimedica.assessment.csv.db.entity.DataRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/datarecords")
public class DataRecordController {

    @Autowired
    private DataRecordService dataRecordService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadData(@RequestParam("file") File file) {
        dataRecordService.uploadDataRecords(file);
        return ResponseEntity.ok("Data uploaded successfully");
    }

    @GetMapping
    public ResponseEntity<List<DataRecord>> getAllDataRecords() {
        List<DataRecord> records = dataRecordService.fetchDataRecords();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Object> getDataRecordByCode(@PathVariable String code) {
        DataRecord record = dataRecordService.fetchDataRecordByCode(code);
        return ResponseEntity.ok(record);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllDataRecords() {
        dataRecordService.deleteAllDataRecords();
        return ResponseEntity.ok("All data deleted successfully");
    }
}
