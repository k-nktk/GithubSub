//package com.example.githubsub.repository.datastore.initcontents
//
//import android.util.Log
//import androidx.datastore.core.DataStore
//import com.example.application.InitContentsPreference
//import com.example.application.ListIssueItem
//import com.example.application.SettingsPreference
//import com.example.githubsub.model.IssueList
//import com.example.githubsub.model.datastore.InitContent
//import com.example.githubsub.model.datastore.Label
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.withContext
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class InitContentsRepository @Inject constructor(
//    private val initContentsResultDataStore: DataStore<InitContentsPreference>,
//) : IInitContentsRepository {
//    override suspend fun writeUserResult(index: Int, issueNumber: Int, issueTitle: String, projectTitle: String, user: String, imageUrl: String, labelList: Label) {
//        withContext(Dispatchers.IO) {
//            try {
//                initContentsResultDataStore.updateData { currentResult ->
////                    currentResult.toBuilder().clear().setListIssue(index,
////
////                    )
////
////                        .build()
//                }
//            } catch (exception: Exception) {
//                Log.e("writeUserResult", "Failed to update user preferences")
//            }
//        }
//    }
//
//    override suspend fun getUserResult(): Result<SettingsPreference> =
//        withContext(Dispatchers.IO) {
//            try {
//                Result.Success(data = userResultDataStore.data.first())
//            } catch (exception: Exception) {
//                Result.Error(exception = exception)
//            }
//        }
//}