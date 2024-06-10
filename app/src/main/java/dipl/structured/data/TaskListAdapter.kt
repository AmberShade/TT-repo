package dipl.structured.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dipl.structured.R
import dipl.structured.data.model.Project
import dipl.structured.data.model.Task

class TaskListAdapter: ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TasksComparator()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        return TaskViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val taskTitleView:TextView=itemView.findViewById(R.id.taskTitle)
        private val taskAuthorView:TextView=itemView.findViewById(R.id.taskAuthor)
        private val taskAgeView:TextView=itemView.findViewById(R.id.syncAge)//creation date at this time, sync age by design
        private val taskLifespanView:TextView=itemView.findViewById(R.id.taskLifespan)
        private val taskStateView:TextView=itemView.findViewById(R.id.taskState)//won't use it at this time
        fun bind(item:Task){
            taskTitleView.text=item.Title
            taskAuthorView.text=item.AuthorId
            taskAgeView.text=item.Created.toString()
            taskLifespanView.text=item.Start.toString()+" - "+item.End.toString()
            taskStateView.text=when(item.State) {
                4->"Завершено"
                3->"На проверке"
                2->"В работе"
                1 -> "Не начато"
                else -> "неопознанный"
            }
                //no onclick here, task fragment not functional
        }
        companion object{
            fun create(parent:ViewGroup):TaskViewHolder{
                val view:View=LayoutInflater.from(parent.context).inflate(R.layout.task_view_item,parent,false)
                return TaskViewHolder(view)
            }
        }
    }
    class TasksComparator: DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.Id==newItem.Id
        }
    }
}