package eu.ase.tema2android.databaseCentru;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CentruDao {

    @Insert
    long insert(Centru centru);

    @Query("select * from centrus")
    List<Centru> getAll();

    @Update
    int update(Centru centru);

    @Delete
    int delete(Centru centru);

}