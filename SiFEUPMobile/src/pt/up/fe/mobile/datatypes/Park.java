package pt.up.fe.mobile.datatypes;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;
       
/**
 * Class Park Occupation
 * 
 * @author Ângela Igreja
 *
 */
@SuppressWarnings("serial")
public class Park implements Serializable 
{
	/** Free places in the park */
	private int places;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	private String name;

	public String getPlaces() {
		return Integer.toString(places);
	}
	
	public int getPlacesNumber() {
		return places;
	}

	public void setPlaces(int places) {
		this.places = places;
	}

	
	/**
	 *  Park Occupation Parser.
	 *  Returns true in case of correct parsing.
	 * 
	 * @param page
	 * @return itself
	 * @throws JSONException 
	 */
    public Park JSONParkOccupation(String page) throws JSONException
    {
    	JSONObject jObject = new JSONObject(page);
			
		if(jObject.has("lugares"))
			this.places = jObject.getInt("lugares");
		return this;

    }
    
}