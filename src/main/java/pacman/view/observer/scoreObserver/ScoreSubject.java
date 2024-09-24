package pacman.view.observer.scoreObserver;

public interface ScoreSubject {
    void registerObserver(ScoreObserver scoreObserver);
    void removeObserver(ScoreObserver scoreObserver);
    void notifyObservers();
}
