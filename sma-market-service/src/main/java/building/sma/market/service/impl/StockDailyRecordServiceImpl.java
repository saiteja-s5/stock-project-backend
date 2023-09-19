package building.sma.market.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import building.sma.market.dto.HistoricalQuote;
import building.sma.market.model.CompanyName;
import building.sma.market.model.Interval;
import building.sma.market.model.StockDailyRecord;
import building.sma.market.model.StockDailyRecordLastTradeSavedInfo;
import building.sma.market.repository.CompanyNameRepository;
import building.sma.market.repository.StockDailyRecordLastTradeSavedInfoRepository;
import building.sma.market.repository.StockDailyRecordRepository;
import building.sma.market.service.MarketService;
import building.sma.market.service.StockDailyRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@AllArgsConstructor
public class StockDailyRecordServiceImpl implements StockDailyRecordService {

    private final StockDailyRecordLastTradeSavedInfoRepository infoRepository;

    private final StockDailyRecordRepository recordRepository;

    private final CompanyNameRepository companyRepository;

    private final MarketService marketService;

    @Override
    public void saveRecords() {
	log.info("Starting the quote history fetch job at {}", LocalDateTime.now());
	final StopWatch stopWatch = new StopWatch();
	stopWatch.start();
	LocalDate today = LocalDate.now();
	List<CompanyName> companies = companyRepository.findAll();
	log.debug("Fetched all companies details");
	for (CompanyName company : companies) {
	    log.debug("Pre Post: Saving data of {}", company.getSymbol());
	    Optional<StockDailyRecordLastTradeSavedInfo> lastSavedInfo = infoRepository
		    .findByStockIdAndMarketId(company.getSymbol(), company.getMarket());
	    LocalDate lastSavedOn = lastSavedInfo.isPresent() ? lastSavedInfo.get().getLastSavedDate()
		    : LocalDate.of(2000, 1, 1);
	    log.debug("Get: Last updated date for {} is {}", company.getSymbol(), lastSavedOn);
	    log.debug("Get: Sending request to API for {}", company.getSymbol());
	    List<HistoricalQuote> history = marketService.getQuoteHistory(company.getMarket(), company.getSymbol(),
		    lastSavedOn.plusDays(1), today, Interval.DAILY);
	    log.debug("Get: Response recived for {} from API as List with size {}", company.getSymbol(),
		    history.size());
	    LocalDate latestFetchDate = history.stream().map(HistoricalQuote::getTradedDate).max(LocalDate::compareTo)
		    .orElse(lastSavedOn);
	    List<StockDailyRecord> recordsToSave = new ArrayList<>();
	    for (HistoricalQuote quote : history) {
		recordsToSave.add(new StockDailyRecord(quote, company.getMarket(), company.getSymbol()));
	    }
	    recordRepository.saveAll(recordsToSave);
	    log.debug("Post: Saved data of {}", company.getSymbol());
	    if (lastSavedInfo.isPresent()) {
		infoRepository.save(new StockDailyRecordLastTradeSavedInfo(lastSavedInfo.get().getLastSavedId(),
			company.getMarket(), company.getSymbol(), latestFetchDate));
	    } else {
		infoRepository.save(new StockDailyRecordLastTradeSavedInfo(company.getMarket(), company.getSymbol(),
			latestFetchDate));
	    }
	    log.debug("Post: Saved latest update date of {} as {}", company.getSymbol(), latestFetchDate);
	}
	stopWatch.stop();
	stopWatch.getTotalTimeMillis();
	log.info("Quote history fetch job finished at {}", LocalDateTime.now());
	log.debug("Quote history fetch job took {}ms", stopWatch.getTotalTimeMillis());
    }

}
