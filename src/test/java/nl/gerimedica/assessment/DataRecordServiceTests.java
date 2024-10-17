package nl.gerimedica.assessment;

import nl.gerimedica.assessment.common.ResourceNotFoundException;
import nl.gerimedica.assessment.csv.db.entity.DataRecord;
import nl.gerimedica.assessment.csv.impl.DataRecordFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class DataRecordServiceTests {

	private static final File FILE = new File("src/test/resources/test.csv");

	@Autowired
	private DataRecordFacade dataRecordFacade;

	@BeforeEach
	void setUp() {
		dataRecordFacade.deleteAllDataRecords();
		dataRecordFacade.uploadDataRecords(FILE);
	}

	@Test
	void testFetchDataRecords() {
		List<DataRecord> result = dataRecordFacade.fetchDataRecords();
		assertThat(result).isNotNull();
		assertThat(result).size().isEqualTo(6);
	}

	@Test
	void testFetchDataRecordByCode() {
		DataRecord result = dataRecordFacade.fetchDataRecordByCode("271636001");
		assertThat(result).isNotNull();
		assertThat(result.getCode()).isEqualTo("271636001");
		assertThat(result.getLongDescription()).isEqualTo("The long description is necessary");
		assertThat(result.getToDate()).isNull();
	}

	@Test
	void testFetchDataRecordByCode_ResourceNotFound() {
		assertThatThrownBy(() -> dataRecordFacade.fetchDataRecordByCode("1234"))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("No record found for code 1234.");
	}

	@Test
	void testDeleteAllDataRecords() {
		dataRecordFacade.deleteAllDataRecords();
		List<DataRecord> dataRecords = dataRecordFacade.fetchDataRecords();
		assertThat(dataRecords).isEmpty();
	}
}
