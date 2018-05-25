package mafafo.com.netfloristmobileapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class MainActivity extends AppCompatActivity {

    //declarations
    private TextView tv;
    private ImageView iv;


    //Connection url and
    private static final String url = "jdbc:mysql://127.0.0.1:3306/netfloristdb";
    private static final String user = "katlego";
    private static final String pass = "12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assigning
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim);

        //call methods
       // testDatabase();
       // logandregister();

        final Intent intent = new Intent(this, LoginActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
           timer.start();
    }

    public void testDatabase() {

        TextView textView = (TextView) this.findViewById(R.id.tv);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //load driver
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);

            String result = "Database connected success\n";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from orders");
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                result += rsmd.getColumnName(1) + ": " + rs.getString(1) + "\n";
                result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
                result += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
                result += rsmd.getColumnName(4) + ": " + rs.getString(4) + "\n";
            }
            textView.setText(result);
        } catch (Exception e) {
            e.printStackTrace();
            textView.setText(e.toString());
        }
    }
    public void logandregister(){
       // Button btnlog = (Button) this.findViewById(R.id.buttonlog);
        //Button btnreg = (Button) this.findViewById(R.id.buttonreg);
    }
}
