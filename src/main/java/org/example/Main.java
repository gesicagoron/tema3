package org.example;

import Presentation.Controller;

import javax.swing.*;

/**
 * The main class of the application.
 */
public class Main {
    /**
     * The entry point of the application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Controller controller = new Controller();
            }
        });
    }
}