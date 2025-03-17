package br.com.fiap.meuscontatos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_tarefa")
data class Tarefa(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var nome: String = "",
    @ColumnInfo(name = "is_feito") var isFeito: Boolean = false,
    var descricao: String = ""
)


