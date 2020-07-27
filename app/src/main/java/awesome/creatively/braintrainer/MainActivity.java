package awesome.creatively.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    TextView intro1;
    TextView intro2;
    ImageView intro3;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    TextView resultTextView;
    int score = 0;
    int numberOfQuestions = 0;
    TextView scoreTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    TextView sumTextView;
    TextView timerTextView;
    Button playAgainButton;
    ConstraintLayout gameLayout;
    boolean playing = true;

    public void playAgain(View view) {
        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        newQuestion();
        resultTextView.setText("");
        playing = true;

        playAgainButton.setVisibility(View.INVISIBLE);

        new CountDownTimer (30100, 1000) {

            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l/1000) + "s");
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Times Up!");
                playAgainButton.setVisibility(View.VISIBLE);
                playing = false;

                MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                mplayer.start();
            }
        }.start();
    }

    public void chooseAnswer(View view) {

        if (playing) {

            if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
                Log.i("Correct", "You got it!");
                resultTextView.setText("Correct!");
                score++;
            } else {
                Log.i("Wrong", ":/");
                resultTextView.setText("Wrong :(");
            }

            numberOfQuestions++;
            scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            newQuestion();
        }
    }

    public void start (View view) {
        resultTextView.setText("");
        goButton.setVisibility(view.INVISIBLE);
        intro1.setVisibility(view.INVISIBLE);
        intro2.setVisibility(view.INVISIBLE);
        intro3.setVisibility(view.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        playAgain(timerTextView);
    }

    public void newQuestion() {

        if(playing) {
            Random rand = new Random();

            int a = rand.nextInt(21);
            int b = rand.nextInt(21);

            sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

            locationOfCorrectAnswer = rand.nextInt(4);

            answers.clear();

            for (int i = 0; i < 4; i++) {
                if (i == locationOfCorrectAnswer) {
                    answers.add(a + b);
                } else {
                    int wrongAnswer = rand.nextInt(40);

                    while (wrongAnswer == a + b) {
                        wrongAnswer = rand.nextInt(40);
                    }
                    answers.add(wrongAnswer);
                }
            }

            button0.setText(Integer.toString(answers.get(0)));
            button1.setText(Integer.toString(answers.get(1)));
            button2.setText(Integer.toString(answers.get(2)));
            button3.setText(Integer.toString(answers.get(3)));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        gameLayout = findViewById(R.id.gameLayout);
        goButton = findViewById(R.id.goButton);
        intro1 = findViewById(R.id.intro1);
        intro2 = findViewById(R.id.intro2);
        intro3 = findViewById(R.id.intro3);

        goButton.setVisibility(View.VISIBLE);
        intro1.setVisibility(View.VISIBLE);
        intro2.setVisibility(View.VISIBLE);
        intro3.setVisibility(View.VISIBLE);

        intro2.setMovementMethod(LinkMovementMethod.getInstance());

        playAgainButton = findViewById(R.id.playAgainButton);

        sumTextView = findViewById(R.id.sumTextView);
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView= findViewById(R.id.timerTextView);
        gameLayout.setVisibility(View.INVISIBLE);

    }
}
