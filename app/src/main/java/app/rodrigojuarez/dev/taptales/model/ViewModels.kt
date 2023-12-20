package app.rodrigojuarez.dev.taptales.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TalesViewModel: ViewModel() {

    lateinit var tales: LiveData<List<Tale>>

    fun loadTales(taleDao: TaleDao) {
        viewModelScope.launch {
            tales = taleDao.getAllTales()
        }
    }
}