package pt.up.beta.mobile.content;

import android.net.Uri;

public final class SigarraContract {

	public interface SubjectsColumns {
		String USER_NAME = SubjectsTable.COLUMN_USER_NAME;
		String CODE = SubjectsTable.COLUMN_CODE;
		String NAME_PT = SubjectsTable.COLUMN_NAME_PT;
		String NAME_EN = SubjectsTable.COLUMN_NAME_EN;
		String YEAR = SubjectsTable.COLUMN_YEAR;
		String PERIOD = SubjectsTable.COLUMN_PERIOD;
		String CONTENT = SubjectsTable.COLUMN_CONTENT;
		String FILES = SubjectsTable.COLUMN_FILES;
	}

	public interface ProfileColumns {
		String ID = ProfilesTable.KEY_ID_PROFILE;
		String CONTENT = ProfilesTable.KEY_CONTENT_PROFILE;
		String PIC = ProfilesTable.KEY_PROFILE_PIC;
	}

	public interface FriendsColumns {
		String ID = FriendsTable.KEY_ID_FRIEND;
		String USER_CODE = FriendsTable.KEY_USER_CODE;
		String CODE_FRIEND = FriendsTable.KEY_CODE_FRIEND;
		String NAME_FRIEND = FriendsTable.KEY_NAME_FRIEND;
		String COURSE_FRIEND = FriendsTable.KEY_COURSE_FRIEND;
	}

	public interface ExamsColumns {
		String ID = ExamsTable.KEY_ID_USER;
		String CONTENT = ExamsTable.KEY_CONTENT_EXAM;
	}

	public interface AcademicPathColumns {
		String ID = AcademicPathTable.KEY_ID_USER;
		String CONTENT = AcademicPathTable.KEY_CONTENT;
	}

	public interface TuitionColumns {
		String ID = TuitionTable.KEY_ID_USER;
		String CONTENT = TuitionTable.KEY_CONTENT;
	}

	public interface PrintingQuotaColumns {
		String ID = PrintingQuotaTable.KEY_ID_USER;
		String QUOTA = PrintingQuotaTable.KEY_QUOTA;
	}

	public interface ScheduleColumns {
		String CODE = ScheduleTable.KEY_ID;
		String CONTENT = ScheduleTable.KEY_CONTENT;
		String INITIAL_DAY = ScheduleTable.KEY_INITIAL_DAY;
		String FINAL_DAY = ScheduleTable.KEY_FINAL_DAY;
		String BASE_TIME = ScheduleTable.KEY_BASE_TIME;
		String TYPE = ScheduleTable.KEY_TYPE;
	}
	

	public interface NotificationsColumns {
		String CODE = NotificationsTable.KEY_ID_USER;
		String ID_NOTIFICATION = NotificationsTable.KEY_ID_NOTIFCATION;
		String CONTENT = NotificationsTable.KEY_NOTIFICATION;
		String STATE = NotificationsTable.KEY_STATE;
	}
	


	public interface CanteensColumns {
		String ID = CanteensTable.KEY_ID;
		String CONTENT = CanteensTable.KEY_CONTENT;
	}
	

	public interface LastSyncColumns {
		String ID = LastSyncTable.KEY_USER;
		String ACADEMIC_PATH = LastSyncTable.KEY_ACADEMIC_PATH;
		String CANTEENS = LastSyncTable.KEY_CANTEENS;
		String EXAMS = LastSyncTable.KEY_EXAMS;
		String NOTIFICATIONS = LastSyncTable.KEY_NOTIFICATIONS;
		String PRINTING = LastSyncTable.KEY_PRINTING;
		String PROFILES = LastSyncTable.KEY_PROFILES;
		String SCHEDULE = LastSyncTable.KEY_SCHEDULE;
		String SUBJECTS = LastSyncTable.KEY_SUBJECTS;
		String TUIION = LastSyncTable.KEY_TUITION;
	}

	public static final String CONTENT_AUTHORITY = "pt.up.fe.mobile.content.SigarraProvider";

	public static final Uri BASE_CONTENT_URI = Uri.parse("content://"
			+ CONTENT_AUTHORITY);

