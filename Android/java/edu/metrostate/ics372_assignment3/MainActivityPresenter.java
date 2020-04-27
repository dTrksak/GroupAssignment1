package edu.metrostate.ics372_assignment3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;

public class MainActivityPresenter {
    MainActivityView view;

    public MainActivityPresenter(MainActivityView view){
        this.view = view;
    }

    public void textUpdated(String text){
        view.checkText(text);
    }

    public void launchOtherActivityClicked(Class activity ){
        view.launchOtherActivity(activity);
    }
    public void fileChooserOpened(Intent intent){
        view.fileOpener(intent);
    }
    public void printAllSelected(TextView view){
        view.setText(view.toString());
    }
}
