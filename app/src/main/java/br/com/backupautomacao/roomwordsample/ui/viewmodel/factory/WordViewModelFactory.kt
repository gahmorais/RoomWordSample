package br.com.backupautomacao.roomwordsample.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.backupautomacao.roomwordsample.data.WordRepository
import br.com.backupautomacao.roomwordsample.ui.viewmodel.WordViewModel
import java.lang.IllegalArgumentException

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return WordViewModel(repository) as T
    }

    throw IllegalArgumentException("Unknow ViewModel class")
  }
}