package com.example.myfirstapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfirstapp.imdb.ImdbClass;
import com.example.myfirstapp.imdb.Search;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class IMDBSearchActivity extends AppCompatActivity {
    List<Search> result=new ArrayList<Search>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imdbsearch);

        final EditText edtImdb=findViewById(R.id.edtMovieName);
        Button btnImdbSerach=findViewById(R.id.btnImdbSearch);
        final RecyclerView recyle=findViewById(R.id.ImdbRecycler);
        Button btnImdbSave=findViewById(R.id.btnImdbSave);

        final DatabaseHandler dbManager=new DatabaseHandler(IMDBSearchActivity.this,"FirstAppDB",null,1);




        btnImdbSerach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                //String strSearch=edtImdb.getText().toString().replace("\\s+", "+");
                String strSearch1=edtImdb.getText().toString();

                //String url="http://omdbapi.com/?t="+strSearch+"&apikey=70ad462a";
                String url1="http://omdbapi.com/?s="+strSearch1+"&apikey=70ad462a";

                client.get(url1,new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Gson gson=new Gson();
                        ImdbClass imdb = gson.fromJson(response.toString(), ImdbClass.class);
                        result=imdb.getSearch();

                        IMDBAdapter adapter=new IMDBAdapter(result);
                        recyle.setAdapter(adapter);
                        recyle.setLayoutManager(new LinearLayoutManager(IMDBSearchActivity.this,RecyclerView.VERTICAL,false));

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                        log.d("myTag",throwable.getMessage());
                    }
                });

            }
        });


        btnImdbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(IMDBSearchActivity.this,"Button click",Toast.LENGTH_LONG);
                log.d("myimdb","button click");

                try {


                    for (int i = 0; i < result.size(); i++) {
                        boolean isExist=dbManager.isExistMovie(result.get(i).getImdbID());
                        if(!isExist)
                        {
                            boolean b = dbManager.insertMovie(result.get(i));
                            if (!b) {
                                log.d("myimdb", "error in saving "+result.get(i).getTitle());

                            }
                        }
                        else{
                            log.d("myimdb", result.get(i).getTitle()+" is Exist");
                        }
                    }//

                    AlertDialog saveDialog = new AlertDialog.Builder(IMDBSearchActivity.this).create();
                    saveDialog.setTitle("Offline Save");
                    saveDialog.setMessage("Items are successfully saved to database!");
                    saveDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    saveDialog.show();
                }
                catch (Exception e)
                {
                    AlertDialog errorDialog = new AlertDialog.Builder(IMDBSearchActivity.this).create();
                    errorDialog.setTitle("Error");
                    errorDialog.setMessage("There is an error in adding items to database!");
                    errorDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    errorDialog.show();
                }

            }
        });


    }
}
