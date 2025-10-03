package com.complexparking.ui.wizard

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.CustomButton
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.base.CustomTextMedium
import com.complexparking.ui.base.CustomTextMediumBold
import com.complexparking.ui.base.Dimensions.size2dp
import com.complexparking.ui.base.Dimensions.size30dp
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.base.Dimensions.size80dp
import com.complexparking.ui.base.MainContainer
import com.complexparking.ui.theme.LocalCustomColors
import com.complexparking.ui.utilities.AnimateLinearProgressBarControl
import com.complexparking.ui.utilities.LinearProgressManager
import com.complexparking.ui.widgets.PermissionView
import com.complexparking.utils.tools.ExcelTools
import org.koin.java.KoinJavaComponent.inject

@Composable
fun UploadComplexDataScreen(navController: NavController) {
    val colors = LocalCustomColors.current
    val wizardScreenViewModel: WizardScreenViewModel by inject(WizardScreenViewModel::class.java)
    PermissionView()
    MainContainer(
        header = {
            CustomHeader(
                headerTitle = stringResource(id = R.string.wizard_upload_complex_data_title),
                modifier = Modifier.fillMaxSize()
            )
        },
        body = {
            UploadComplexDataBody(
                wizardScreenViewModel
            )
        }
    )
}

@Composable
private fun UploadComplexDataBody(wizardScreenViewModel: WizardScreenViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(size30dp)
    ) {
        CustomTextMedium(
            text = stringResource(id = R.string.wizard_upload_complex_file_title)
        )
        CustomTextMedium(text = wizardScreenViewModel.wizardModel.value.pathFile?.path ?: "")
        HorizontalDivider(Modifier.height(size5dp))
        FilePickerButton(
            wizardScreenViewModel.wizardModel.value
        )
        if (wizardScreenViewModel.wizardModel.value.showPreviousList) {
            Column(
                modifier = Modifier
                    .border(
                        width = size2dp,
                        color = LocalCustomColors.current.colorNeutralBorder,
                        shape = RoundedCornerShape(size2dp)
                    )
                    .padding(top = size5dp)
            ) {

                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .border(
                                width = size2dp,
                                color = LocalCustomColors.current.colorNeutralBorder,
                                shape = RoundedCornerShape(size2dp)
                            )
                    ) {
                        CustomTextMediumBold(stringResource(R.string.wizard_upload_complex_file_column_unit), modifier = Modifier.width(size80dp))
                        CustomTextMediumBold(stringResource(R.string.wizard_upload_complex_file_column_name), modifier = Modifier.width(size80dp))
                        CustomTextMediumBold(stringResource(R.string.wizard_upload_complex_file_column_last_name), modifier = Modifier.width(size80dp))
                        CustomTextMediumBold(stringResource(R.string.wizard_upload_complex_file_column_plate), modifier = Modifier.width(size80dp))
                    }
                    wizardScreenViewModel.wizardModel.value.previousList.forEach { data ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                        ) {
                            CustomTextMedium(data.complexUnit.toString(), modifier = Modifier.width(size80dp))
                            VerticalDivider(modifier = Modifier.width(size5dp))
                            CustomTextMedium(data.residentName, modifier = Modifier.width(size80dp))
                            VerticalDivider(modifier = Modifier.width(size5dp))
                            CustomTextMedium(data.residentLastName, modifier = Modifier.width(size80dp))
                            VerticalDivider(modifier = Modifier.width(size5dp))
                            CustomTextMedium(data.plate, modifier = Modifier.width(size80dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilePickerButton(model: WizardScreenModel) {
    val context = LocalContext.current
    val fileType = "*/*"
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            model.onSearchFileButton(uri)
        }
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        CustomButton(
            buttonText = stringResource(R.string.wizard_upload_complex_file_button),
            onClick = {
                launcher.launch(arrayOf(fileType))
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
                            model.onUploadFileClick(result)
                        }
                    }
                }
            )
        }
    }
    AnimateLinearProgressBarControl()
}
