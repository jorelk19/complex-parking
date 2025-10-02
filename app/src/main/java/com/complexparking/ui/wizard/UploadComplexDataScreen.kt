package com.complexparking.ui.wizard

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.complexparking.R
import com.complexparking.ui.base.CustomButton
import com.complexparking.ui.base.CustomHeader
import com.complexparking.ui.base.CustomTextMedium
import com.complexparking.ui.base.Dimensions.size30dp
import com.complexparking.ui.base.Dimensions.size5dp
import com.complexparking.ui.base.MainContainer
import com.complexparking.ui.theme.LocalCustomColors
import com.complexparking.ui.utilities.AnimateLinearProgressBarControl
import com.complexparking.ui.utilities.LinearProgressManager
import com.complexparking.ui.widgets.PermissionView
import com.complexparking.utils.tools.ExcelTools
import org.koin.java.KoinJavaComponent.inject
import kotlin.arrayOf

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
