package pacman.observer.gameStateObserver;


public interface GameStateSubject {
    void registerObserver(GameStateObserver observer);
    void removeObserver(GameStateObserver observer);
    void notifyObservers();
}
