package com.example.eatmeet.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.UserDAO;

public class AccountSettingsActivity extends AppCompatActivity {

    private TextView fullNameField;
    private TextView emailField;
    private Button deleteAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fullNameField = (TextView) findViewById(R.id.fullNameField);
        emailField = (TextView) findViewById(R.id.emailField);
        deleteAccountButton = (Button) findViewById(R.id.deleteAccount);

        fullNameField.setText(EatMeetApp.getCurrentUser().getFullName());
        emailField.setText(EatMeetApp.getCurrentUser().getEmail());

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountSettingsActivity.this);
                builder.setPositiveButton("OK, Cancelliamo", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        UserDAO userDAO = EatMeetApp.getDaoFactory().getUserDAO();
                        BackendStatusManager bsm = new BackendStatusManager();
                        bsm.setBackendStatusListener(new BackendStatusListener() {
                            @Override
                            public void onSuccess(Object response, Integer code) {
                                System.out.println("ACCOUNT DELETIO SUCCESS: "+response);
                                EatMeetApp.setCurrentUser(null);
                                Intent intent = new Intent(AccountSettingsActivity.this, MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Object response, Integer code) {
                                System.out.println("ACCOUNT DELETION FAILED: "+response);
                            }
                        });
                        userDAO.deleteAccount(bsm);
                    }
                });
                builder.setNegativeButton("No, non cancellare", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Stuff to do
                    }
                });

                builder.setMessage("ELIMINAZIONE ACCOUNT");
                builder.setTitle("Sei sicuro di voler eliminare l'account? L'azione non è reversibile");

                AlertDialog d = builder.create();
                d.show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(AccountSettingsActivity.this, MainActivity.class);
        startActivity(intent);
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editButton:
                Intent intent = new Intent(AccountSettingsActivity.this, EditAccountSettingsActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
