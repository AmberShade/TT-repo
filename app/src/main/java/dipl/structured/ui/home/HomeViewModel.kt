package dipl.structured.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import dipl.structured.data.ProjectRepository
import dipl.structured.data.model.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOf
import java.sql.Timestamp
import java.util.Date

class HomeViewModel(private val repo:ProjectRepository, private val scope:CoroutineScope) : ViewModel() {
    var availableProjects:LiveData<List<Project>>?=null
    init{
        //scope.launch{availableProjects=repo.getProjects()}
        val demoSampleList=ArrayList<Project>(0)
        var project= Project("randomId1","Учебный проект","Сделать всё для защиты диплома",(arrayOf("3fd06ee7-cc26-4372-8127-837411f77c87").toList()),arrayOf("aTaskId").toList(),
            Timestamp(Date().time)
        )
        demoSampleList.add(project)
        //add more samples? Will keep in check with main db

        availableProjects= flowOf(demoSampleList).asLiveData()
    }
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}
class HomeViewModelFactory(private val repo:ProjectRepository, private val scope:CoroutineScope):ViewModelProvider.Factory{
    override fun <T:ViewModel> create(modelClass:Class<T>):T{
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repo,scope) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}