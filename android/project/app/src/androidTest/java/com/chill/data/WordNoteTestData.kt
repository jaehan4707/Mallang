package com.chill.data

import com.chill.mallang.ui.feature.word.CorrectWord
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

object WordNoteTestData {

    val wordList: PersistentList<CorrectWord> = persistentListOf(
        CorrectWord(
            word = "단어장1",
            pos = "명사",
            meaning = "단어장1 의미",
            example = "단어장1 예문"
        ),
        CorrectWord(
            word = "단어장2",
            pos = "부사",
            meaning = "단어장2 의미",
            example = "단어장2 예문"
        ),
    )
}