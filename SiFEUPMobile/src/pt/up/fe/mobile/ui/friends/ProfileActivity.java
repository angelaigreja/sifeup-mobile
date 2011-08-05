package pt.up.fe.mobile.ui.friends;


import pt.up.fe.mobile.ui.BaseSinglePaneActivity;
import pt.up.fe.mobile.ui.ProfileFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Profile Activity
 * @author angela
 */
public class ProfileActivity extends BaseSinglePaneActivity {
	
    protected Fragment onCreatePane() {
    	
        return new ProfileFragment();
    }
    
    /** Called when the activity is first created. */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getActivityHelper().setupSubActivity();
    }  

}
