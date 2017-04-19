package edu.steward.stock;

/**
 * Created by mrobins on 4/16/17.
 */
public interface Fundamental<T extends Fundamental> {

  public Double getValue();

  public int getTime();
}
