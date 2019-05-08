package com.example.leastofficialpokedex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leastofficialpokedex.model.PokemonDatabaseApi
import com.example.leastofficialpokedex.model.PokemonModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PokemonViewmodel: ViewModel() {
    companion object {
        const val MAX_POKEMON = 802
        val api: PokemonDatabaseApi by lazy { PokemonDatabaseApi.create() }
        var disposable: Disposable? = null
    }
    var startIndex = 0

    private val nameList: MutableLiveData<List<PokemonModel.PokemonName>> =
        MutableLiveData()
    val names: LiveData<List<PokemonModel.PokemonName>>
        get() = nameList

    fun getMoreNames() {
        val limit = if (startIndex + 20 <= MAX_POKEMON) 20 else MAX_POKEMON - startIndex

        if (limit > 0) {
            disposable?.dispose()
            disposable = api.getNext20(startIndex, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result: PokemonModel.PokemonListResponse ->
                    nameList.postValue(result.results)
                }
        }
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }
}