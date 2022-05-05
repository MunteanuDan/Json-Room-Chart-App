package eu.ase.tema2android.databaseCentru;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Centru.class}, exportSchema = false, version = 1)
public abstract class DatabaseManager extends RoomDatabase {

    public static final String DAM_DB = "dam_db";
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) { // sincronizam 2 apeluri care incearca sa foloseasca aceeasi resursa
                // dupa sincronizarea primului apel, incepe si al doilea, dar ingreuneaza procesarea
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context, DatabaseManager.class, DAM_DB)
                            .fallbackToDestructiveMigration()
                            //.allowMainThreadQueries() - nu se foloseste decat in teste. produce pagube la nivelul memorie pentru activitati
                            .build();
                }
            }
        }
        return databaseManager;
    }

    public abstract CentruDao getCentruDao();

}
