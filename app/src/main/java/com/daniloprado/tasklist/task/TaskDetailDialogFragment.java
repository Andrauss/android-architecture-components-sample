package com.daniloprado.tasklist.task;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daniloprado.tasklist.R;

public class TaskDetailDialogFragment extends DialogFragment {

    EditText editTextTitle;
    EditText editTextDescription;

    private Task task;

    interface TaskDialogListener {
        void onDialogSaveClick(Task task);
    }

    TaskDialogListener listener;

    public static TaskDetailDialogFragment newInstance(Task task) {
        TaskDetailDialogFragment fragment = new TaskDetailDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("task", task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            listener = (TaskDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement TaskDialogListener");
        }

        task = (Task) getArguments().getSerializable("task");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogfragment_task_detail, null);
        editTextTitle = (EditText) v.findViewById(R.id.edittext_task_title);
        editTextDescription = (EditText) v.findViewById(R.id.edittext_task_description);
        builder.setView(v)
                .setPositiveButton("Save", (dialog, id) -> {
                    //Do nothing here because we override this button later to change the close behaviour.
                    //However, we still need this because on older versions of Android unless we
                    //pass a handler the button doesn't get instantiated
                    //https://stackoverflow.com/questions/2620444/how-to-prevent-a-dialog-from-closing-when-a-button-is-clicked
                })
                .setNegativeButton("Cancel", (dialog, id) -> dismiss());
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog) getDialog();
        if(dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(view -> {
                if (!editTextTitle.getText().toString().isEmpty()
                        && !editTextDescription.getText().toString().isEmpty()) {
                    task.setTitle(editTextTitle.getText().toString());
                    task.setDescription(editTextDescription.getText().toString());
                    listener.onDialogSaveClick(task);
                    dismiss();
                } else {
                    if (editTextTitle.getText().toString().isEmpty()) {
                        editTextTitle.setError("A title is required");
                    }
                    if (editTextDescription.getText().toString().isEmpty()) {
                        editTextDescription.setError("A description is required");
                    }
                }
            });
        }
    }
}
