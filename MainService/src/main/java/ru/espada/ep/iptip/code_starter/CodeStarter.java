package ru.espada.ep.iptip.code_starter;

public interface CodeStarter {

    void start();
    void stop();
    String getOutput() throws InterruptedException;

}
