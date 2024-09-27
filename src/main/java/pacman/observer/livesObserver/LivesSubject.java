package pacman.observer.livesObserver;

public interface LivesSubject {
    void registerObserver(LivesObserver observer);
    void removeObserver(LivesObserver observer);
    void notifyObservers();
}
