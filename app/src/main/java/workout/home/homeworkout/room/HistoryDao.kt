package workout.home.homeworkout.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(historyEntity: HistoryEntity)


    @Query("SELECT * FROM 'history_table'")
    fun fetchAllDates(): Flow<List<HistoryEntity>>
}