import java.util.Date;

public record ParseRecord(String date, String ticker, double open, double high, double low, double close, double adjClose, long volume){}