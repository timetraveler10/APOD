package com.hussein.apod.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE ApodEntity ADD COLUMN image_type TEXT ")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE ApodEntity RENAME COLUMN image_type TO media_type ")
    }
}
val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE ApodEntity ADD COLUMN favorite INTEGER ")
    }
}
@Database(
    entities = [ApodEntity::class],
    version = 4
)
abstract class ApodDatabase : RoomDatabase() {
    abstract val dao: ApodDao

}