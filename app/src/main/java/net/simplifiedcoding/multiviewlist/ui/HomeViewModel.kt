package net.simplifiedcoding.multiviewlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import net.simplifiedcoding.multiviewlist.data.network.Resource
import net.simplifiedcoding.multiviewlist.data.repository.Repository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _homeListItemsLiveData = MutableLiveData<Resource<List<HomeRecyclerViewItem>>>()
    val homeListItemsLiveData: LiveData<Resource<List<HomeRecyclerViewItem>>>
        get() = _homeListItemsLiveData

    init {
        getHomeListItems()
    }

    private fun getHomeListItems() = viewModelScope.launch {
            _homeListItemsLiveData.postValue(Resource.Loading)
        val movieDeferred = async { repository.getMovies() }
        val directorsDeferred = async { repository.getDirectors() }

        val movies = movieDeferred.await()
        val directors = directorsDeferred.await()

        val homeItemList = mutableListOf<HomeRecyclerViewItem>()

        if (movies is Resource.Success && directors is Resource.Success){
            homeItemList.add(HomeRecyclerViewItem.Title(1,"Recommended Movies"))
            homeItemList.addAll(movies.value)
            homeItemList.add(HomeRecyclerViewItem.Title(2,"Top Directors"))
            homeItemList.addAll(directors.value)
            _homeListItemsLiveData.postValue(Resource.Success(homeItemList))


        }else{
            Resource.Failure(false,null,null)
        }


    }
}