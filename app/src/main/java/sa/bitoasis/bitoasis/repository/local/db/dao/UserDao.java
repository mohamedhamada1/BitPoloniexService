package sa.bitoasis.bitoasis.repository.local.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import sa.bitoasis.bitoasis.user.User;


/**
 * Created by Mohamed.Shaaban on 3/5/2018.
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("SELECT * FROM user")
    LiveData<User> get();

    @Query("DELETE FROM user")
    public void deleteTable();

}
