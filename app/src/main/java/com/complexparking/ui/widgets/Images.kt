package com.complexparking.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.complexparking.ui.base.Dimensions.size0dp
import com.complexparking.ui.base.Dimensions.size100dp
import com.complexparking.ui.base.Dimensions.size5dp

@Composable
fun CustomCard(
    cardModifier: Modifier = Modifier,
    imageId: Int,
    imagePadding: Dp = size0dp
) {
    ElevatedCard(
        modifier = cardModifier
            .fillMaxSize(),
        shape = RoundedCornerShape(size100dp),
        elevation = CardDefaults.cardElevation(size5dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.wrapContentSize()
        ) {
            val image = createRef()
            Image(
                painter = painterResource(imageId),
                contentDescription = null,
                modifier = Modifier
                    .padding(imagePadding)
                    .fillMaxSize()
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}