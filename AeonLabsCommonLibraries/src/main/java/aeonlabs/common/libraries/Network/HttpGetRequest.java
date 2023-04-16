package aeonlabs.common.libraries.Network;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import java.util.Observable;

public class HttpGetRequest {

    private Activity activity;
    public ObservableValue DelayedResult;
    private String getUrl;

    public class ObservableValue extends Observable
    {
        private HttpRequestHandler requestHandler;
        public ObservableValue(HttpRequestHandler requestHandler)
        {
            this.requestHandler = requestHandler;
        }
        public void setValue(HttpRequestHandler requestHandler)
        {
            this.requestHandler = requestHandler;
            setChanged();
            notifyObservers();
        }
        public HttpRequestHandler getValue()
        {
            return requestHandler;
        }
    }

    public HttpGetRequest(){
        this.DelayedResult = new ObservableValue(null);
    }

    @SuppressLint("StaticFieldLeak")
    public void load(String getUrl, Activity _activity){
        this.activity=_activity;
        this.getUrl=getUrl;

        final HttpGetRequestAsyncTask task = new HttpGetRequestAsyncTask(this.activity, this.getUrl);
        task.execute();
    }

    public class HttpGetRequestAsyncTask extends AsyncTask<Void, Void, HttpRequestHandler> {
        private final Activity activity;
        private final String url;

        public HttpGetRequestAsyncTask(final Activity _activity, String url) {
            this.activity = _activity;
            this.url=url;
        }

        protected HttpRequestHandler doInBackground(Void[] params) {
            HttpRequestHandler requestHandler = new HttpRequestHandler();
            requestHandler.sendGetRequest( url, this.activity);
            return requestHandler;
        }

        protected void onPostExecute(HttpRequestHandler requestHandler) {
            DelayedResult.setValue(requestHandler);
        }
    }
}