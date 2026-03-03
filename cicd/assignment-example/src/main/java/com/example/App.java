package com.example;

/**
 * Minimal application for CI/CD assignment pipeline
 * (build, lint, Docker, scan).
 */
public final class App {

    private App() {
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(final String[] args) {
        System.out.println("Hello, CI/CD!");
    }
}
