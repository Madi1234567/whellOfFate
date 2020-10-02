package com.example.simplerndfrontend;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    TextView rez, nr1,nr2, rdn1,rdn2, rez_nr1,rez_nr2;
    Button btnShow, btnVerify;
    int count=0;
    int random1 ;
    int random2 ;
    int[] ap={0,0,0,0,0,0,0,0,0,0,0};
    int oldValue1,oldValue2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rez = (TextView) findViewById(R.id.res);
        nr1 = (TextView) findViewById(R.id.title);
        nr2 = (TextView) findViewById(R.id.counts1);
        rez_nr1 = (TextView) findViewById(R.id.title2);
        rez_nr2 = (TextView) findViewById(R.id.counts2);
        rdn1 = (TextView) findViewById(R.id.rdn1);
        rdn2 = (TextView) findViewById(R.id.rdn2);
        btnShow = (Button) findViewById(R.id.btn);
        btnVerify = (Button) findViewById(R.id.btnVerify);
        final TableLayout tableResults = (TableLayout)findViewById(R.id.table_layout1);
        final TableLayout tableResults2 = (TableLayout)findViewById(R.id.table_layout2);
        tableResults.setVisibility(View.INVISIBLE);
        tableResults2.setVisibility(View.VISIBLE);

        btnShow.setOnClickListener(new View.OnClickListener() {
            private AlertDialog dialog;
            @Override
            public void onClick(View v) {
                count++;
                btnShow.setText("choose team nr."+count);
                    if (count<=10)
                    {
                        random1 = (int)(Math.random() * 10 + 1);
                        random2 = (int)(Math.random() * 10 + 1);
                        rdn1.setText(String.valueOf(random1));
                        rdn2.setText(String.valueOf(random2));
                    }
                    else
                    {
                        dialog = new AlertDialog.Builder(MainActivity.this) // Pass a reference to your main activity here
                                .setTitle("The 2 weeks organise plan is ready")
                                .setMessage("Now you can reset the plan by pressing reset")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        count=0;
                                        for(int j=1;j<=10;j++)
                                        {
                                            ap[j]=0;
                                        }
                                        tableResults2.removeAllViews();
                                        dialog.cancel();
                                    }
                                })
                                .show();
                        rdn1.setText("-");
                        rdn2.setText("-");
                    }
                }


        });
        btnVerify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showResults(random1,random2);
                if(count==1) {
                    while (random1 == random2 ) {
                        random1 = (int) (Math.random() * 10 + 1);
                    }
                    showResults(random1, random2);
                    rdn1.setText(String.valueOf(random1));
                    oldValue1 = random1;
                    oldValue2 = random2;
                    for (int i = 1; i <= 10; i++) {
                        if (oldValue1 == i)
                            ap[oldValue1] = ap[oldValue1] + 1;
                        if (oldValue2 == i)
                            ap[oldValue2] = ap[oldValue2] + 1;
                    }
                    rez.setText(Arrays.toString(ap));
                    showResults2(oldValue1,oldValue2);
                }
                else
                {
                    while (random1 == random2 ||  ap[random2]>=2 ||  ap[random1]>=2) {
                        random1 = (int) (Math.random() * 10 + 1);
                        random2 = (int) (Math.random() * 10 + 1);

                    }
                    rdn1.setText(String.valueOf(random1));
                    rdn2.setText(String.valueOf(random2));
                    verifyConsecutiveDays();
                    showResults(random1,random2);
                    for (int i = 1; i<=10; i++) {
                        if (oldValue1 == i)
                            ap[oldValue1] = ap[oldValue1] + 1;
                        if (oldValue2 == i)
                            ap[oldValue2] = ap[oldValue2] + 1;
                    }
                    rez.setText(Arrays.toString(ap));
                    showResults2(oldValue1,oldValue2);
                }
            }

            private void verifyConsecutiveDays() {
                if(random1!=oldValue1 && random1!=oldValue2)
                    if(random2!=oldValue1 && random2!=oldValue2)
                    {
                        oldValue1=random1;
                        oldValue2=random2;
                    }
                else
                    {
                        while(random2==oldValue1 || random2==oldValue2 || ap[random2]>=2 )
                        {
                            random2=(int)(Math.random() * 10 + 1);
                        }
                        //showResults(random1,random2);
                        rdn2.setText(String.valueOf(random2));
                        if(random1!=random2 && random1!=oldValue1 && random1!=oldValue2)
                        {
                            oldValue1=random1;
                            oldValue2=random2;
                        }
                        else
                        {
                            while(random1==random2 || random2==oldValue2  || ap[random2]>=2)
                            {
                                random2=(int)(Math.random() * 10 + 1);
                            }
                            rdn2.setText(String.valueOf(random2));
                            oldValue2=random2;
                        }
                        oldValue1=random1;
                    }
                else
                {
                    while(random1==oldValue1 || random1==oldValue2 || ap[random1]>=2)
                    {
                        random1=(int)(Math.random() * 10 + 1);
                    }
                    rdn1.setText(String.valueOf(random1));
                    if(random1!=random2 && random2!=oldValue1 && random2!=oldValue2 )
                    {
                        oldValue1=random1;
                        oldValue2=random2;
                    }
                    else
                    {
                        while(random2==random1|| random1==oldValue1 || ap[random1]>=2)
                        {
                            random1=(int)(Math.random() * 10 + 1);
                        }
                        oldValue1=random1;
                        rdn1.setText(String.valueOf(random2));
                    }
                    oldValue2=random2;
                    rdn2.setText(String.valueOf(random2));
                }
            }


            private void showResults(int random1, int random2) {
                TableRow tr =  new TableRow(MainActivity.this);
                TextView c1 = new TextView(MainActivity.this);
                c1.setText(String.valueOf(random1));
                TextView c2 = new TextView(MainActivity.this);
                c2.setText(String.valueOf(random2));
                TextView c3 = new TextView(MainActivity.this);
                tr.addView(c1);
                tr.addView(c2);
                tableResults.addView(tr);
            }
            private void showResults2(int oldValue1, int oldValue2) {
                TableRow tr =  new TableRow(MainActivity.this);
                TextView c1 = new TextView(MainActivity.this);
                c1.setText(String.valueOf(random1));
                TextView c2 = new TextView(MainActivity.this);
                c2.setText(String.valueOf(random2));
                TextView c3 = new TextView(MainActivity.this);
                tr.addView(c1);
                tr.addView(c2);
                tableResults2.addView(tr);
            }
        });
    }
}
