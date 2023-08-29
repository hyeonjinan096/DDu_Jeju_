package com.lyj.direction.TMAP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TMapWebService
{
    private final String REVERSE_GEOCODING = "https://api2.sktelecom.com/tmap/geo/"
            + "reversegeocoding";
    private final String ROUTES = "https://api2.sktelecom.com/tmap/routes";

    private String mStrFullURI = "";
    private String mStrURI = "";

    public TMapWebService(String uri)
    {
        this.mStrURI = uri;
    }

    public void setParameters(String[] parametersName, String[] parametersData, int size)
    {
        for( int i = 0; i < size; i++ )
        {
            if( i == 0 )
            {
                mStrFullURI += mStrURI + "?" + parametersName[i] + "=" + parametersData[i];
                continue;
            }

            mStrFullURI += "&" + parametersName[i] + "=" + parametersData[i];
        }
    }

    public String connectWebService(String[] jsonKeys)
    {
        try
        {
            URL url = new URL(mStrFullURI);
            HttpURLConnection urlConnection = ( HttpURLConnection ) url.openConnection();
            urlConnection.setDoInput(true);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            JSONObject json = new JSONObject(getStringFromInputStream(in));

            String totalAddress = parseJSON(json, jsonKeys);

            return totalAddress;
        }
        catch( MalformedURLException e )
        {
            e.printStackTrace();
        }
        catch( JSONException e )
        {
            e.printStackTrace();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }

        return null;
    }

    private String getStringFromInputStream(InputStream inputStream)
    {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";

        try
        {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while( (line = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(line);
            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if( bufferedReader != null )
            {
                try
                {
                    bufferedReader.close();
                }
                catch( IOException e )
                {
                    e.printStackTrace();
                }
            }
        }

        return stringBuilder.toString();
    }

    private String parseJSON(JSONObject jsonObject, String[] arrKey) throws JSONException
    {
        if( REVERSE_GEOCODING.equals(mStrURI) )
        {
            JSONObject jsonAddress = jsonObject.getJSONObject(arrKey[0]);

            String arrAddress[] = new String[arrKey.length - 1];
            String strAddress = "";

            for(int i = 1; i < arrKey.length; i++)
            {
                arrAddress[i - 1] = jsonAddress.getString(arrKey[i]);

                strAddress += arrAddress[i - 1] + " ";
            }

            return strAddress;
        }
        else if( ROUTES.equals(mStrURI) )
        {
            JSONArray jsonArray = jsonObject.getJSONArray(arrKey[0]);
            JSONObject jsonFeatures = jsonArray.getJSONObject(0);
            JSONObject jsonProperties = jsonFeatures.getJSONObject(arrKey[1]);

            String strTime = jsonProperties.getString(arrKey[2]);

            return strTime;
        }

        return null;
    }
}