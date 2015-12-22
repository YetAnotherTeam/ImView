package jat.imview.network.ServiceHelperObserver;

/**
 * Created by bulat on 21.12.15.
 */
public interface Observable {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(String response);
}
