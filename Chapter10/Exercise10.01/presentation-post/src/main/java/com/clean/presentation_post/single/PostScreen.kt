package com.clean.presentation_post.single

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clean.presentation_common.navigation.PostInput
import com.clean.presentation_common.state.CommonScreen

@Composable
fun PostScreen(
    viewModel: PostViewModel,
    postInput: PostInput
) {
    viewModel.submitAction(PostUiAction.Load(postInput.postId))
    viewModel.uiStateFlow.collectAsState().value.let { result ->
        CommonScreen(result) { postModel ->
            Post(postModel)
        }
    }
}

@Composable
fun Post(postModel: PostModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = postModel.title)
        Text(text = postModel.body)
    }
}