/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.example.sooheib.habittrackerapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sooheib.habittrackerapp.R;
import com.example.sooheib.habittrackerapp.constants.VariableConstant;
import com.example.sooheib.habittrackerapp.database.HabitDbHelper;
import com.example.sooheib.habittrackerapp.models.HabitModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sooheib on 8/27/16.
 */
public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.details_txtTitle)
    protected TextView txtTitle;
    @BindView(R.id.details_txtDate)
    protected TextView txtDate;
    @BindView(R.id.details_edtDetails)
    protected EditText edtDetails;
    @BindView(R.id.details_cbCompleted)
    protected CheckBox cbCompleted;
    @BindView(R.id.details_btnUpdate)
    protected Button btnUpdate;
    @BindView(R.id.details_btnDelete)
    protected Button btnDelete;
    @BindView(R.id.details_btnCancel)
    protected Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // initViews
        initViews();
    }

    /**
     * initViews
     */
    private void initViews() {
        ButterKnife.bind(this);

        Intent intent = getIntent();
        final HabitModel model = intent.getParcelableExtra(VariableConstant.HABIT_PARCELABLE);
        if (model != null) {
            txtTitle.setText(model.getmTitle());
            txtDate.setText(model.getmDate());
            edtDetails.setText(model.getmDetails());
            cbCompleted.setChecked(model.getmCompleted() != 0);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAction(model);

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAction(model);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * deleteAction
     *
     * @param model HabitModel
     */
    private void deleteAction(HabitModel model) {
        HabitDbHelper habitDbHelper = new HabitDbHelper(this);
        int result = habitDbHelper.deleteHabit(model);
        if (result > 0) {
            Toast.makeText(DetailsActivity.this, this.getString(R.string.btn_remove) + model.getmTitle(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(DetailsActivity.this, this.getString(R.string.error) + this.getString(R.string.btn_remove), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * updateAction
     *
     * @param model HabitModel
     */
    private void updateAction(HabitModel model) {
        model.setmDetails(edtDetails.getText().toString());
        model.setmCompleted(cbCompleted.isChecked() ? 1 : 0);

        HabitDbHelper habitDbHelper = new HabitDbHelper(this);
        int result = habitDbHelper.updateHabit(model);
        if (result > 0) {
            Toast.makeText(DetailsActivity.this, this.getString(R.string.btn_update) + model.getmTitle(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(DetailsActivity.this, this.getString(R.string.error) + this.getString(R.string.btn_update), Toast.LENGTH_SHORT).show();
        }
    }
}
