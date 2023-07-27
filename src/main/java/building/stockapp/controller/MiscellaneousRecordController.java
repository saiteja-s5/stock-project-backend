package building.stockapp.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import building.stockapp.model.MiscellaneousRecord;
import building.stockapp.service.MiscellaneousRecordService;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/misc-records")
public class MiscellaneousRecordController {

	private MiscellaneousRecordService miscRecordService;
	private static final Logger LOGGER = Logger.getLogger(MiscellaneousRecordController.class.getName());

	public MiscellaneousRecordController(MiscellaneousRecordService miscRecordService) {
		this.miscRecordService = miscRecordService;
	}

	@GetMapping()
	public ResponseEntity<MiscellaneousRecord> getMiscellaneousRecords() {
		LOGGER.log(Level.INFO, "Request received to get table update info");
		return new ResponseEntity<>(miscRecordService.getMiscellaneousRecords(), HttpStatus.OK);
	}

	@PutMapping()
	public ResponseEntity<MiscellaneousRecord> updateMiscellaneousRecord(
			@RequestBody @Valid MiscellaneousRecord miscellaneousRecord) {
		LOGGER.log(Level.INFO, "Request received to update table info");
		return new ResponseEntity<>(miscRecordService.updateMiscellaneousRecord(miscellaneousRecord), HttpStatus.OK);
	}
}
