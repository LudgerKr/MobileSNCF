package com.example.sergio.enquete_rer.Controleur;

import android.os.AsyncTask;
import android.webkit.CookieManager;

import com.example.sergio.enquete_rer.StockSession;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.net.HttpURLConnection;

import javax.net.ssl.SSLHandshakeException;

public class Post extends AsyncTask<List, String, String> {

    private String adresse;
    private Controleur controleur;
    private String callback;

    public Post(String adresse, Controleur controleur, String callback) {

        this.adresse = adresse;
        this.controleur = controleur;
        this.callback = callback;
    }

    public void getCookie(URLConnection conn) {
        Map<String, List<String>> headerFields = conn.getHeaderFields();
        Set<String> headerFieldsSet = headerFields.keySet();
        Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();

        while (hearerFieldsIter.hasNext()) {

            String headerFieldKey = hearerFieldsIter.next();

            if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
                List<String> headerFieldValue = headerFields.get(headerFieldKey);
                for (String headerValue : headerFieldValue) {
                    System.out.println("Cookie Found...");
                    String[] fields = headerValue.split(";\\s*");
                    String cookieValue = fields[0];
                    String expires = null;
                    String path = null;
                    String domain = null;
                    boolean secure = false;

                    // Parse each field
                    for (int j = 1; j < fields.length; j++) {

                        if ("secure".equalsIgnoreCase(fields[j])) {
                            secure = true;
                        } else if (fields[j].indexOf('=') > 0) {
                            String[] f = fields[j].split("=");
                            if ("expires".equalsIgnoreCase(f[0])) {
                                expires = f[1];
                            } else if ("domain".equalsIgnoreCase(f[0])) {
                                domain = f[1];
                            } else if ("path".equalsIgnoreCase(f[0])) {
                                path = f[1];
                            }
                        }
                    }
                    if (cookieValue.split("=")[0].equals("PHPSESSID")) {
                        StockSession.PHPSESSID = cookieValue.split("=")[1];
                    }
                }
            }
        }
    }

    protected String doInBackground(List... args) {
        String adress = this.adresse;
        System.out.println("ADRESSE =====> "+adress);
        List<String> keys = args[0];
        List<String> values = args[1];
        String result = "";
        String cookies = "";
        OutputStreamWriter writer = null;
        BufferedReader reader = null;
        try {
            //encodage des paramètres de la requête
            String data="";
            for(int i=0;i<keys.size();i++){
                if (i!=0) data += "&";
                //publishProgress(keys.get(i)+"="+values.get(i));
                data += URLEncoder.encode(keys.get(i), "UTF-8")+"="+ URLEncoder.encode(values.get(i), "UTF-8");
            }

            CookieManager cookieManager = CookieManager.getInstance();

            //création de la connection
            URL url = new URL(adress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5 * 1000);
            if (StockSession.PHPSESSID != null) {
                conn.setRequestProperty("Cookie", "PHPSESSID="+StockSession.PHPSESSID);
            }

            try {
                //envoi de la requête
                writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(data);
                writer.flush();

                //lecture de la réponse
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    result += ligne;
                }
                //cookies = cookieManager.getCookie(adress);
                //publishProgress(cookies);
                this.getCookie(conn);
                //this.msg("Réponse serveur", result, context);
            } catch(SocketTimeoutException e) {
                return "{'rep': 'failed', 'errors': ['Connection timeout']}";
            } catch(SSLHandshakeException e) {
                return "{'rep': 'failed', 'errors': ['Incorrect TLS certificate']}";
            } catch (java.net.ConnectException e) {
                return "{'rep': 'failed', 'errors': ['Connection error']}";
            }
        }catch (Exception e) {
            //this.msg("Erreur :", e.toString(), context);
            e.printStackTrace();
        }finally{
            try{writer.close();}catch(Exception e){}
            try{reader.close();}catch(Exception e){}
            //this.msg("Finally", "Final", context);
        }
        return result;
    }

    protected void onProgressUpdate(String... msg) {
        this.controleur.msg("Progress", msg[0]);
    }

    protected void onPostExecute(String result) {
        switch(this.callback) {
            case "insertDataCallback":
                this.controleur.insertDataCallback(result);
                break;
        }
    }
}
