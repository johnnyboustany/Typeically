package edu.brown.cs.student.main.REPL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * a class for Read-Evaluate-Print-Loop objects, featuring the ability to:
 *  - add commands
 *  - remove commands
 *  - list commands
 *  - run commands.
 *  in order for a class to add a command, it must implement REPLable,
 *      which ensures that it has an execute() method, allowing it to be
 *      called within the REPL
 */
public class GenericREPL {

    /**
     * the means of storing a command linked to a name.
     */
    private final Map<String, REPLable> commandsMap;

    /**
     * constructor method for a new GenericREPL,
     *  takes no arguments and creates a new empty map for commands.
     */
    public GenericREPL() {
        this.commandsMap = new HashMap<>();
    }

    /**
     * a wrapper for HashMap.put() to avoid direct manipulation of internal
     * structure (note also that this implementation does not allow
     * replacement without deletion).
     * @param commandName the command name to be associated with a
     *                    REPLable-implementing Object
     * @param executor the REPLable-implementing Object
     * @throws IllegalArgumentException if attempting to add a duplicate command
     */
    public void addCommand(final String commandName, final REPLable executor)
            throws IllegalArgumentException {
        if (!this.commandsMap.containsKey(commandName)
                && !commandName.equals("")) {
            this.commandsMap.put(commandName, executor);
        } else {
            System.out.println("ERROR: attempt to add duplicate"
                    + "/empty command.");
            throw new IllegalArgumentException();
        }
    }

    /**
     * A wrapper for the HashMap.remove() method,
     *  to avoid direct manipulation of internal structure.
     * @param key a String name of the command to remove
     * @return the REPLable object removed, or null if not present in map
     */
    public REPLable removeCommand(final String key) {
        return this.commandsMap.remove(key);
    }

    /**
     * Lists the commands currently available in the REPL's command database.
     */
    public void listCommands() {
        // iterate through commands and print using REPLable's getDescription()
        for (String key : this.commandsMap.keySet()) {
            String cmdName = this.commandsMap.get(key).getDescription();
            System.out.println(key + ": " + cmdName);
        }
    }

    /**
     * Runs the REPL, accepting input from System.in.
     * @throws IOException BufferedReader.readline() possibly
     *                      throws IOException upon error
     */
    public void runREPL() throws IOException {
        // new BufferedReader instance to get terminal input
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // begin REPL by awaiting a line of input
        String line;
        while ((line = reader.readLine()) != null) {
            // tokenize the input, trimming whitespace
            String[] tokens = line.trim().split("\\s+");

            // if we have the command, execute, otherwise loop
            REPLable executor = this.commandsMap.get(tokens[0]);
            if (executor != null) {
                executor.execute(Arrays.asList(tokens));
            } else {
                System.out.println("ERROR: command not found.");
            }
        }
        // done reading, close the input stream reader
        reader.close();
    }
}
