package building.stockapp.service.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import building.stockapp.exception.MiscellaneousRecordNotExistException;
import building.stockapp.model.MiscellaneousRecord;
import building.stockapp.repository.MiscellaneousRecordRepository;
import building.stockapp.service.MiscellaneousRecordService;
import building.stockapp.utility.Utility;

@Service
public class MiscellaneousRecordServiceImpl implements MiscellaneousRecordService {

	private MiscellaneousRecordRepository miscRecordRepository;
	private static final Logger LOGGER = Logger.getLogger(MiscellaneousRecordServiceImpl.class.getName());

	public MiscellaneousRecordServiceImpl(MiscellaneousRecordRepository miscRecordRepository) {
		this.miscRecordRepository = miscRecordRepository;
	}

	@Override
	public MiscellaneousRecord getMiscellaneousRecords() {
		LOGGER.log(Level.INFO, "Getting table update info");
		MiscellaneousRecord savedMiscRecord = miscRecordRepository.findById(Utility.MISC_TABLE_PRIMARY_KEY)
				.orElseThrow(MiscellaneousRecordNotExistException::new);
		LOGGER.log(Level.INFO, "Retrieved table update details sucessfully");
		return savedMiscRecord;
	}

	@Override
	public MiscellaneousRecord updateMiscellaneousRecord(MiscellaneousRecord miscellaneousRecord) {
		LOGGER.log(Level.INFO, "Updating table record");
		MiscellaneousRecord miscRecord = getMiscellaneousRecords();
		miscRecord.setCashAvailableForInvesting(miscellaneousRecord.getCashAvailableForInvesting());
		miscRecord.setStockTableUpdatedOn(miscellaneousRecord.getStockTableUpdatedOn());
		miscRecord.setMutualFundTableUpdatedOn(miscellaneousRecord.getMutualFundTableUpdatedOn());
		miscRecord.setFundTableUpdatedOn(miscellaneousRecord.getFundTableUpdatedOn());
		miscRecord.setDividendTableUpdatedOn(miscellaneousRecord.getDividendTableUpdatedOn());
		miscRecord.setProfitLossTableUpdatedOn(miscellaneousRecord.getProfitLossTableUpdatedOn());
		miscRecord.setGoldTableUpdatedOn(miscellaneousRecord.getGoldTableUpdatedOn());
		MiscellaneousRecord savedRecord = miscRecordRepository.save(miscRecord);
		LOGGER.log(Level.INFO, "Table updated sucessfully");
		return savedRecord;
	}

}
