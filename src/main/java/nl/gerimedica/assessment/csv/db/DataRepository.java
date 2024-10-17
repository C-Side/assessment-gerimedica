package nl.gerimedica.assessment.csv.db;

import nl.gerimedica.assessment.csv.impl.record.DataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<DataRecord, String> {

    List<DataRecord> findByCode(String code);
}
