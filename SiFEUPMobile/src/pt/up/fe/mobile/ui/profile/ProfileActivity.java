package pt.up.fe.mobile.ui.profile;


import pt.up.fe.mobile.ui.BaseSinglePaneActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Profile Activity
 * @author Ângela Igreja
 */
public abstract class ProfileActivity extends BaseSinglePaneActivity {
	
    protected Fragment onCreatePane(String param) 
    {
    	if(param.equalsIgnoreCase("TEACHER"))
    		return new TeacherProfileFragment();
    	else
    		return new StudentProfileFragment();
    }
    
    /** Called when the activity is first created. */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getActivityHelper().setupSubActivity();
    }

}

