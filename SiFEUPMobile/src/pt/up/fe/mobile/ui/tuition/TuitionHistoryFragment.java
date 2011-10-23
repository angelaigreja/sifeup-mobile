/*
 * Copyright 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pt.up.fe.mobile.ui.tuition;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import pt.up.fe.mobile.R;
import pt.up.fe.mobile.service.SessionManager;
import pt.up.fe.mobile.service.SifeupAPI;
import pt.up.fe.mobile.service.YearsTuition;
import pt.up.fe.mobile.tracker.AnalyticsUtils;
import pt.up.fe.mobile.ui.BaseActivity;
import pt.up.fe.mobile.ui.BaseFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TuitionHistoryFragment extends BaseFragment implements OnItemClickListener {

    private ListView list;
    private SimpleAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnalyticsUtils.getInstance(getActivity()).trackPageView("/TuitionHistory");
    }
    
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View root = inflater.inflate(R.layout.generic_list, getParentContainer(), true);
		list = (ListView) root.findViewById(R.id.generic_list);
		new TuitionTask().execute();
		return getParentContainer(); //this is mandatory.
	 }
	
    private void loadList()
    {
    	String[] from = new String[] {"year", "paid", "to_pay"};
        int[] to = new int[] { R.id.tuition_history_year, R.id.tuition_history_paid, R.id.tuition_history_to_pay};
	    //prepare the list of all records
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
         
        for(YearsTuition y: SessionManager.tuitionHistory.getHistory()){
            HashMap<String, String> map = new HashMap<String, String>();
            
            map.put("year", getString(R.string.lbl_year)+" "+y.getYear());
            map.put("paid", getString(R.string.lbl_paid)+": "+y.getTotal_paid()+"€");
            if(y.getTotal_in_debt()>0.0)
            	map.put("to_pay", getString(R.string.lbl_still_to_pay)+": "+y.getTotal_in_debt()+"€");
            fillMaps.add(map);
        }
		 
        // fill in the grid_item layout
        adapter = new SimpleAdapter(getActivity(), fillMaps, R.layout.list_item_tuition_history, from, to);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        Log.i("Propinas", "List view loaded successfully");
    }
    
	 /** Classe privada para a busca de dados ao servidor */
    private class TuitionTask extends AsyncTask<Void, Void, String> {

    	protected void onPreExecute (){
    		showLoadingScreen();
    	}

        protected void onPostExecute(String result) {
        	if ( !result.equals("") )
        	{
				Log.i("Propinas","Propinas Loaded successfully");
	            loadList();
	            showMainScreen();

    		}
			else
			{	
				Log.e("Propinas","Data not loaded or parsed correctly");
				if ( getActivity() != null ) 
				{
					getActivity().removeDialog(BaseActivity.DIALOG_FETCHING);
					Toast.makeText(getActivity(), getString(R.string.toast_auth_error), Toast.LENGTH_LONG).show();
					((BaseActivity)getActivity()).goLogin(true);
					return;
				}
			}
        	if ( getActivity() != null) 
        		getActivity().removeDialog(BaseActivity.DIALOG_FETCHING);
        }

		@Override
		protected String doInBackground(Void ... theVoid) {
			try {
			  		if(!SessionManager.tuitionHistory.isLoaded())
			  		{
			  			String page = "";
		    			page = SifeupAPI.getTuitionReply(
									SessionManager.getInstance().getLoginCode());
		    			int error =	SifeupAPI.JSONError(page);
			    		switch (error)
			    		{
			    		case SifeupAPI.Errors.NO_AUTH: return "";
			    		}
						
			    		JSONObject jHistory=new JSONObject(page);
			    		
			    		if(SessionManager.tuitionHistory.load(jHistory))
			    			return "Sucess";
			    		else
			    			return "";			    		
			  		}
			  		else
			  			return "Sucess";
				
				} catch (JSONException e) {
					if ( getActivity() != null ) 
						Toast.makeText(getActivity(), "F*** JSON", Toast.LENGTH_LONG).show();
					e.printStackTrace();
					return "";
				}
		}
    }

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
    	SessionManager.tuitionHistory.setSelected_year(position);
    	startActivity(new Intent(getActivity(), TuitionActivity.class));		
	}	
}
