package com.complexparking.ui.wizard

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import com.complexparking.R
import com.complexparking.ui.base.CustomButton
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.base.CustomTextMedium
import com.complexparking.ui.base.Dimensions.size10dp
import com.complexparking.ui.base.Dimensions.size16dp
import com.complexparking.ui.base.Dimensions.size2dp
import com.complexparking.ui.base.Dimensions.size30dp
import com.complexparking.ui.base.Dimensions.size40dp
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.base.SimpleContainer
import com.complexparking.ui.controls.CustomImage
import com.complexparking.ui.theme.LocalCustomColors
import com.complexparking.ui.utilities.AnimateLinearProgressBarControl
import com.complexparking.ui.utilities.LinearProgressManager
import com.complexparking.ui.utilities.formatPlate
import com.complexparking.ui.widgets.PermissionView
import com.complexparking.utils.tools.ExcelTools
import com.complexparking.utils.tools.FileData
import org.koin.androidx.compose.koinViewModel

@Composable
fun UploadComplexDataScreen() {
    val wizardScreenViewModel: WizardScreenViewModel = koinViewModel()
    val model by wizardScreenViewModel.wizardModel.collectAsState()
    PermissionView()
    SimpleContainer(
        header = {
            CustomHeader(
                headerTitle = stringResource(id = R.string.wizard_upload_complex_data_title),
                modifier = Modifier.fillMaxSize()
            )
        },
        body = {
            UploadComplexDataBody(
                model = model,
                onSearchFileButton = { wizardScreenViewModel.onFileChange(it) },
                onUploadFileClick = { wizardScreenViewModel.onUploadDataFile(it) }
            )
        }
    )
}

@Composable
private fun UploadComplexDataBody(
    model: WizardScreenModel,
    onSearchFileButton: (Uri) -> Unit,
    onUploadFileClick: (ArrayList<FileData>) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = size30dp, end = size30dp, bottom = size40dp)
    ) {
        CustomTextMedium(
            text = stringResource(id = R.string.wizard_upload_complex_file_title)
        )
        CustomTextMedium(text = model.pathFile?.path ?: "")
        HorizontalDivider(Modifier.height(size5dp))
        FilePickerButton(
            model = model,
            onSearchFileButton = onSearchFileButton,
            onUploadFileClick = onUploadFileClick
        )
        if (model.showPreviousList) {
            Column(
                modifier = Modifier
                    .padding(top = size5dp, bottom = size10dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Column {
                    model.previousList.forEach { data ->
                        UserItemRow(data)
                    }
                }
            }
        }
    }
}

@Composable
fun UserItemRow(data: PreviousFileData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(size5dp),
        elevation = CardDefaults.cardElevation(defaultElevation = size2dp),
        colors = CardDefaults.cardColors(containerColor = LocalCustomColors.current.colorPrimaryBg)
    ) {
        Row(
            modifier = Modifier
                .padding(size16dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomImage(
                modifier = Modifier.size(size30dp),
                imageResourceId = R.drawable.ic_home
            )
            Spacer(modifier = Modifier.width(size16dp))
            Column {
                CustomTextMedium(text = data.complexUnit.toString(), fontWeight = FontWeight.Bold)
                Row {
                    CustomTextMedium(text = data.residentName)
                    HorizontalDivider(modifier = Modifier.width(size5dp))
                    CustomTextMedium(text = data.residentLastName)
                }
                CustomTextMedium(text = data.plate.formatPlate())
            }
        }
    }
}

@Composable
fun FilePickerButton(
    model: WizardScreenModel,
    onSearchFileButton: (Uri) -> Unit,
    onUploadFileClick: (ArrayList<FileData>) -> Unit,
) {
    val context = LocalContext.current
    val fileType = "*/*"

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            onSearchFileButton(uri)
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (fileButton, loadButton) = createRefs()
        CustomButton(
            buttonText = stringResource(R.string.wizard_upload_complex_file_button),
            onClick = {
                launcher.launch(arrayOf(fileType))
            },
            modifier = Modifier.constrainAs(fileButton) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
        if (model.uploadButtonVisibility) {
            CustomButton(
                buttonText = stringResource(R.string.wizard_upload_complex_file_load_button),
                onClick = {
                    LinearProgressManager.showLoader()
                    model.pathFile?.let {
                        if (!it.path.isNullOrEmpty()) {
                            val result = ExcelTools.readExcelFile(context, model.pathFile) {
                                LinearProgressManager.updateProgress(it)
                            }
                            onUploadFileClick(result)
                        }
                    }
                },
                modifier = Modifier.constrainAs(loadButton) {
                    start.linkTo(fileButton.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
            )
        }
    }
    AnimateLinearProgressBarControl()
}
