package ru.zont.topbuilder.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.zont.topbuilder.R;
import ru.zont.topbuilder.ui.ActionFragment;

public class ActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.action_container, ActionFragment.newInstance())
                    .commitNow();
        }
    }

}