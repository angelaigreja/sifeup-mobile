package pt.up.beta.mobile.ui.personalarea;

import java.util.List;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import pt.up.beta.mobile.datatypes.ScheduleBlock;
import pt.up.beta.mobile.datatypes.ScheduleRoom;
import pt.up.beta.mobile.datatypes.ScheduleTeacher;
import pt.up.beta.mobile.ui.BaseFragment;
import pt.up.beta.mobile.ui.facilities.FeupFacilitiesDetailsActivity;
import pt.up.beta.mobile.ui.facilities.FeupFacilitiesDetailsFragment;
import pt.up.beta.mobile.ui.profile.ProfileActivity;
import pt.up.beta.mobile.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Class Description Fragment
 * 
 * @author Ângela Igreja
 * 
 */
public class ClassDescriptionFragment extends BaseFragment {
	/**
	 * The key for the student code in the intent.
	 */
	final public static String BLOCK = "pt.up.fe.mobile.ui.studentarea.BLOCK";
	private ScheduleBlock block;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		block = (ScheduleBlock) getArguments().getParcelable(BLOCK);
		if ( block == null && savedInstanceState != null )
			block = savedInstanceState.getParcelable(BLOCK);
	}
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BLOCK, block);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View root = inflater.inflate(R.layout.class_description,
				getParentContainer(), true);

		// Subject
		TextView subject = (TextView) root.findViewById(R.id.class_subject);

		subject.setText(getString(R.string.class_subject,
				block.getLectureAcronym()));
		subject.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (getActivity() == null)
					return;
				Intent i = new Intent(getActivity(),
						SubjectDescriptionActivity.class);
				// assumed only one page of results
				i.putExtra(SubjectDescriptionFragment.SUBJECT_CODE,
						block.getLectureCode());
				i.putExtra(SubjectDescriptionFragment.SUBJECT_YEAR,
						block.getYear());
				i.putExtra(SubjectDescriptionFragment.SUBJECT_PERIOD,
						block.getSemester());
				i.putExtra(Intent.EXTRA_TITLE, block.getLectureAcronym());
				startActivity(i);

			}
		});

		// Teachers
		LinearLayout teachersContainer = (LinearLayout) root.findViewById(R.id.list_teachers);
		OnClickListener teacherClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				final ScheduleTeacher teacher = (ScheduleTeacher) v.getTag();
				if (teacher.getCode() == null)
					return;
				final Intent i = new Intent(getActivity(),
						ProfileActivity.class);
				i.putExtra(ProfileActivity.PROFILE_CODE, teacher.getCode());
				i.putExtra(ProfileActivity.PROFILE_TYPE,
						ProfileActivity.PROFILE_EMPLOYEE);
				i.putExtra(Intent.EXTRA_TITLE, teacher.getName());
				startActivity(i);
			}
		};
		List<ScheduleTeacher> teachers = block.getTeachers();
		for ( ScheduleTeacher teacher : teachers )
		{
		    TextView llItem = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, null);
		    llItem.setText(teacher.toString());
		    // To know wich item has been clicked
		    llItem.setTag(teacher);
		    // In the onClickListener just get the id using getTag() on the view
		    llItem.setOnClickListener(teacherClick);
		    teachersContainer.addView(llItem);
		}
		
		
		// Rooms
		LinearLayout roomsContainer = (LinearLayout) root.findViewById(R.id.list_rooms);
		OnClickListener roomClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				final ScheduleRoom room = (ScheduleRoom) v.getTag();
				Intent i = new Intent(getActivity(), ScheduleActivity.class);
				i.putExtra(ScheduleFragment.SCHEDULE_TYPE,
						ScheduleFragment.SCHEDULE_ROOM);
				i.putExtra(ScheduleFragment.SCHEDULE_CODE,
						room.getBuildingCode() + room.getRoomCode());
				i.putExtra(
						Intent.EXTRA_TITLE,
						getString(R.string.title_schedule_arg,
								room.toString()));

				startActivity(i);				
			}
		};
		List<ScheduleRoom> rooms = block.getRooms();
		for ( ScheduleRoom room : rooms )
		{
		    TextView llItem = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, null);
		    llItem.setText(room.toString());
		    // To know wich item has been clicked
		    llItem.setTag(room);
		    // In the onClickListener just get the id using getTag() on the view
		    llItem.setOnClickListener(roomClick);
		    roomsContainer.addView(llItem);
		}
		

		// Team
		TextView team = (TextView) root.findViewById(R.id.class_team);
		team.setText(getString(R.string.class_team, block.getClassAcronym()));

		// Start time
		int startTime = block.getStartTime();
		StringBuilder start = new StringBuilder();
		if (startTime / 3600 < 10)
			start.append("0");
		start.append(startTime / 3600);
		start.append(":");
		if (((startTime % 3600) / 60) < 10)
			start.append("0");
		start.append((startTime % 3600) / 60);
		TextView startT = (TextView) root.findViewById(R.id.class_start_time);
		startT.setText(getString(R.string.class_start_time, start.toString()));

		// End Time
		int endTime = (int) (block.getStartTime() + block.getLectureDuration() * 3600);
		StringBuilder end = new StringBuilder();
		if (endTime / 3600 < 10)
			end.append("0");
		end.append(endTime / 3600);
		end.append(":");
		if (((endTime % 3600) / 60) < 10)
			end.append("0");
		end.append((endTime % 3600) / 60);
		TextView endT = (TextView) root.findViewById(R.id.class_end_time);
		endT.setText(getString(R.string.class_end_time, end.toString()));

		showMainScreen();

		return getParentContainer();// mandatory
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.map_menu_items, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final ScheduleRoom room = block.getRooms().get(0);
		if (item.getItemId() == R.id.menu_map) {
			final Intent intent = new Intent(getActivity(),
					FeupFacilitiesDetailsActivity.class);
			intent.putExtra(FeupFacilitiesDetailsFragment.ROOM_EXTRA, room);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
