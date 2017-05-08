package edu.steward.user;

/**
 * Created by mrobins on 5/7/17.
 */
public class LBscore {

    private Portfolio port;
    private Double score;

    public LBscore(Portfolio port, Double score) {
      this.port = port;
      this.score = score;
    }

    public Portfolio getPort() {
      return port;
    }

    public Double getScore() {
      return score;
    }
}
