package nl.gerimedica.assessment;

import nl.gerimedica.assessment.common.ResourceNotFoundException;
import nl.gerimedica.assessment.csv.db.DataRepository;
import nl.gerimedica.assessment.csv.impl.DataRecordFacade;
import nl.gerimedica.assessment.csv.impl.record.DataRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class DataRecordServiceTests {

	@Mock
	private DataRepository dataRepository;

	private static final File FILE = new File("src/test/resources/test.csv");
	@InjectMocks
	private DataRecordFacade dataRecordFacade;

	@BeforeEach
	void setUp() {
		dataRecordFacade.deleteAllData();
		dataRecordFacade.uploadData(FILE);
	}


	@Test
	void testUploadData() {
		List<DataRecord> result = dataRecordFacade.fetchAllData();
		assertThat(result).isNotNull();
		assertThat(result).size().isEqualTo(6);
	}

	@Test
	void testFetchAllData() {
		List<DataRecord> result = dataRecordFacade.fetchAllData();
		assertThat(result).isNotNull();
		assertThat(result).size().isEqualTo(6);
		verify(dataRepository, times(1)).findAll();
	}

	@Test
	void testFetchByCode() {
		List<DataRecord> result = dataRecordFacade.fetchByCode("ZIB001");
		assertThat(result).isNotNull();
		assertThat(result).size().isEqualTo(3);
	}

	@Test
	void testFetchByCode_ResourceNotFound() {
		assertThatThrownBy(() -> dataRecordFacade.fetchByCode("ZIB005"))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("No records found for code ZIB005.");
	}

	@Test
	void testDeleteAllData() {
		dataRecordFacade.deleteAllData();
		List<DataRecord> dataRecords = dataRecordFacade.fetchAllData();
		assertThat(dataRecords).isEmpty();
	}
}
