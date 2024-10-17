package nl.gerimedica.assessment;

import nl.gerimedica.assessment.common.ResourceNotFoundException;
import nl.gerimedica.assessment.csv.api.DataRecordService;
import nl.gerimedica.assessment.csv.db.DataRepository;
import nl.gerimedica.assessment.csv.impl.record.DataRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
class DataRecordServiceTests {

	@Mock
	private DataRepository dataRepository;

	@InjectMocks
	private DataRecordService dataRecordService;

	private DataRecord dataRecord1;
	private DataRecord dataRecord2;

	@BeforeEach
	void setUp() {
		dataRecord1 = new DataRecord(1, "ZIB", "ZIB001", "271636001", "Polsslag regelmatig", "The long description is necessary", LocalDate.parse("01-01-2019"), LocalDate.parse("01-01-2019"), 1);
		dataRecord2 = new DataRecord(1, "ZIB", "ZIB002", "Type 5", "Zachte keutels met duidelijke randen", "", LocalDate.parse("01-01-2019"), null, 2);
	}


	@Test
	void testUploadData() throws Exception {
		File file = new File("src/test/resources/test.csv");

		dataRecordService.uploadData(file);
		verify(dataRepository, times(1)).saveAll(anyList());

		List<DataRecord> result = dataRecordService.fetchAllData();
		assertThat(result).isNotNull();
		assertThat(result).size().isEqualTo(6);
	}

	@Test
	void testFetchAllData() {
		when(dataRepository.findAll()).thenReturn(Arrays.asList(dataRecord1, dataRecord2));

		List<DataRecord> result = dataRecordService.fetchAllData();
		assertThat(result).isNotNull();
		assertThat(result).size().isEqualTo(6);
		verify(dataRepository, times(1)).findAll();
	}

	@Test
	void testFetchByCode() {
		when(dataRepository.findById("ZIB001")).thenReturn(Optional.of(dataRecord1));

		List<DataRecord> result = dataRecordService.fetchByCode("ZIB001");
		assertThat(result).isNotNull();
		assertThat(result).size().isEqualTo(3);
		verify(dataRepository, times(1)).findById("ZIB001");
	}

	@Test
	void testFetchByCode_ResourceNotFound() {
		when(dataRepository.findById("ZIB005")).thenReturn(null);

		assertThatThrownBy(() -> dataRecordService.fetchByCode("ZIB005"))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("No records found for code ZIB005.");
		verify(dataRepository, times(1)).findById("ZIB005");
	}

	@Test
	void testDeleteAllData() {
		dataRecordService.deleteAllData();
		verify(dataRepository, times(1)).deleteAll();
		List<DataRecord> dataRecords = dataRecordService.fetchAllData();
		assertThat(dataRecords).isEmpty();
	}
}
