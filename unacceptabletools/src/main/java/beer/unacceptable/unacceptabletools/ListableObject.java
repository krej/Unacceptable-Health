package beer.unacceptable.unacceptabletools;

import android.net.Network;

/*import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;*/
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * Created by Megatron on 10/4/2017.
 */

public class ListableObject {
    @Expose
    public String name = "Empty"; //Used to store 'Empty'
    public String idString;
    //protected String ClassName = ""; //Used for the API to know what URL to request to. Could maybe be replaced with just getting the class name later.

    public void Save() {

        //String sRecipeURL = Tools.RestAPIURL() + "/" + ClassName.toLowerCase() + "/";// "/recipe/";
        //the one below is the one copied from brewzilla but im commenting it out because Tools.RestAPIURL isnt a thing
        //String sRecipeURL = Tools.RestAPIURL() + "/" + this.getClass().getSimpleName().toLowerCase() + "/";// "/recipe/";
        String sRecipeURL = "";
        if ( idString != null && idString.length() > 0 ) {
            sRecipeURL += idString;
        }
/*
        Network.WebRequest(Request.Method.POST, sRecipeURL, BuildRestData(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // your response

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String response = error.getMessage();
                        response += "";
                    }
                });*/
    }

    protected byte[] BuildRestData() {
        //GsonBuilder gsonBuilder = new GsonBuilder().setExclusionStrategies(new JsonExclusion());
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(this);
        return json.getBytes();
    }

}
