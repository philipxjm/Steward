package edu.steward.Sentiment;

/**
 * Created by mrobins on 4/16/17.
 */
public final class Sentiment {

  /**
   * Private constructor for Sentiment utility class.
   * TODO: Merge with class SentimentAnalysis
   */
  private Sentiment() {
  }

  /**
   * Gets the sentimental value of a word for a given time frame
   * @param data A string value which will have its sentiment value analyzed.
   * @param days The integer value specifying how many days to go back from
   *             the current date to generate sentimental analysis.
   * @return An integer is returned that represents the sentimental value
   * of the inputted data in the specified amount of days.
   */
  public synchronized int getSentiment(String data, int days) {
    /*
    TODO: Return sentiment value generated from API.
    TODO: Put in a timeout.
    TODO: Make thread safe.
     */
    return 0;
  }

}
