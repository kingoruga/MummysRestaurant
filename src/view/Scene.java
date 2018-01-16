package view;

import model.Connector;

import java.util.List;
import java.util.Scanner;

/**
 * Comparable to an http request, on any scene transition
 * the inherited class should set up everything it needs
 * to display choices/do actions, any resource gathering
 * would be like server-side requesting from a database
 */
public abstract class Scene {

    static Scanner scanner;

    static Connector connector;

    static {
        scanner = new Scanner(System.in);
        connector = new Connector();
    }

    /**
     * Do all the work necessary to transition to the next scene,
     * then return the next scene
     */
    public abstract Scene transitionNext();

    /**
     * The input loop requested in a transition
     */
    public boolean requestTransition = false;

    /**
     * Get input to get information from one scene to the next
     */
    public abstract void process();

    /**
     * The selected choice option, possibly from process
     */
    String selectedChoice;

    /**
     * Attempt to retrieve a choice given an index
     */
    static <T> T matchInputWithChoice(String input, List<T> choices) {

        // Parse and match with the given selection
        try {
            int index = Integer.parseInt(input);
            if (index >= 0 && index < choices.size())
                return choices.get(index);
        }

        // Invalid input, null result
        catch (Exception e) {
            return null;
        }

        return null;
    }
}
