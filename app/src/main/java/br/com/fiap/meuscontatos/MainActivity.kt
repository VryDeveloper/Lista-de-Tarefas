package br.com.fiap.meuscontatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.meuscontatos.database.repository.TarefaRepository
import br.com.fiap.meuscontatos.model.Tarefa
import br.com.fiap.meuscontatos.ui.theme.TarefasCheckListTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TarefasCheckListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        TarefasScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun TarefasScreen() {

    var nomeState = remember {
        mutableStateOf("")
    }

    var descricaoState = remember {
        mutableStateOf("")
    }

    var feitoState = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val tarefaRepository = TarefaRepository(context)

    var listaTarefasState = remember {
        mutableStateOf(tarefaRepository.listarTarefas())
    }



    Column {
        TarefasForm(
            nome = nomeState.value,
            descricao = descricaoState.value,
            feito = feitoState.value,
            onNomeChange = {
                nomeState.value = it
            },
            onDescricaoChange = {
                descricaoState.value = it
            },
            onFeitoChange = {
                feitoState.value = it
            },
            atualizar = {
                listaTarefasState.value = tarefaRepository.listarTarefas()
            }
        )
        TarefasList(
            listaTarefasState,
            atualizar = { listaTarefasState.value = tarefaRepository.listarTarefas() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarefasForm(
    nome: String,
    descricao: String,
    feito: Boolean,
    onNomeChange: (String) -> Unit,
    onDescricaoChange: (String) -> Unit,
    onFeitoChange: (Boolean) -> Unit,
    atualizar: () -> Unit
) {
    //Obter contexto

    val context = LocalContext.current
    val tarefaRepository = TarefaRepository(context)
    var error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tarefas",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A4FB7),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            singleLine = true,
            isError = error,
            value = nome,
            onValueChange = { onNomeChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Nome da Tarefa")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words
            )

        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = descricao,
            onValueChange = { onDescricaoChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Descricao da Tarefa")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            isError = error
        )
        if(error){
            Text(
                text = "Nome e Descricao Obrigatorios",
                fontSize = 14.sp,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                colors = CheckboxDefaults.colors(Color(0xFF3A4FB7)),
                checked = feito,
                onCheckedChange = {
                    onFeitoChange(it)
                }
            )
            Text(text = "Feito")
            Icon(
                imageVector = if (feito) Icons.Filled.Check else Icons.Filled.Check,
                contentDescription = "amigo",
                tint = if (feito) Color(0xFF7E0C0C) else Color(0xFF136E07)
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            colors = ButtonDefaults.buttonColors(Color(0xFF3A4FB7)),
            onClick = {
                val tarefa = br.com.fiap.meuscontatos.model.Tarefa(
                    id = 0,
                    nome = nome,
                    descricao = descricao,
                    isFeito = feito,
                )
                if (nome.isNotEmpty() && descricao.isNotEmpty()) {
                    tarefaRepository.salvar(tarefa)
                    atualizar()
                    onNomeChange("")
                    onDescricaoChange("")
                    onFeitoChange(false)
                    error = false
                }else{
                    error = true
                }
            }
        ) {
            Text(
                text = "CADASTRAR",
                modifier = Modifier
                    .padding(8.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TarefasList(
    listaContatos: MutableState<List<Tarefa>>,
    atualizar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        for (contato in listaContatos.value) {
            TarefasCard(contato, atualizar)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun TarefasCard(tarefa: Tarefa, atualizar: () -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
            },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(3.dp, Color(0xFF3A4FB7))
    ) {
        Row(modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(2f)
            ) {
                Text(
                    text = tarefa.nome,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = tarefa.descricao,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (tarefa.isFeito) "Feito" else "A fazer",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if(tarefa.isFeito) Color.Green else Color.Red
                )
            }
            IconButton(onClick = {
                val tarefaRepository = TarefaRepository(context)
                tarefaRepository.excluir(tarefa)
                atualizar()
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = ""
                )
            }
        }
    }
}