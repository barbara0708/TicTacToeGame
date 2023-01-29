package com.example.tictactoegame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnReset, btnStartAgain;
    Button []buttons;
    TextView tvScoreOne, tvScoreTwo, tvWinner;
    boolean activePlayer=true;
    int scoreOne, scoreTwo, count;
    int gameState[]={2,2,2,2,2,2,2,2,2};
    int[][] winningPosition={
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findItems();
        buttons= new Button[]{btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
        for(Button btn : buttons){
            btn.setOnClickListener(this);
        }
        btnStartAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAgain();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
        activePlayer=true;
        scoreOne=0;
        scoreTwo=0;
        count=0;
    }

    private void reset() {
        scoreOne=0;
        scoreTwo=0;
        tvScoreOne.setText(String.valueOf(scoreOne));
        tvScoreTwo.setText(String.valueOf(scoreTwo));
        Toast.makeText(this, "Game is restarted", Toast.LENGTH_LONG).show();

    }

    void findItems(){
        btnReset=findViewById(R.id.btnReset);
        btnStartAgain=findViewById(R.id.btnStartAgain);
        btn1=findViewById(R.id.btn_1);
        btn2=findViewById(R.id.btn_2);
        btn3=findViewById(R.id.btn_3);
        btn4=findViewById(R.id.btn_4);
        btn5=findViewById(R.id.btn_5);
        btn6=findViewById(R.id.btn_6);
        btn7=findViewById(R.id.btn_7);
        btn8=findViewById(R.id.btn_8);
        btn9=findViewById(R.id.btn_9);
        tvScoreOne=findViewById(R.id.tvScoreOne);
        tvScoreTwo=findViewById(R.id.tvScoreTwo);
        tvWinner=findViewById(R.id.tvWinner);
    }
    @Override
    public void onClick(View view) {
        Button btn=(Button) view;
        if(!(btn.getText().toString().equals(""))){
            return;
        }
        String buttonID=view.getResources().getResourceEntryName(view.getId());
        int statePointer=Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));

        if(activePlayer){
            btn.setText("X");
            btn.setTextColor(Color.parseColor("#E13700"));
            gameState[statePointer-1]=0;
        }else {
            btn.setText("0");
            btn.setTextColor(Color.parseColor("#337CA0"));
            gameState[statePointer-1]=1;
        }
        count++;
        if(checkWinner()){
            if(activePlayer){
                scoreOne++;
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
            }else {
                scoreTwo++;
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
            }
            update();
            startAgain();
        }else if(count==9){
            reset();
            Toast.makeText(this, "No Winners", Toast.LENGTH_SHORT).show();
        }else {
            activePlayer=!activePlayer;
        }
    }
    public boolean checkWinner(){
        boolean winning=false;
        for (int i=0;i<8;i++){
                if(gameState[winningPosition[i][0]]==gameState[winningPosition[i][1]]&&gameState[winningPosition[i][1]]==gameState[winningPosition[i][2]]&&gameState[winningPosition[i][0]]!=2){
                    winning=true;
                }
        }
        return winning;
    }
    public void update(){
        tvScoreOne.setText(Integer.toString(scoreOne));
        tvScoreTwo.setText(Integer.toString(scoreTwo));
        if(scoreOne>scoreTwo){
            tvWinner.setText("Player one is winning!");
        }else if(scoreTwo>scoreOne){
            tvWinner.setText("Player two is winning!");
        }else{
            tvWinner.setText("Scores are equal");
        }
    }
    public void startAgain()
    {
        count=0;
        activePlayer=true;
        for(int i=0;i<buttons.length;i++){
            gameState[i]=2;
            buttons[i].setText("");
        }
    }
}