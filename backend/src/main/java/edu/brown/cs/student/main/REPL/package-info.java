/**
 * This package contains classes and interfaces that pertain to
 * the GenericREPL class. This REPL can take any command, which
 * will point to an object that is implementing the REPLable
 * interface. The REPLable implementer will have its .execute method
 * be called by the REPL, it will perform is computation, and then
 * control will be given back to the REPL. Sending the REPL EOF will
 * terminate the REPL's execution, and return control to the caller.
 * @since 1.0
 * @author masonburke
 * @version 1.0
 */
package edu.brown.cs.student.main.REPL;
