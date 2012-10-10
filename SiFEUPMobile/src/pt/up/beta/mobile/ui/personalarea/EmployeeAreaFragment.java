package pt.up.beta.mobile.ui.personalarea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.up.beta.mobile.R;
import pt.up.beta.mobile.tracker.AnalyticsUtils;
import pt.up.beta.mobile.ui.BaseActivity;
import pt.up.beta.mobile.ui.profile.ProfileActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class EmployeeAreaFragment extends ListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AnalyticsUtils.getInstance(getActivity()).trackPageView(
				"/Employee Area");
		String[] from = new String[] { "title" };
		int[] to = new int[] { R.id.list_menu_title };

		// prepare the list of all records
		List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> profile = new HashMap<String, String>();
		profile.put(from[0], getString(R.string.btn_profile));
		fillMaps.add(profile);

		HashMap<String, String> schedule = new HashMap<String, String>();
		schedule.put(from[0], getString(R.string.btn_schedule));
		fillMaps.add(schedule);

		HashMap<String, String> food = new HashMap<String, String>();
		food.put(from[0], getString(R.string.btn_lunch_menu));
		fillMaps.add(food);

		HashMap<String, String> park = new HashMap<String, String>();
		park.put(from[0], getString(R.string.btn_park_occupation));
		fillMaps.add(park);

		// fill in the grid_item layout
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), fillMaps,
				R.layout.list_item_menu, from, to);
		setListAdapter(adapter);

	}

	@Override
	public void onStart() {
		super.onStart();
		registerForContextMenu(getActivity().findViewById(android.R.id.list));
	}

	/** {@inheritDoc} */
	public void onListItemClick(ListView l, View v, int position, long id) {

		final BaseActivity activity = (BaseActivity) getActivity();
		switch (position) {
		case 0:
			activity.openActivityOrFragment(new Intent(getActivity(),
					ProfileActivity.class).putExtra(
					ProfileActivity.PROFILE_TYPE,
					ProfileActivity.PROFILE_EMPLOYEE));
			return;
		case 1:
			activity.openActivityOrFragment(new Intent(getActivity(),
					ScheduleActivity.class).putExtra(
					ScheduleFragment.SCHEDULE_TYPE,
					ScheduleFragment.SCHEDULE_EMPLOYEE));
			return;
		case 2:
			activity.openActivityOrFragment(new Intent(getActivity(),
					LunchMenuActivity.class));
			return;
		case 3:
			activity.openActivityOrFragment(new Intent(getActivity(),
					ParkOccupationActivity.class));
			return;
		}
	}

}
