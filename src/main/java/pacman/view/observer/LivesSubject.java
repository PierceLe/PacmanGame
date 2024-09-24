package pacman.view.observer;

public interface LivesSubject {
    void registerObserver(LivesObserver observer);
    void removeObserver(LivesObserver observer);
    void notifyObservers();
}
