package com.example.data.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface SecurityLogDao {
    @Query("SELECT * FROM security_logs ORDER BY timestamp DESC LIMIT 150")
    fun getRecentLogs(): Flow<List<SecurityLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: SecurityLogEntity)

    @Query("DELETE FROM security_logs")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM security_logs WHERE eventType = 'CURFEW_LOCK'")
    fun getCurfewLockCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM security_logs WHERE eventType = 'TAMPER_ATTEMPT'")
    fun getTamperCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM security_logs WHERE eventType = 'PIN_VERIFY'")
    fun getPinVerifyCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM security_logs")
    fun getTotalLogsCount(): Flow<Int>
}

@Database(
    entities = [SecurityLogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SafeGuardDatabase : RoomDatabase() {
    abstract fun securityLogDao(): SecurityLogDao

    companion object {
        @Volatile
        private var INSTANCE: SafeGuardDatabase? = null

        fun getDatabase(context: Context): SafeGuardDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SafeGuardDatabase::class.java,
                    "safeguard_security.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
