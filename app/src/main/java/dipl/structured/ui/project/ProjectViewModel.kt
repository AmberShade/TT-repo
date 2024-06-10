package dipl.structured.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import dipl.structured.data.ProjectRepository
import dipl.structured.data.TaskRepository
import dipl.structured.data.model.Project
import dipl.structured.data.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOf
import java.sql.Timestamp

class ProjectViewModel(private val repo:TaskRepository, private val scope: CoroutineScope) : ViewModel() {
    var availableTasks:LiveData<List<Task>>?=null
init{
    //scope.launch{availableProjects=repo.getProjects()}
    val demoSampleList=ArrayList<Task>(0)
    var task=Task("A task id","Название новое","Описание223232", Timestamp.valueOf("2024-04-13 00:00:00"),
        Timestamp.valueOf("2024-04-14 00:00:00"),Timestamp.valueOf("2024-04-07 21:38:22.226105"),"Кротов Георгий",2)
    demoSampleList.add(task)
    task=Task("Another task id","Сделать диплом", "Очень надо",Timestamp.valueOf("2024-05-04 00:00:00"),Timestamp.valueOf("2024-05-09 00:00:00"),
        Timestamp.valueOf("2024-04-03 22:42:39.606"),"Кротов Георгий",2)
    demoSampleList.add(task)
    task=Task("One more task","Задача 1", "Срочно сделать",Timestamp.valueOf("2024-04-03 21:54:02.626"),Timestamp.valueOf("2024-04-05 21:54:05.817"),
        Timestamp.valueOf("2024-04-03 21:54:09.163"),"Кротов Георгий",1)
    demoSampleList.add(task)
    availableTasks=flowOf(demoSampleList).asLiveData()
}

}
class ProjectViewModelFactory(private val repo: TaskRepository, private val scope:CoroutineScope):ViewModelProvider.Factory{
    override fun <T:ViewModel> create(modelClass: Class<T>):T{
        if (modelClass.isAssignableFrom(ProjectViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ProjectViewModel(repo, scope) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}