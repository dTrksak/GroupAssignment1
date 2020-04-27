package edu.metrostate.ics372_assignment3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;

public interface MainActivityView {

    public void launchOtherActivity(Class activity);
    public void checkText(String string);
    public void fileOpener(Intent intent);
    public void printAll(TextView text);



}
