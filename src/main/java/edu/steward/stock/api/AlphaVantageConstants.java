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

  //  TODO: ADD a list of all valid stock ticker symbols
  public enum SYMBOL {
    FOXA, FOX, ATVI, ADBE, AKAM, ALXN, GOOGL, GOOG, AMZN, AAL, AMGN, ADI,
    AAPL, AMAT, ADSK, ADP, AVGO, BIDU, BBBY, BIIB, BMRN, BRCM, CHRW, CA, CELG,
    CERN, CHTR, CHKP, CSCO, CTXS, CTSH, CMCSK, CMCSA, COST, DISCA, DISCK, DISH,
    DLTR, EBAY, EA, EXPD, ESRX, FB, FAST, FISV, GRMN, GILD, HSIC, ILMN, INCY,
    INTC, INTU, ISRG, JD, KLAC, GMCR, KHC, LRCX, LBTYA, LBTYK, QVCA, LILA, LILAK,
    LMCK, LMCA, LVNTA, LLTC, MAR, MAT, MU, MSFT, MDLZ, MNST, MYL, NTAP, NFLX,
    NVDA, NXPI, ORLY, PCAR, PAYX, QCOM, REGN, ROST, SNDK, SBAC, STX, SIRI, SWKS,
    SPLS, SBUX, SRCL, SYMC, TSLA, TXN, PCLN, TSCO, TRIP, VRSK, VRTX, VIAB, VIP,
    VOD, WBA, WDC, WFM, WYNN, XLNX, YHOO
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
