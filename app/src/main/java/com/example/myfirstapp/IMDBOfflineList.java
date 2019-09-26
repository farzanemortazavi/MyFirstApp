package com.example.myfirstapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.DialogTitle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myfirstapp.imdb.Search;

import java.util.List;

import static com.loopj.android.http.AsyncHttpClient.log;

public class IMDBOfflineList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imdboffline_list);

        final DatabaseHandler dbManager=new DatabaseHandler(IMDBOfflineList.this,"FirstAppDB",null,1);

        RecyclerView recylcler=findViewById(R.id.ImdbRecyclerOffline);

try {
    List<Search> lstMovie=dbManager.GetMovieList();
    log.d("myimdb","lstMovie size is "+lstMovie.size());
    IMDBAdapter adapter=new IMDBAdapter(lstMovie);
    recylcler.setAdapter(adapter);
    recylcler.setLayoutManager(new LinearLayoutManager(IMDBOfflineList.this,RecyclerView.VERTICAL,false));

}
catch (Exception e)
{
    log.d("myimdb","error");
    log.d("myimdb",e.getMessage());

}
  Button btnClearDB=findViewById(R.id.btnClearDatabase);
  btnClearDB.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
         boolean result= dbManager.ClearImdbDatabase();
         if(result){
             AlertDialog successDialog=new AlertDialog.Builder(IMDBOfflineList.this).create();
             successDialog.setTitle("Delete");
             successDialog.setMessage("Items are successfully deleted");
             successDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"Ok", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                 }
             });
             successDialog.show();
         }
         else {
             AlertDialog ErrorDialog=new AlertDialog.Builder(IMDBOfflineList.this).create();
             ErrorDialog.setTitle("Error");
             ErrorDialog.setMessage("There is an error in deleting items");
             ErrorDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"Ok", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                 }
             });
             ErrorDialog.show();
         }

      }
  });



    }
}
