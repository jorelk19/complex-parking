package com.complexparking.ui.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.HorizontalAlign
import com.complexparking.R
import com.complexparking.ui.base.Dimensions.size15dp
import com.complexparking.ui.base.Dimensions.size24dp
import com.complexparking.ui.base.Dimensions.size40dp
import com.complexparking.ui.theme.LocalCustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomHeader(
    modifier: Modifier = Modifier,
    headerTitle: String,
    imageStart: ImageVector? = null,
    imageEnd: ImageVector? = null,
    onClickStart: () -> Unit = {},
    onClickEnd: () -> Unit = {},
) {
    /*val colors = LocalCustomColors.current
    TopAppBar(
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = headerTitle,
                    color = colors.colorPrimaryText,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        modifier = modifier.padding(4.dp),
        navigationIcon = {
            Box(
                modifier = Modifier
                    .size(size24dp),
                contentAlignment = Alignment.Center
            ) {
                imageStart?.let {
                    Image(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.clickable { onClickStart() }
                    )
                }
            }
        },
        actions = {
            Box(
                modifier = Modifier
                    .size(size24dp),
                contentAlignment = Alignment.Center
            ) {
                imageEnd?.let {
                    Image(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.clickable { onClickEnd() }
                    )
                }
            }
        }
    )*/
    val colors = LocalCustomColors.current
    Row(
        modifier = Modifier.height(50.dp)
    ) {
        ConstraintLayout(
            modifier = modifier
                .height(size40dp)
                .fillMaxWidth()
        ) {
            val (imageS, text, imageE) = createRefs()
            Box(
                modifier = Modifier
                    .size(size24dp)
                    .constrainAs(imageS) {
                        start.linkTo(parent.start, margin = size15dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentAlignment = Alignment.Center
            ) {
                imageStart?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.clickable { onClickStart() },
                        tint = colors.colorIcPrimary
                    )
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.constrainAs(text) {
                    start.linkTo(imageS.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(imageE.start)
                }
            ) {
                CustomText2XLageBold(
                    text = headerTitle,
                    color = colors.colorPrimaryText,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Box(
                modifier = Modifier
                    .size(size24dp)
                    .constrainAs(imageE) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end, margin = size15dp)
                    },
                contentAlignment = Alignment.Center
            ) {
                imageEnd?.let {
                    Image(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.clickable { onClickEnd() }
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TestHeaderControl() {
    CustomHeader(
        headerTitle = "Titulo de prueba",
        imageStart = ImageVector.vectorResource(R.drawable.ic_house), imageEnd = ImageVector.vectorResource(R.drawable.ic_edit)
    )
}
