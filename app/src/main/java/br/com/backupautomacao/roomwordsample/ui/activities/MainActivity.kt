package br.com.backupautomacao.roomwordsample.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import br.com.backupautomacao.roomwordsample.R
import br.com.backupautomacao.roomwordsample.data.Word
import br.com.backupautomacao.roomwordsample.adapter.WordListAdapter
import br.com.backupautomacao.roomwordsample.application.WordsApplication
import br.com.backupautomacao.roomwordsample.ui.viewmodel.WordViewModel
import br.com.backupautomacao.roomwordsample.ui.viewmodel.factory.WordViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

  private val wordViewModel: WordViewModel by viewModels {
    WordViewModelFactory((application as WordsApplication).repository)
  }

  private val activityResult =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
      Log.i("MainActivity", "${result.resultCode}")
      if (result.resultCode == RESULT_OK) {
        val data = result.data
        Log.i("MainActivity", "resultActivity: ${data?.getStringExtra(NewWordActivity.EXTRA_REPLY)}")
        data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
          Log.i("MainActivity", "resultActivity Word: $it")
          val word = Word(word = it)
          wordViewModel.insert(word)
        }
      } else {
        Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
      }
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
    val adapter = WordListAdapter()
    recyclerView.adapter = adapter

    wordViewModel.allWords.observe(this) { words ->
      words.forEach {
        Log.i("MainActivity", "onCreate: Palavras: ${it.word}")
      }
      words?.let { adapter.submitList(it) }
    }

    findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
      val intent = Intent(this@MainActivity, NewWordActivity::class.java)
      activityResult.launch(intent)
    }
  }
}