package pacman.observer;

public interface ScoreSubject {
    void registerObserver(ScoreObserver scoreObserver);
    void removeObserver(ScoreObserver scoreObserver);
    void notifyObservers();
}