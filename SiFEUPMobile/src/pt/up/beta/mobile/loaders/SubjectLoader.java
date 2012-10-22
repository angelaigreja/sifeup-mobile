/*
 * Copyright (C) 2011 The Android Open Source Project
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

package pt.up.beta.mobile.loaders;

import org.acra.ACRA;

import pt.up.beta.mobile.content.SigarraContract;
import pt.up.beta.mobile.datatypes.Subject;
import pt.up.beta.mobile.datatypes.SubjectFiles;
import pt.up.beta.mobile.sifeup.AccountUtils;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;

import com.google.gson.Gson;

/**
 * Static library support version of the framework's
 * {@link android.content.CursorLoader}. Used to write apps that run on
 * platforms prior to Android 3.0. When running on Android 3.0 or above, this
 * implementation is still used; it does not try to switch to the framework's
 * implementation. See the framework SDK documentation for a class overview.
 */
public class SubjectLoader extends AsyncTaskLoader<Subject> {
	final ForceLoadContentObserver mObserver;

	Uri mUri;
	String[] mProjection;
	String mSelection;
	String[] mSelectionArgs;
	String mSortOrder;

	Subject subject;
	Cursor mCursor;

	/* Runs on a worker thread */
	@Override
	public Subject loadInBackground() {
		Cursor cursor = getContext().getContentResolver().query(mUri,
				mProjection, mSelection, mSelectionArgs, mSortOrder);
		if (cursor != null) {
			// Ensure the cursor window is filled
			cursor.getCount();
			registerContentObserver(cursor, mObserver);

			if (mCursor != null && !mCursor.isClosed()) {
				mCursor.close();
			}
			mCursor = cursor;

			if (cursor.moveToFirst()) {
				try {
					final Gson gson = new Gson();
					final Subject subject = gson
							.fromJson(
									cursor.getString(cursor
											.getColumnIndex(SigarraContract.SubjectsColumns.CONTENT)),
									Subject.class);
					subject.setFiles(SubjectFiles.JSONSubjectContent(cursor.getString(cursor
							.getColumnIndex(SigarraContract.SubjectsColumns.FILES))));
					return subject;
				} catch (Exception e) {
					e.printStackTrace();
					ACRA.getErrorReporter().handleSilentException(e);
					ACRA.getErrorReporter()
							.handleSilentException(
									new RuntimeException(
											"Id:"
													+ AccountUtils
															.getActiveUserCode(null)
													+ "\n\n"
													+ cursor.getString(cursor
															.getColumnIndex(SigarraContract.SubjectsColumns.CONTENT))
													+ "\n\n"
													+ cursor.getString(cursor
															.getColumnIndex(SigarraContract.SubjectsColumns.FILES))));
				}
			}
		}
		return null;
	}

	/**
	 * Registers an observer to get notifications from the content provider when
	 * the cursor needs to be refreshed.
	 */
	void registerContentObserver(Cursor cursor, ContentObserver observer) {
		cursor.registerContentObserver(mObserver);
	}

	/* Runs on the UI thread */
	@Override
	public void deliverResult(Subject subject) {
		if (isReset()) {
			// An async query came in while the loader is stopped
			if (subject != null) {
				subject = null;
			}
			return;
		}
		this.subject = subject;
		if (isStarted()) {
			super.deliverResult(subject);
		}
	}

	/**
	 * Creates an empty unspecified CursorLoader. You must follow this with
	 * calls to {@link #setUri(Uri)}, {@link #setSelection(String)}, etc to
	 * specify the query to perform.
	 */
	public SubjectLoader(Context context) {
		super(context);
		mObserver = new ForceLoadContentObserver();
	}

	/**
	 * Creates a fully-specified CursorLoader. See
	 * {@link android.content.ContentResolver#query(Uri, String[], String, String[], String)
	 * ContentResolver.query()} for documentation on the meaning of the
	 * parameters. These will be passed as-is to that call.
	 */
	public SubjectLoader(Context context, Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		super(context);
		mObserver = new ForceLoadContentObserver();
		mUri = uri;
		mProjection = projection;
		mSelection = selection;
		mSelectionArgs = selectionArgs;
		mSortOrder = sortOrder;
	}

	/**
	 * Starts an asynchronous load of the contacts list data. When the result is
	 * ready the callbacks will be called on the UI thread. If a previous load
	 * has been completed and is still valid the result may be passed to the
	 * callbacks immediately.
	 * 
	 * Must be called from the UI thread
	 */
	@Override
	protected void onStartLoading() {
		if (subject != null) {
			deliverResult(subject);
		}
		if (takeContentChanged() || subject == null) {
			forceLoad();
		}
	}

	/**
	 * Must be called from the UI thread
	 */
	@Override
	protected void onStopLoading() {
		// Attempt to cancel the current load task if possible.
		cancelLoad();
	}

	@Override
	public void onCanceled(Subject subject) {
		if (mCursor != null && !mCursor.isClosed()) {
			mCursor.close();
		}
	}

	@Override
	protected void onReset() {
		super.onReset();

		// Ensure the loader is stopped
		onStopLoading();

		if (mCursor != null && !mCursor.isClosed()) {
			mCursor.close();
		}
		mCursor = null;
		subject = null;
	}
}