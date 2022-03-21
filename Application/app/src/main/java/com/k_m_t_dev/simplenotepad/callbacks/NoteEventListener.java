package com.k_m_t_dev.simplenotepad.callbacks;

import com.k_m_t_dev.simplenotepad.model.Note;

public interface NoteEventListener {

    void onNoteClick(Note note);

    void onNoteLongClick(Note note);
}
