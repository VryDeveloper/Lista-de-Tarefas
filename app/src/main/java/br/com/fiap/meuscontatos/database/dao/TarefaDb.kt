package br.com.fiap.meuscontatos.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.meuscontatos.model.Tarefa
import kotlin.jvm.java

@Database(entities = [Tarefa::class], version = 1)
abstract class TarefaDb : RoomDatabase() {

    abstract fun TarefaDao(): TarefaDao

    companion object {

        private lateinit var instance: TarefaDb

        fun getDatabase(context: Context): TarefaDb {
            if (!::instance.isInitialized) {
                instance = Room
                    .databaseBuilder(
                        context,
                        TarefaDb::class.java,
                        "tarefa_db"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration() //Nivel de Teste - APAGA OS DADOS
                    .build()
            }
            return instance
        }
    }
}