package edu.brown.cs.student.main.REPL;

import java.util.List;

/**
 * an interface for classes wishing to add commands to a GenericREPL object.
 */
public interface REPLable {
    /**
     * Returns a description of the class' REPL command functionality.
     * @return a String describing the REPL command's functionality
     */
    String getDescription();

    /**
     * A method called by the REPL when the appropriate command is invoked.
     * @param args the arguments necessary for the REPL command's invocation
     *             (to be parsed and converted within)
     */
    void execute(List<String> args);
}
