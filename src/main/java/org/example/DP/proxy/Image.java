package org.example.DP.proxy;

/**
 * Interface commune pour le sujet réel et le proxy.
 * Indispensable pour que le client puisse interagir de la même manière avec les deux (principe de substitution).
 */
public interface Image {
    void display();
}
