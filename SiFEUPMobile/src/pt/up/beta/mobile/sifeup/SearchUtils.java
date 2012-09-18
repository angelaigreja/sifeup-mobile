package pt.up.beta.mobile.sifeup;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.acra.ACRA;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.up.beta.mobile.datatypes.ResultsPage;
import pt.up.beta.mobile.datatypes.Student;
import pt.up.beta.mobile.sifeup.ResponseCommand.ERROR_TYPE;
import android.os.AsyncTask;

public class SearchUtils {
	private SearchUtils() {
	}

	public static AsyncTask<String, Void, ERROR_TYPE> getStudentsSearchReply(
			String query, int page, ResponseCommand command) {
		return new FetcherTask(command, new StudentsSearchParser())
				.execute(SifeupAPI.getStudentsSearchUrl(encode(query), page));
	}

	public static AsyncTask<String, Void, ERROR_TYPE> getSingleStudentSearchReply(
			String code, ResponseCommand command) {
		return new FetcherTask(new SingleStudentCom(command),
				new SingleStudentSearchParser()).execute(SifeupAPI
				.getStudentUrl(code));
	}

	public static ResultsPage getStudentsSearchReply(String query, int page) {
		final String res = SifeupAPI.getReply(SifeupAPI.getStudentsSearchUrl(
				encode(query), page));
		StudentsSearchParser parser = new StudentsSearchParser();
		return (ResultsPage) parser.parse(res);
	}

	private static String encode(String s) {
		try {
			return URLEncoder.encode(s.trim(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Parses a JSON String containing Exams info, Stores that info at
	 * Collection exams.
	 */

	private static class StudentsSearchParser implements ParserCommand {

		public Object parse(String page) {
			try {
				ResultsPage resultsPage = new ResultsPage();
				JSONObject jObject = new JSONObject(page);
				if (jObject.has("alunos")) {
					// new results page
					if (jObject.has("total"))
						resultsPage.setSearchSize(jObject.getInt("total"));
					if (jObject.has("primeiro"))
						resultsPage.setPage(jObject.getInt("primeiro"));
					if (jObject.has("tam_pagina"))
						resultsPage
								.setPageResults(jObject.getInt("tam_pagina"));
					if (resultsPage.getSearchSize() - resultsPage.getPage() < 15)
						resultsPage.setPageResults(resultsPage.getSearchSize()
								- resultsPage.getPage());
					JSONArray jArray = jObject.getJSONArray("alunos");

					// iterate over jArray
					for (int i = 0; i < jArray.length(); i++) {
						// new JSONObject
						JSONObject jStudent = jArray.getJSONObject(i);
						// new Block
						Student student = new Student();

						if (jStudent.has("codigo"))
							student.setCode("" + jStudent.getString("codigo"));
						if (jStudent.has("nome"))
							student.setName(jStudent.getString("nome"));
						if (jStudent.has("cur_sigla"))
							student.setProgrammeCode(jStudent
									.getString("cur_sigla"));
						if (jStudent.has("cur_nome"))
							student.setProgrammeName(jStudent
									.getString("cur_nome"));
						if (jStudent.has("cur_name"))
							student.setProgrammeNameEn(jStudent
									.getString("nome"));

						// add student to the page results
						resultsPage.getStudents().add(student);
					}
				}
				return resultsPage;
			} catch (JSONException e) {
				e.printStackTrace();
				ACRA.getErrorReporter().handleSilentException(e);
				ACRA.getErrorReporter().handleSilentException(
						new RuntimeException("Id:"
								+ AccountUtils.getActiveUserCode(null) + "\n\n" + page));
				
			}
			return null;
		}
	}

	private static class SingleStudentSearchParser implements ParserCommand {

		@Override
		public Object parse(String page) {
			try {
				Student me = new Student();
				JSONObject jObject = new JSONObject(page);
				SifeupUtils.removeEmptyKeys(jObject);
				if (jObject.has("codigo"))
					me.setCode(jObject.getString("codigo"));
				if (jObject.has("nome"))
					me.setName(jObject.getString("nome"));
				if (jObject.has("curso_sigla"))
					me.setProgrammeAcronym(jObject.getString("curso_sigla"));
				if (jObject.has("curso_nome"))
					me.setProgrammeName(jObject.getString("curso_nome"));
				if (jObject.has("ano_lect_matricula"))
					me.setRegistrationYear(jObject
							.getString("ano_lect_matricula"));
				if (jObject.has("estado"))
					me.setState(jObject.getString("estado"));
				if (jObject.has("ano_curricular"))
					me.setAcademicYear(jObject.getString("ano_curricular"));
				if (jObject.has("email"))
					me.setEmail(jObject.getString("email"));
				if (jObject.has("email_alternativo"))
					me.setEmailAlt(jObject.getString("email_alternativo"));
				if (jObject.has("telemovel"))
					me.setMobile(jObject.getString("telemovel"));
				if (jObject.has("telefone"))
					me.setTelephone(jObject.getString("telefone"));
				if (jObject.has("ramo"))
					me.setBranch(jObject.getString("ramo"));
				return me;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	private static class SingleStudentCom implements ResponseCommand {
		private final ResponseCommand com;

		public SingleStudentCom(final ResponseCommand com) {
			this.com = com;
		}

		public void onError(ERROR_TYPE error) {
			if (error == ERROR_TYPE.NETWORK)
				com.onResultReceived((Object[]) null);
			else
				com.onError(error);
		}

		public void onResultReceived(Object... results) {
			com.onResultReceived(results);
		}

	}
}
