package com.thejohnsondev.ui.displaymessage

import androidx.compose.runtime.Composable
import com.thejohnsondev.model.DisplayableMessageValue
import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.added_success
import vaultmultiplatform.core.ui.generated.resources.cancel
import vaultmultiplatform.core.ui.generated.resources.check_internet_connection
import vaultmultiplatform.core.ui.generated.resources.copied
import vaultmultiplatform.core.ui.generated.resources.email_error_incorrect
import vaultmultiplatform.core.ui.generated.resources.export_message_successful
import vaultmultiplatform.core.ui.generated.resources.export_message_unsuccessful
import vaultmultiplatform.core.ui.generated.resources.import_message_successful
import vaultmultiplatform.core.ui.generated.resources.import_message_unsuccessful
import vaultmultiplatform.core.ui.generated.resources.password_error_bad_length
import vaultmultiplatform.core.ui.generated.resources.password_error_no_capital
import vaultmultiplatform.core.ui.generated.resources.password_error_no_numbers
import vaultmultiplatform.core.ui.generated.resources.password_error_no_small
import vaultmultiplatform.core.ui.generated.resources.password_generator_strength_extremely_strong
import vaultmultiplatform.core.ui.generated.resources.password_generator_strength_extremely_weak
import vaultmultiplatform.core.ui.generated.resources.password_generator_strength_moderate
import vaultmultiplatform.core.ui.generated.resources.password_generator_strength_strong
import vaultmultiplatform.core.ui.generated.resources.password_generator_strength_too_weak
import vaultmultiplatform.core.ui.generated.resources.password_generator_strength_very_strong
import vaultmultiplatform.core.ui.generated.resources.password_generator_strength_weak
import vaultmultiplatform.core.ui.generated.resources.password_generator_top_10
import vaultmultiplatform.core.ui.generated.resources.password_generator_top_100
import vaultmultiplatform.core.ui.generated.resources.password_generator_top_1000
import vaultmultiplatform.core.ui.generated.resources.password_generator_very_common
import vaultmultiplatform.core.ui.generated.resources.stub
import vaultmultiplatform.core.ui.generated.resources.unknown_error
import vaultmultiplatform.core.ui.generated.resources.updated_success

@Composable
fun DisplayableMessageValue.getAsComposeText(): String {
    if (this is DisplayableMessageValue.StringValue) {
        return this.value
    }
    val stringResource = getStringResource(this)
    return stringResource(stringResource)
}

suspend fun DisplayableMessageValue.getAsText(): String {
    if (this is DisplayableMessageValue.StringValue) {
        return this.value
    }
    val stringResource = getStringResource(this)
    return getString(stringResource)
}

internal fun getStringResource(value: DisplayableMessageValue): StringResource {
    return when (value) {
        is DisplayableMessageValue.StringValue -> ResString.stub
        DisplayableMessageValue.UnknownError -> ResString.unknown_error
        DisplayableMessageValue.PasswordEditSuccess -> ResString.updated_success
        DisplayableMessageValue.BadLength -> ResString.password_error_bad_length
        DisplayableMessageValue.NoNumbers -> ResString.password_error_no_numbers
        DisplayableMessageValue.NoCapital -> ResString.password_error_no_capital
        DisplayableMessageValue.NoSmall -> ResString.password_error_no_small
        DisplayableMessageValue.EmailInvalid -> ResString.email_error_incorrect
        DisplayableMessageValue.CheckInternetConnection -> ResString.check_internet_connection
        DisplayableMessageValue.PasswordAddedSuccess -> ResString.added_success
        DisplayableMessageValue.Copied -> ResString.copied
        DisplayableMessageValue.ExportSuccessful -> ResString.export_message_successful
        DisplayableMessageValue.ExportUnsuccessful -> ResString.export_message_unsuccessful
        DisplayableMessageValue.ImportSuccessful -> ResString.import_message_successful
        DisplayableMessageValue.ImportUnsuccessful -> ResString.import_message_unsuccessful
        DisplayableMessageValue.Cancel -> ResString.cancel
        DisplayableMessageValue.StrengthExtremelyStrong -> ResString.password_generator_strength_extremely_strong
        DisplayableMessageValue.StrengthExtremelyWeak -> ResString.password_generator_strength_extremely_weak
        DisplayableMessageValue.StrengthModerate -> ResString.password_generator_strength_moderate
        DisplayableMessageValue.StrengthStrong -> ResString.password_generator_strength_strong
        DisplayableMessageValue.StrengthTooWeak -> ResString.password_generator_strength_too_weak
        DisplayableMessageValue.StrengthVeryStrong -> ResString.password_generator_strength_very_strong
        DisplayableMessageValue.StrengthWeak -> ResString.password_generator_strength_weak
        DisplayableMessageValue.Top1000Common -> ResString.password_generator_top_1000
        DisplayableMessageValue.Top100Common -> ResString.password_generator_top_100
        DisplayableMessageValue.Top10Common -> ResString.password_generator_top_10
        DisplayableMessageValue.VeryCommon -> ResString.password_generator_very_common
    }
}