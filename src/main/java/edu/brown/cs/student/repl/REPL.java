package edu.brown.cs.student.repl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Creates a generic REPL that can support any generic REPL commands engineers add to the REPLCmdMap
 * class.
 *
 * @author Elizabeth Wu
 */
public class REPL {

  /**
   * Class constructor.
   */
  public REPL() { }

  /** errorOutput method prints out the specific error message if something goes wrong while the
   * repl runs.
   *
   * @param err - the specific error message to print
   * @return a String containing the error
   */
  public static String errorOutput(String err) {
    return ("ERROR: " + err + "\n");
  }

  /** repl method runs the REPL. */
  public void repl() {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      String query = reader.readLine();
      REPLCmdMap getCommands = new REPLCmdMap();
      Map<String, REPLCommand> cmdMap = getCommands.getCmdMap();

      while (query != null) {
        List<String> queryLs = new ArrayList<>();

        // matches on non-whitespace (including things in quotation marks with the marks included)
        Matcher m = Pattern.compile("(?:\"[^\"]*\"|[^\\s\"])+").matcher(query);
        while (m.find()) {
          queryLs.add(m.group());
        }

        if (!queryLs.isEmpty()) {
          String cmd = queryLs.get(0);
          // if the command exists in the hashmap of commands
          if (cmdMap.containsKey(cmd)) {
            System.out.print(cmdMap.get(cmd).commandExec(queryLs));
          } else {
            System.out.print(errorOutput("Invalid command"));
          }
        } else { // command was something like a new line
          System.out.print(errorOutput("Invalid command"));
        }
        query = reader.readLine();
      }
      reader.close();
    } catch (Exception e) {
      System.out.print(errorOutput(e.getMessage()));
      e.printStackTrace();
    }
  }
}
