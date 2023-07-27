package building.stockapp.service.impl;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import building.stockapp.model.HistoricalQuote;
import building.stockapp.model.Portfolio;
import building.stockapp.model.Stock;
import building.stockapp.repository.PortfolioRepository;
import building.stockapp.repository.StockRepository;
import building.stockapp.service.MarketService;
import building.stockapp.service.PortfolioService;
import building.stockapp.utility.Interval;
import building.stockapp.utility.Utility;

@Service
public class PortfolioServiceImpl implements PortfolioService {

	private PortfolioRepository portfolioRepository;
	private StockRepository stockRepository;
	private MarketService marketService;
	private static final Logger LOGGER = Logger.getLogger(PortfolioServiceImpl.class.getName());

	@Override
	public List<Portfolio> getPortfolioHistory() {
		LOGGER.log(Level.INFO, "Getting portfolio history");
		List<Portfolio> portfolioHistory = new ArrayList<>();
		Optional<Portfolio> latestRecord = portfolioRepository.findFirstByOrderByTradeDateDesc();
		LocalDate lastUpdatedOn = latestRecord.isPresent() ? latestRecord.get().getTradeDate()
				: Utility.PORTFOLIO_START_DATE;
		List<Stock> currentHoldings = stockRepository.findAll();
		for (LocalDate date = lastUpdatedOn; date.isBefore(LocalDate.now().plusDays(1)); date = date.plusDays(1)) {
			if (!date.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
				Double portfolioValue = 0.0;
				for (Stock stock : currentHoldings) {
					LOGGER.log(Level.INFO, "Getting quote info of {0} on " + date, stock.getStockName());
					HistoricalQuote quote = marketService
							.getHistory("NSE", stock.getStockName(), date, date, Interval.DAILY).get(0);
					portfolioValue += quote.getClose().doubleValue() * stock.getQuantity();
				}
				portfolioHistory.add(new Portfolio(date, BigDecimal.valueOf(portfolioValue), BigDecimal.valueOf(0.0),
						BigDecimal.valueOf(0.0)));
			}
		}
		portfolioRepository.saveAll(portfolioHistory);
		LOGGER.log(Level.INFO, "Portfolio history fetched sucessfully");
		return portfolioHistory;
	}

	@Autowired
	public void setPortfolioRepository(PortfolioRepository portfolioRepository) {
		this.portfolioRepository = portfolioRepository;
	}

	@Autowired
	public void setStockRepository(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	@Autowired
	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}

}
