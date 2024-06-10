package dipl.structured.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dipl.structured.R
import dipl.structured.data.model.Project

class ProjectListAdapter: ListAdapter<Project,ProjectListAdapter.ProjectViewHolder>(ProjectsComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProjectViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val projectTitleView:TextView=itemView.findViewById(R.id.projectTitle)
        private val projectDescView:TextView=itemView.findViewById(R.id.projectDescription)
        private val projectAgeView:TextView=itemView.findViewById(R.id.syncAge)

        fun bind(item:Project){//may need to make it nullable
            projectTitleView.text=item.Title
            projectAgeView.text=item.updated.toString()//make a reasonable time and date format here
            projectDescView.text=item.Description
            val args=Bundle()
            args.putString("ProjectId", item.id)
            itemView.setOnClickListener { itemView.findNavController().navigate(R.id.action_navigation_home_to_navigation_project,args) }

        }

        companion object{
            fun create(parent:ViewGroup):ProjectViewHolder{
                val view:View=LayoutInflater.from(parent.context).inflate(R.layout.project_view_item,parent,false)
                return ProjectViewHolder(view)
            }
        }
    }
    class ProjectsComparator:DiffUtil.ItemCallback<Project>(){
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.id==newItem.id
        }
    }
}