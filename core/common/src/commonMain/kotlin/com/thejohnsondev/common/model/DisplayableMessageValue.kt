package com.thejohnsondev.common.model

sealed interface DisplayableMessageValue {
    data object UnknownError : DisplayableMessageValue
    data class StringValue(val value: String) : DisplayableMessageValue
    data object PasswordEditSuccess : DisplayableMessageValue
    data object PasswordAddedSuccess : DisplayableMessageValue
    data object BadLength : DisplayableMessageValue
    data object NoNumbers : DisplayableMessageValue
    data object NoCapital : DisplayableMessageValue
    data object NoSmall : DisplayableMessageValue
    data object EmailInvalid : DisplayableMessageValue
    data object CheckInternetConnection : DisplayableMessageValue
    data object Copied : DisplayableMessageValue
    data object ExportSuccessful : DisplayableMessageValue
    data object ExportUnsuccessful : DisplayableMessageValue
    data object ImportSuccessful : DisplayableMessageValue
    data object ImportUnsuccessful : DisplayableMessageValue
    data object Cancel: DisplayableMessageValue

    // Password generator
    data object Top10Common: DisplayableMessageValue
    data object Top100Common: DisplayableMessageValue
    data object Top1000Common: DisplayableMessageValue
    data object VeryCommon: DisplayableMessageValue
    data object StrengthExtremelyWeak: DisplayableMessageValue
    data object StrengthTooWeak: DisplayableMessageValue
    data object StrengthWeak: DisplayableMessageValue
    data object StrengthModerate: DisplayableMessageValue
    data object StrengthStrong: DisplayableMessageValue
    data object StrengthVeryStrong: DisplayableMessageValue
    data object StrengthExtremelyStrong: DisplayableMessageValue
}