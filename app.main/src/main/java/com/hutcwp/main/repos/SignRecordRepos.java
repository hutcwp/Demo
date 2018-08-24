package com.hutcwp.main.repos;

import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.hutcwp.main.model.SignRecord;

import static net.wequick.small.Small.getContext;

public class SignRecordRepos {

    public static void createRecord(SignRecord record) {
        AVObject object = convertToAVObject(record);
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d("saved", "success!");
                    Toast.makeText(getContext(), "create success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "create failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private static AVObject convertToAVObject(SignRecord record) {
        AVObject object = new AVObject(TableNames.SIGN_RECORD);
        object.addUnique("date", record.getDate());
        object.put("endTime", record.getEndTime());
        object.put("startTime", record.getStartTime());
        return object;
    }

}
