package pt.up.fe.mobile.ui.studentservices;

import pt.up.fe.mobile.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import external.com.google.android.apps.iosched.util.AnalyticsUtils;

public class ChangePasswordFragment extends Fragment
{
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
       super.onCreate(savedInstanceState);
       AnalyticsUtils.getInstance(getActivity()).trackPageView("/Change Password");
    }
	 
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) 
	{
		//ViewGroup root = (ViewGroup) inflater.inflate(R.layout.list_ucs_inscriptions, null);
		
		return  super.onCreateView(inflater, container, savedInstanceState);
	} 
	    
	      
}
