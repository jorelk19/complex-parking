package com.complexparking.ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.complexparking.ui.base.Dimensions.size2dp
import com.complexparking.ui.base.Dimensions.size30dp
import com.complexparking.ui.theme.LocalCustomColors

@Composable
fun CustomImage(
    modifier: Modifier = Modifier,
    imageResourceId: Int,

) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = size2dp),
        colors = CardDefaults.cardColors(containerColor = LocalCustomColors.current.colorPrimaryBgCard)
    ) {
        ConstraintLayout {
            val image = createRef()
            Icon(
                imageVector = ImageVector.vectorResource(imageResourceId),
                contentDescription = "User Icon",
                modifier = Modifier
                    .size(size30dp)
                    .padding(size2dp)
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}