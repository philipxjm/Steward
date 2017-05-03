package edu.steward.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import com.google.common.collect.Iterators;

/**
 * Class to read in CSV data from file. Resulting class is mutable. Does not
 * verify that all lines are equal in number of columns.
 *
 * @author wpovell
 *
 */
public class CSVReader {
  private String[] header;
  private String[][] lines;
  private int columns;

  /**
   * Constructor for CSVReader given a filename.
   *
   * @param fn
   *          Filename to read in CSV data from.
   * @throws IOException
   *           Throws when there is an issue with file read.
   * @throws NoSuchElementException
   *           Throws when file is empty
   */
  public CSVReader(String fn) throws IOException, NoSuchElementException {
    this(Files.lines(Paths.get(fn)));
  }

  /**
   * Constructor for CSVReader given a stream of lines.
   *
   * @param s
   *          Stream of lines to read in.
   * @throws NoSuchElementException
   *           Throws when file is empty
   *
   */
  public CSVReader(Stream<String> s) throws NoSuchElementException {
    Iterator<String[]> it = s.map((line) -> line.split(",", -1)).iterator();
    header = it.next();
    columns = header.length;
    lines = Iterators.toArray(it, String[].class);
    // TODO: Should remove this?
    // if (!validate(lines)) {
    // throw new IllegalArgumentException();
    // }
  }

  private boolean validate(String[][] ls) {
    for (String[] line : ls) {
      if (line.length != columns) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns header of the loaded CSV file. Mutating the result of this will
   * mutate the header internally.
   *
   * @return String array of the CSV header, each index being a column name.
   */
  public String[] getHeader() {
    return header;
  }

  /**
   * Returns array of lines, each line a string array with an index for each
   * column. Mutating the result of this will mutate the lines held internally
   * in the class.
   *
   * @return 2d array of lines and columns of CSV data.
   */
  public String[][] getLines() {
    return lines;
  }
}
