package br.com.fiap.meuscontatos.database.repository

import android.content.Context
import br.com.fiap.meuscontatos.database.dao.TarefaDb
import br.com.fiap.meuscontatos.model.Tarefa


class TarefaRepository(context: Context) {

    var db = TarefaDb.getDatabase(context).TarefaDao()

    fun salvar(tarefa: Tarefa): Long {
        return db.salvar(tarefa)
    }

    fun atualizar(tarefa: Tarefa): Int {
        return db.atualizar(tarefa)
    }

    fun excluir(tarefa: Tarefa): Int {
        return db.excluir(tarefa)
    }

    fun buscarTarefaPeloId(id: Long): Tarefa? {
        return db.buscarTarefaPeloId(id = id)
        }

    fun listarTarefas(): List<Tarefa> {
        return db.listarTarefas()
    }
}