package com.k_m_t_dev.simplenotepad;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.k_m_t_dev.simplenotepad.db.NotesDB;
import com.k_m_t_dev.simplenotepad.db.NotesDao;
import com.k_m_t_dev.simplenotepad.model.Note;

import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {
    private EditText inputNote;
    private NotesDao dao;
    private Note temp;
    public static final String NOTE_EXTRA_Key = "note_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        int theme = sharedPreferences.getInt(MainActivity.THEME_Key, R.style.AppTheme);
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_note);
        Toolbar toolbar = findViewById(R.id.edit_note_activity_toolbar);
        setSupportActionBar(toolbar);
        createNotificationChannel();

        inputNote = findViewById(R.id.input_note);
        dao = NotesDB.getInstance(this).notesDao();
        if (getIntent().getExtras() != null) {
            int id = getIntent().getExtras().getInt(NOTE_EXTRA_Key, 0);
            temp = dao.getNoteById(id);
            inputNote.setText(temp.getNoteText());
        } else inputNote.setFocusable(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edite_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_note){
            onSaveNote();
            Intent intent = new Intent(EditNoteActivity.this,ReminderBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(EditNoteActivity.this, 0, intent, 0);


            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            long timeAtButtonClick = System.currentTimeMillis();

            long tenSecondsMillis = 1;

            alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtButtonClick + tenSecondsMillis,pendingIntent);
        }


        return super.onOptionsItemSelected(item);
    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "LemubitRemindererChannel";
            String description = "Channel for Lemubit Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifylemubit", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



    private void onSaveNote() {
        String text = inputNote.getText().toString();
        if (!text.isEmpty()) {
            long date = new Date().getTime();

            if (temp == null) {
                temp = new Note(text, date);
                dao.insertNote(temp);
            } else {
                temp.setNoteText(text);
                temp.setNoteDate(date);
                dao.updateNote(temp);
            }

            finish();
        }

    }
}
