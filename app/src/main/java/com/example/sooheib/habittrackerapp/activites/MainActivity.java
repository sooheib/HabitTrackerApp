/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.example.sooheib.habittrackerapp.activites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sooheib.habittrackerapp.R;
import com.example.sooheib.habittrackerapp.adapters.HabitAdapter;
import com.example.sooheib.habittrackerapp.database.HabitDbHelper;
import com.example.sooheib.habittrackerapp.models.HabitModel;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sooheib on 8/27/16.
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.fab)
    protected FloatingActionButton fab;
    @BindView(R.id.btnRemoveAll)
    protected Button btnRemoveAll;
    @BindView(R.id.lvHabits)
    protected ListView lvHabits;
    @BindView(R.id.txtNoneHabits)
    protected TextView txtNoneHabits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initViews
        initViews();
    }

    /**
     * initViews
     */
    private void initViews() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAction();
            }
        });

        // Data
        loadHabitList();
    }

    /**
     * loadHabitList
     */
    private void loadHabitList() {
        HabitDbHelper habitDbHelper = new HabitDbHelper(this);
        final List<HabitModel> habits = habitDbHelper.selectAllHabits();

        if (habits.size() > 0) {
            HabitAdapter habitAdapter = new HabitAdapter(this, R.layout.list_habit_item, habits);
            lvHabits.setAdapter(habitAdapter);
            lvHabits.setVisibility(View.VISIBLE);
            txtNoneHabits.setVisibility(View.GONE);
            btnRemoveAll.setVisibility(View.VISIBLE);
            btnRemoveAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeAllAction(view);
                }
            });
        } else {
            lvHabits.setVisibility(View.GONE);
            txtNoneHabits.setVisibility(View.VISIBLE);
            btnRemoveAll.setVisibility(View.GONE);
        }
    }

    /**
     * removeAllAction
     *
     * @param view View
     */
    private void removeAllAction(View view) {
        HabitDbHelper habitDbHelper = new HabitDbHelper(this);
        habitDbHelper.deleteAllHabit();
        lvHabits.setVisibility(View.GONE);
        btnRemoveAll.setVisibility(View.GONE);
        txtNoneHabits.setVisibility(View.VISIBLE);
        Snackbar.make(view, this.getString(R.string.btn_removeAll), Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    /**
     * insertAction
     */
    private void insertAction() {
        showInputDialog();
    }

    /**
     * showInputDialog
     */
    private void showInputDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.inputDialog_txtTitle);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(this.getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HabitDbHelper habitDbHelper = new HabitDbHelper(getApplicationContext());
                        HabitModel model = new HabitModel();
                        model.setmTitle(editText.getText().toString());
                        model.setmDate(new Date().toString());
                        long result = habitDbHelper.insertHabit(model);

                        if (result > 0) {
                            loadHabitList();
                            Toast.makeText(MainActivity.this, getString(R.string.btn_insert) + editText.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(this.getString(R.string.btn_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHabitList();
    }
}
