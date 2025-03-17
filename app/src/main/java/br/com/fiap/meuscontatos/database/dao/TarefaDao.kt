package br.com.fiap.meuscontatos.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.meuscontatos.model.Tarefa

@Dao
interface TarefaDao  {

    @Insert
    fun salvar(tarefa: Tarefa): Long

    @Update
    fun atualizar(tarefa: Tarefa): Int

    @Delete
    fun excluir(tarefa: Tarefa): Int

    @Query("SELECT * FROM tbl_tarefa WHERE id = :id")
    fun buscarTarefaPeloId(id: Long): Tarefa?

    @Query("SELECT * FROM tbl_tarefa ORDER BY nome ASC")
    fun listarTarefas(): List<Tarefa>

}