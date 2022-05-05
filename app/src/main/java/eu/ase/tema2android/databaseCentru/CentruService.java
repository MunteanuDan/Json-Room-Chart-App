package eu.ase.tema2android.databaseCentru;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.tema2android.async.AsyncTaskRunner;
import eu.ase.tema2android.async.Callback;

public class CentruService {

    private final CentruDao centruDao;
    private final AsyncTaskRunner asyncTaskRunner;

    public CentruService(Context context) { // folosim Context-ul clasei pe care-l vom folosi pt a stabili conexiunea cu baza de date
        this.centruDao = DatabaseManager.getInstance(context).getCentruDao();
        this.asyncTaskRunner = new AsyncTaskRunner();
    }

    public void insert(Centru centru, Callback<Centru> activityThread) { // al doilea param este locatia unde trimitem raspunsul
        //operation executata pe un alt thread
        Callable<Centru> insertOperation = new Callable<Centru>() {
            @Override
            public Centru call() {

                // aici se realizeaza operatia de insert cu baza de date
                // ne aflam pe un alt fir de executie

                if (centru == null || centru.getId() > 0) { // returnam null daca Id-ul nu e 0
                    return null;
                }
                long id = centruDao.insert(centru); // luam id-ul inregistrarii
                if (id < 0) { //daca ceva nu a functionat in scriptul de insert, vom avea -1
                    return null;
                }
                centru.setId(id); // setam id-ul
                return centru;
            }
        };

        //pornire thread secundar
        asyncTaskRunner.executeAsync(insertOperation, activityThread);
    }

    public void getAll(Callback<List<Centru>> activityThread) {
        Callable<List<Centru>> selectOperation = new Callable<List<Centru>>() {
            @Override
            public List<Centru> call() {
                return centruDao.getAll();
            }
        };

        asyncTaskRunner.executeAsync(selectOperation, activityThread);
    }


    public void update(Centru centru, Callback<Centru> activityThread) {
        Callable<Centru> updateOperation = new Callable<Centru>() {
            @Override
            public Centru call() {
                if (centru == null || centru.getId() <= 0) {
                    return null;
                }
                int count = centruDao.update(centru);
                if (count <= 0) {
                    return null;
                }
                return centru;
            }
        };

        asyncTaskRunner.executeAsync(updateOperation, activityThread);
    }

    public void delete(Centru centru, Callback<Boolean> activityThread) { // Boolean pt ca daca s-a sters true, daca nu false
        Callable<Boolean> deleteOperation = new Callable<Boolean>() {
            @Override
            public Boolean call() {
                if (centru == null || centru.getId() <= 0) {
                    return false;
                }
                int count = centruDao.delete(centru);
                return count > 0;
            }
        };

        asyncTaskRunner.executeAsync(deleteOperation, activityThread);
    }


}