package nl.gerimedica.assessment.csv.db;

import nl.gerimedica.assessment.csv.db.entity.DataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRecordRepository extends JpaRepository<DataRecord, Long> {

    DataRecord findByCode(String code);
}