	static final String PATH_SUBJECTS = "subjects";
	static final String PATH_FRIENDS = "friends";
	static final String PATH_PROFILES = "profiles";
	static final String PATH_PROFILES_PIC = "profiles_pic";
	static final String PATH_EXAMS = "exams";
	static final String PATH_ACADEMIC_PATH = "academic_path";
	static final String PATH_TUITION = "tuition";
	static final String PATH_PRINTING = "printing_quota";
	static final String PATH_SCHEDULE = "schedules";
	static final String PATH_NOTIFICATIONS = "notifications";
	static final String PATH_CANTEENS = "canteens";
	static final String PATH_LAST_SYNC = "last_sync";

	/**
	 * The public contract for the subjects.
	 */
	public static class Subjects implements SubjectsColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_SUBJECTS).build();

		public static final String CONTENT_TYPE = "vnd.feup.cursor.dir/vnd.feup.subject";
		public static final String CONTENT_ITEM_TYPE = "vnd.feup.cursor.item/vnd.feup.subject";

		/** Default "ORDER BY" clause. */
		public static final String DEFAULT_SORT = PERIOD + " ASC, " + NAME_PT
				+ " ASC, " + NAME_EN + " ASC ";

		public static final String USER_SUBJECTS = USER_NAME + "=?";

		public static final String[] getUserSubjectsSelectionArgs(String code) {
			return new String[] { code };
		}

		public static final String SUBJECT_SELECTION = CODE + "=? AND "
				+ PERIOD + "=? AND " + YEAR + "=?";

		public static final String[] getSubjectsSelectionArgs(String code,
				String year, String period) {
			return new String[] { code, period, year };
		}
	}

	public static class Friends implements FriendsColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_FRIENDS).build();

		public static final String CONTENT_TYPE = "vnd.feup.cursor.dir/vnd.feup.subject";
		public static final String CONTENT_ITEM_TYPE = "vnd.feup.cursor.item/vnd.feup.subject";

		/** Default "ORDER BY" clause. */
		public static final String DEFAULT_SORT = NAME_FRIEND + " ASC ";

		public static final String USER_FRIENDS = USER_CODE + "=?";

		public static final String[] getUserFriendsSelectionArgs(String code) {
			return new String[] { code };
		}

		public static final String[] FRIENDS_COLUMNS = { CODE_FRIEND,
				NAME_FRIEND, COURSE_FRIEND };

		public static final String FRIEND_SELECTION = USER_CODE + "=? AND "
				+ CODE_FRIEND + "=?";

		public static final String[] getFriendSelectionArgs(String userCode,
				String code) {
			return new String[] { userCode, code };
		}
	}

	public static class Profiles implements ProfileColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_PROFILES).build();
		public static final Uri PIC_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_PROFILES_PIC).build();

		public static final String CONTENT_TYPE = "vnd.feup.cursor.dir/vnd.feup.profile";
		public static final String CONTENT_ITEM_TYPE = "vnd.feup.cursor.item/vnd.feup.profile";
		public static final String CONTENT_PIC = "image/jpg";

		public static final String PROFILE = ID + "=?";

		public static final String[] getProfileSelectionArgs(String code,
				String type) {
			return new String[] { code, type };
		}
	
		public static final String[] getProfilePicSelectionArgs(String code) {
			return new String[] { code };
		}

		public static final String[] PROFILE_COLUMNS = { CONTENT };
		public static final String[] PIC_COLUMNS = { PIC };


	}

	public static class Exams implements ExamsColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_EXAMS).build();

		public static final String CONTENT_TYPE = "vnd.feup.cursor.dir/vnd.feup.exams";
		public static final String CONTENT_ITEM_TYPE = "vnd.feup.cursor.item/vnd.feup.exam";

		public static final String PROFILE = ID + "=?";

		public static final String[] getExamsSelectionArgs(String code) {
			return new String[] { code };
		}

		public static final String[] COLUMNS = { CONTENT };

	}

	public static class AcademicPath implements AcademicPathColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_ACADEMIC_PATH).build();

		public static final String CONTENT_TYPE = "vnd.feup.cursor.dir/vnd.feup.academic_path";
		public static final String CONTENT_ITEM_TYPE = "vnd.feup.cursor.item/vnd.feup.academic_path";

		public static final String PROFILE = ID + "=?";

		public static final String[] getAcademicPathSelectionArgs(String code) {
			return new String[] { code };
		}

		public static final String[] COLUMNS = { CONTENT };

	}

	public static class Tuition implements TuitionColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_TUITION).build();

		public static final String CONTENT_TYPE = "vnd.feup.cursor.dir/vnd.feup.tuition";
		public static final String CONTENT_ITEM_TYPE = "vnd.feup.cursor.item/vnd.feup.tuition";

		public static final String PROFILE = ID + "=?";

		public static final String[] getTuitionSelectionArgs(String code) {
			return new String[] { code };
		}

		public static final String[] COLUMNS = { CONTENT };

	}

	public static class PrintingQuota implements PrintingQuotaColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_PRINTING).build();

		public static final String CONTENT_TYPE = "vnd.feup.cursor.dir/vnd.feup.printing_quota";
		public static final String CONTENT_ITEM_TYPE = "vnd.feup.cursor.item/vnd.feup.printing_quota";

		public static final String PROFILE = ID + "=?";

		public static final String[] getPrintingQuotaSelectionArgs(String code) {
			return new String[] { code };
		}

		public static final String[] COLUMNS = { QUOTA };

	}

	public static class Schedule implements ScheduleColumns, ScheduleTable.TYPE {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_SCHEDULE).build();

		public static final String CONTENT_TYPE = "vnd.feup.cursor.dir/vnd.feup.schedule";
		public static final String CONTENT_ITEM_TYPE = "vnd.feup.cursor.item/vnd.feup.schedule";

		public static final String SCHEDULE_SELECTION = CODE + "=? AND "
				+ INITIAL_DAY + "=? AND " + FINAL_DAY + "=? AND "
				+ ScheduleTable.KEY_TYPE + "=? ";


		public static final String[] getRoomScheduleSelectionArgs( String code, String initialDay,
				String finalDay, long mondayMillis) {
			return new String[] { code, initialDay,
					finalDay, ScheduleTable.TYPE.ROOM,
					Long.toString(mondayMillis) };
		}

		public static final String[] getStudentScheduleSelectionArgs(
				String code, String initialDay, String finalDay,
				long mondayMillis) {
			return new String[] { code, initialDay, finalDay,
					ScheduleTable.TYPE.STUDENT, Long.toString(mondayMillis) };
		}

		public static final String[] getEmployeeScheduleSelectionArgs(
				String code, String initialDay, String finalDay,
				long mondayMillis) {
			return new String[] { code, initialDay, finalDay,
					ScheduleTable.TYPE.EMPLOYEE, Long.toString(mondayMillis) };
		}

		public static final String[] getUCScheduleSelectionArgs(String code,
				String initialDay, String finalDay, long mondayMillis) {
			return new String[] { code, initialDay, finalDay,
					ScheduleTable.TYPE.UC, Long.toString(mondayMillis) };
		}

		public static final String[] COLUMNS = { CONTENT, BASE_TIME };

	}
	

	public static class Notifcations implements NotificationsColumns, NotificationsTable.STATE {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_NOTIFICATIONS).build();

		public static final String CONTENT_TYPE = "vnd.feup.cursor.dir/vnd.feup.notifications";
		public static final String CONTENT_ITEM_TYPE = "vnd.feup.cursor.item/vnd.feup.notifications";

		public static final String PROFILE = CODE + "=?";

		public static final String[] getNotificationsSelectionArgs(String code) {
			return new String[] { code };
		}
		
		public static final String DEFAULT_SORT = STATE + " ASC ";

		
		public static final String UPDATE_NOTIFICATION = CODE + "=? AND " + ID_NOTIFICATION + "=?";

		public static final String[] getNotificationsSelectionArgs(String code, String id) {
			return new String[] { code, id };
		}

		public static final String[] COLUMNS = { CONTENT, STATE };

	}
	

	public static class Canteens implements CanteensColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_CANTEENS).build();

		public static final String CONTENT_TYPE = "vnd.feup.cursor.dir/vnd.feup.canteens";
		public static final String CONTENT_ITEM_TYPE = "vnd.feup.cursor.item/vnd.feup.canteens";

		public static final String DEFAULT_ID = CanteensTable.DEFAULT_ID;
		
		public static final String[] COLUMNS = { CONTENT };

	}
	
	public static class LastSync implements LastSyncColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_LAST_SYNC).build();

		public static final String CONTENT_TYPE = "vnd.feup.cursor.dir/vnd.feup.last_sync";
		public static final String CONTENT_ITEM_TYPE = "vnd.feup.cursor.item/vnd.feup.last_sync";

		public static final String PROFILE = ID + "=?";
		
		public static final String[] COLUMNS = null;
		
		public static final String[] getLastSyncSelectionArgs(String code) {
			return new String[] { code };
		}

	}



	private SigarraContract() {
	}
}