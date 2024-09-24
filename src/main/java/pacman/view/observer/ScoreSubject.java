package pacman.view.observer;

public interface ScoreSubject {
    void registerObserver(ScoreObserver scoreObserver);
    void removeObserver(ScoreObserver scoreObserver);
    void notifyObservers();
}
