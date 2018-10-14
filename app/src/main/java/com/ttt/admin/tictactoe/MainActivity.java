package com.ttt.admin.tictactoe;

import android.graphics.Color; 
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons = new Button[3][3];

    private boolean player1turn = true;

    private int roundcount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPlayer1 = findViewById(R.id.text_view_1);
        textViewPlayer2 = findViewById(R.id.text_view_2);

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")){
            return;
        }
        if (player1turn){
            ((Button) view).setText("X");
            ((Button) view).setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(getResources().getColor(R.color.colorAccent));
        }
        roundcount++;

        if (checkForWin()){
            if (player1turn){
                player1Wins();
            }else {
                player2Wins();
            }
        }else if (roundcount == 9){
            draw();
        }else {
            player1turn = !player1turn;
        }


    }




    private boolean checkForWin(){
        String [][] field = new String[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                field [i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++){
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")){
                return true;
            }
        }
        for (int i = 0; i < 3; i++){
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")){
                return true;
            }
        }


            if (field[0][0].equals(field[1][1])
                    && field[0][0].equals(field[2][2])
                    && !field[0][0].equals("")){
                return true;
            }


            if (field[0][2].equals(field[1][1])
                    && field[0][2].equals(field[2][0])
                    && !field[0][2].equals("")){
                return true;
            }
            return false;

    }
    private void player1Wins() {
        player1Points++;
        //Toast.makeText(this,"Player 1 Wins!",Toast.LENGTH_SHORT).show();
        getAlertMessage(this,"Player 1 Wins!");
        updatePointsText();
        resetBoard();
    }
    private void player2Wins() {
        player2Points++;
        //Toast.makeText(this,"Player 2 Wins!",Toast.LENGTH_SHORT).show();
        getAlertMessage(this,"Player 2 Wins!");
        updatePointsText();
        resetBoard();
    }
    private void draw() {
        //Toast.makeText(this,"Draw!",Toast.LENGTH_SHORT).show();
        getAlertMessage(this,"Match is Draw!");
        resetBoard();
    }


    private void getAlertMessage(MainActivity mainActivity,  String s) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mainActivity);
        String titleText = s;
        builder1.setCancelable(true);
        /*ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.BLUE);
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
*/

        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1f), 0,14, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 14, 0);// set color
        ss1.setSpan(new BackgroundColorSpan(Color.WHITE),0,0,0);

        // Set the alert dialog title using spannable string builder
        builder1.setTitle(ss1);
        builder1.setPositiveButton("Close",null);
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }



    private void updatePointsText(){
        textViewPlayer1.setText("Player 1 :"+player1Points);
        textViewPlayer2.setText("Player 2 :"+player2Points);

    }
    private void resetBoard(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }
        roundcount = 0;
        player1turn = true;
    }
    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount",roundcount);
        outState.putInt("player1Points",player1Points);
        outState.putInt("player2Points",player2Points);
        outState.putBoolean("player1Turn",player1turn);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundcount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Count");
        player2Points = savedInstanceState.getInt("player2Count");
        player1turn = savedInstanceState.getBoolean("player1Turn");
    }



}
