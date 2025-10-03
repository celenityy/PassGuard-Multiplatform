package com.thejohnsondev.common.model.vault

data class NoteDto(
    val title: String,
    val description: String,
    val backgroundColorHex: String, // TODO create a color picker for this
    override val id: String,
    override val createdTimeStamp: String? = null,
    override val modifiedTimeStamp: String? = null,
    override val syncedTimeStamp: String? = null,
    override val syncStatus: String? = null,
    override val isFavorite: Boolean = false, // Ignore on the server side
    override val categoryId: String,
): VaultItemDto