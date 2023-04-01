package com.example.firstapponkotlin.model

import android.content.Context
import android.os.Parcelable
import androidx.core.content.ContextCompat
import com.example.firstapponkotlin.R
import com.example.firstapponkotlin.data.noteId
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

@Parcelize
data class Note (
    val id: Long = noteId,
    val title: String = "",
    val note: String = "",
    val color: NoteColor = NoteColor.values()[Random.nextInt(NoteColor.values().size)]
): Parcelable

enum class NoteColor{
    WHITE,
    YELLOW,
    GREEN,
    BLUE,
    RED,
    VIOLET,
    PINK
}

fun NoteColor.mapToColor(context: Context): Int {
    val idColor = when(this){
        NoteColor.WHITE -> R.color.color_white
        NoteColor.YELLOW -> R.color.color_yellow
        NoteColor.GREEN -> R.color.color_green
        NoteColor.BLUE -> R.color.color_blue
        NoteColor.RED -> R.color.color_red
        NoteColor.VIOLET -> R.color.color_violet
        NoteColor.PINK -> R.color.color_pink
    }
    return ContextCompat.getColor(context, idColor)
}