package com.example.githubsub.ui.screen.repositorylist
import com.example.githubsub.model.ProjectList


data class RepositoryListState(
    val projectName: String,
    val projectList: ProjectList
) {
    companion object {
        val initValue = RepositoryListState(
            projectName = "",
            projectList = ProjectList(mutableListOf())
        )
    }
}