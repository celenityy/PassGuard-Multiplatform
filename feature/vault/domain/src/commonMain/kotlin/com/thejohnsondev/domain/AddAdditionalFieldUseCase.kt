package com.thejohnsondev.domain

import com.thejohnsondev.common.empty
import com.thejohnsondev.common.utils.getCurrentTimeStamp
import com.thejohnsondev.common.model.vault.AdditionalFieldDto

class AddAdditionalFieldUseCase {
    operator fun invoke(currentList: List<AdditionalFieldDto>): List<AdditionalFieldDto> {
        val newList = currentList.toMutableList()
        newList.add(
            AdditionalFieldDto(
                id = getCurrentTimeStamp(),
                title = String.Companion.empty,
                value = String.Companion.empty
            )
        )
        return newList
    }
}