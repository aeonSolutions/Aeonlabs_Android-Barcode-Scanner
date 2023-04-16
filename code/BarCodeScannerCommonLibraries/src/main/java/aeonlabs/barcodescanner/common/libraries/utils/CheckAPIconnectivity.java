package aeonlabs.barcodescanner.common.libraries.utils;

import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import aeonlabs.common.libraries.Network.HttpGetRequest;
import aeonlabs.common.libraries.Network.HttpRequestHandler;
import aeonlabs.common.libraries.Network.NetworkUtils;
import aeonlabs.common.libraries.Network.SendData;
import aeonlabs.common.libraries.activities.ActivityBase;
import aeonlabs.common.libraries.data.SessionData;

public class CheckAPIconnectivity {
    private ActivityBase activity;
    private String CloudUrl;
    private int counter;
    private int responseCode;

    public CheckAPIconnectivity(ActivityBase activity, String CloudUrl){
        this.activity=activity;
        this.CloudUrl=CloudUrl;
    }

    public Boolean startCheck(){
        counter=0;
        responseCode=200;
        if(SessionData.Network.getNetworkStatus()==false){
            Toast.makeText(activity, "Web Cloud API address not accessible. Do you have internet ?", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            HttpGetRequest requestHandlerAeon = new HttpGetRequest();
            requestHandlerAeon.load(SessionData.Network.AeonLabsServicesServerBaseUrl + "/?bc=test", activity);
            HttpGetRequest requestHandlerCloud = new HttpGetRequest();
            requestHandlerCloud.load(this.CloudUrl + "/?bc=test", activity);

            HttpGetRequestResultObserver httpGetRequestResultObserverAeon = new HttpGetRequestResultObserver(requestHandlerAeon.DelayedResult);
            requestHandlerAeon.DelayedResult.addObserver(httpGetRequestResultObserverAeon);

            HttpGetRequestResultObserver httpGetRequestResultObserverCloud = new HttpGetRequestResultObserver(requestHandlerCloud.DelayedResult);
            requestHandlerCloud.DelayedResult.addObserver(httpGetRequestResultObserverCloud);
        }
        return true;
    }

    public class HttpGetRequestResultObserver implements Observer
    {
        private HttpGetRequest.ObservableValue ov = null;
        public HttpGetRequestResultObserver(HttpGetRequest.ObservableValue ov) {
            this.ov = ov;
        }
        public void update(Observable obs, Object obj) {
            if (obs == ov) {
                counter++;
                HttpRequestHandler requestHandlerResult= (HttpRequestHandler) ov.getValue();
                responseCode= requestHandlerResult.getResponseCode();
                if(counter==2){
                    String[] message= new String[2];
                    message[1]=String.valueOf(responseCode);
                    if (responseCode !=200){
                        message[0] ="API not found on servers !! Contact support.";
                    }else{
                        message[0] ="Connected to servers.";
                    }
                    activity.notifyObserversActivity(message);
                }
            }
        }
    }
}
