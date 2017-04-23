package edu.steward.stock.api;

/**
 * Created by mrobins on 4/18/17.
 */
public final class AlphaVantageConstants {

  public enum APIKEY {
    APIKEY;

    @Override
    public String toString() {
      return "P192";
    }
  }

  public enum FUNCTION {
    TIME_SERIES_INTRADAY,
    TIME_SERIES_DAILY,
    TIME_SERIES_WEEKLY,
    TIME_SERIES_MONTHLY;

    @Override
    public String toString() {
      return super.toString();
    }
  }

  public enum INTERVAL {
    ONE_MIN,
    FIVE_MIN,
    FIFTEEN_MIN,
    THIRTY_MIN,
    SIXTY_MIN;

    @Override
    public String toString() {
      switch (this) {
        case ONE_MIN:
          return "1min";
        case FIVE_MIN:
          return "5min";
        case FIFTEEN_MIN:
          return "15min";
        case THIRTY_MIN:
          return "30min";
        case SIXTY_MIN:
          return "60min";
        default:
          return "";
      }
    }
  }

  public enum OUTPUT_SIZE {
    COMPACT,
    FULL;

    @Override
    public String toString() {
      switch (this) {
        case COMPACT:
          return "compact";
        case FULL:
          return "full";
        default:
          return "";
      }
    }
  }

}
