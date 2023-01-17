import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.List;
import java.util.DoubleSummaryStatistics;


class Main {
  
  static Stream<String> fileToStream(String filename) {
    try {
      return Files.lines(Paths.get(filename));
    } catch (Throwable t) {
      t.printStackTrace();
      return Stream.empty();
    }
  }

  static ParseRecord stringToRecord(String[] string) {
      ParseRecord record = new ParseRecord(
        string[0],
        string[1],
        Double.parseDouble(string[2]),
        Double.parseDouble(string[3]),
        Double.parseDouble(string[4]),
        Double.parseDouble(string[5]),
        Double.parseDouble(string[6]),
        Long.parseLong(string[7]));
    return record;
  }

  static Double parseToDouble(String[] string) {
    return Double.parseDouble(string[6]);
  }

  static void todo0SeeData() {
    // Nothing to do here. This is just a sample for you.
    fileToStream("data.csv").limit(10).forEach(s -> System.out.println(s));
  }

  static void todo1ParseData() {
    // TODO-1: Convert the Stream<String> of lines from a file to a Stream<Record>
    // of some Record type you define, which should have a java.util.Date,
    // a String (ticker), doubles (open, high, low, close, adj close) and
    // a long (volume). Be sure to leave evidence that your code is working, e.g.,
    // some sort of output.
    /*
     * Return string pipeline
     * take stream pipleine
     */
    fileToStream("data.csv")
        .skip(1)
        .map(s -> s.split(","))
        .map(t -> stringToRecord(t))
        .limit(10)
        .forEach(u -> System.out.println(u));
  }

  static void todo2DescriptiveStatistics() {
    // TODO-2: Use the summaryStatistics() terminal Stream operation to learn more
    // about the Adj Close column. Take a close look at the output. Does it make
    // sense? Remember that "Adj Close" should be a price. For all future TODO
    // items,
    // you will want to filter the data to take care of this problem.
    DoubleSummaryStatistics result = fileToStream("data.csv")
        .skip(1)
        .map(s -> s.split(","))
        .map(t -> stringToRecord(t))
        .collect(Collectors.summarizingDouble(s -> s.adjClose()));
    System.out.println(result);
  }

  static void todo3StatisticsByTicker() {
    // TODO-3: In TODO-2, you got statistics for all the data combined. But it
    // should
    // really be kept separate by ticker, e.g., statistics for AAPL, statistics for
    // GOOG, etc. Group the Stream by ticker, and then process each individual
    // Stream
    // to get its own summary statistics.
    System.out.println("Apple:");
    DoubleSummaryStatistics AAPL = fileToStream("data.csv")
        .skip(1)
        .map(s -> s.split(","))
        .filter(s -> s[1].equals("AAPL"))
        .map(t -> stringToRecord(t))
        .collect(Collectors.summarizingDouble(s -> s.adjClose()));
    System.out.println(AAPL);
    System.out.println("Bitcoin:");
    DoubleSummaryStatistics BTC = fileToStream("data.csv")
        .skip(1)
        .map(s -> s.split(","))
        .filter(s -> s[1].equals("BTC"))
        .map(t -> stringToRecord(t))
        .collect(Collectors.summarizingDouble(s -> s.adjClose()));
    System.out.println(BTC);
    System.out.println("Google:");
    DoubleSummaryStatistics GOOG = fileToStream("data.csv")
        .skip(1)
        .map(s -> s.split(","))
        .filter(s -> s[1].equals("GOOG"))
        .map(t -> stringToRecord(t))
        .collect(Collectors.summarizingDouble(s -> s.adjClose()));
    System.out.println(GOOG);
    System.out.println("Microsoft:");
    DoubleSummaryStatistics MSFT = fileToStream("data.csv")
        .skip(1)
        .map(s -> s.split(","))
        .filter(s -> s[1].equals("MSFT"))
        .map(t -> stringToRecord(t))
        .collect(Collectors.summarizingDouble(s -> s.adjClose()));
    System.out.println(MSFT);
  }

  static void calcPercMove(List<Double> doub) {
    for (int i = 1; i < doub.size(); i++) {
      Double percChange = (doub.get(i) - doub.get(i - 1)) / doub.get(i - 1);
      System.out.println("Percent change from the day before is " + percChange);
    }
  }

  static void todo4CalculatePercentMove() {
    // TODO-4: The actual price of something is not nearly as important as how much
    // it goes up or down. So add a column to the data that computes the daily
    // percent move for each symbol and day. This is computed as
    // (today-prev)/|prev|.
    // You only need to compute the percent change for one column, namely Adj Close.
    // You should do this with a simple map function, but notice that this function
    // is NOT A PURE function. It will need to maintain state (e.g., to remember the
    // prev value). You may assume that the data is already sorted by date.
    System.out.println("Apple:");
    calcPercMove(fileToStream("data.csv")
        .skip(1)
        .map(s -> s.split(","))
        .filter(s -> s[1].equals("AAPL"))
        .map(t -> t[6])
        .map(s -> Double.parseDouble(s))
        .limit(10)
        .collect(Collectors.toList()));
    System.out.println("Bitcoin:");
    calcPercMove(fileToStream("data.csv")
        .skip(1)
        .map(s -> s.split(","))
        .filter(s -> s[1].equals("BTC"))
        .map(t -> t[6])
        .map(s -> Double.parseDouble(s))
        .limit(10)
        .collect(Collectors.toList()));
    System.out.println("Google:");
    calcPercMove(fileToStream("data.csv")
        .skip(1)
        .map(s -> s.split(","))
        .filter(s -> s[1].equals("GOOG"))
        .map(t -> t[6])
        .map(s -> Double.parseDouble(s))
        .limit(10)
        .collect(Collectors.toList()));
    System.out.println("Microsoft:");
    calcPercMove(fileToStream("data.csv")
        .skip(1)
        .map(s -> s.split(","))
        .filter(s -> s[1].equals("MSFT"))
        .map(t -> t[6])
        .map(s -> Double.parseDouble(s))
        .limit(10)
        .collect(Collectors.toList()));
  }

  public static void main(String[] args) {
    System.out.println("Todo 0: \n");
    todo0SeeData();

    System.out.println("Todo 1: \n");
    todo1ParseData();

    System.out.println("Todo 2: \n");
    todo2DescriptiveStatistics();

    System.out.println("Todo 3: \n");
    todo3StatisticsByTicker();

    System.out.println("Todo 4: \n");
    todo4CalculatePercentMove();
  }
}
