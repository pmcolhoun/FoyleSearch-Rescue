package com.example.fsdemo.Activities;

/**
 *QuestionActivity retrieves the question data from the Firestore and displays each question to the
 * user in the format of: Question; option1, option2, option3 and option4. Animations are displayed
 * to the user for correct and wrong answers with a countdown timer displayed to the user counting
 * down from 10.
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fsdemo.Models.Question;
import com.example.fsdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.fsdemo.Activities.SetsActivity.category_id;


public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView question, qCount, timer;
    private Button option1, option2, option3, option4;
    private List<Question> questionsList;
    private CountDownTimer countdown;
    private int questionNum;
    private int score;
    private FirebaseFirestore firestore;
    private int setNo;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //Linking variables to UI design
        question = findViewById(R.id.question);
        qCount = findViewById(R.id.question_num);
        timer = findViewById(R.id.countdown);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        //Initialise loading dialog for progress bar
        loadingDialog = new Dialog(QuestionActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        //Initialise set number and set value to 1
        setNo = getIntent().getIntExtra("SETNO", 1);

        //Initialise Firestore instance
        firestore = FirebaseFirestore.getInstance();

        //Method to retrieve a List to store all of the questions
        getQuestionList();

        //Initialise user's score and set to 0 - updated as user progresses through questions
        score = 0;


    }//onCreate()


    private void getQuestionList(){
        //Initialise questionList
        questionsList = new ArrayList<>();

        //Accessing main collection that stores questions from each set
        firestore.collection("QUIZ").document("CAT" + String.valueOf(category_id))
                .collection("SET" + String.valueOf(setNo))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //checking if query was successful or failed
                if (task.isSuccessful()) {
                    QuerySnapshot questions = task.getResult();

                    //Loop to retrieve all questions from set and initialise question list
                    for (QueryDocumentSnapshot doc: questions){
                        questionsList.add(new Question(doc.getString("QUESTION"),
                                doc.getString("A"),
                                doc.getString("B"),
                                doc.getString("C"),
                                doc.getString("D"),
                                Integer.valueOf(doc.getString("ANSWER"))
                        ));
                    }//for

                    setQuestion();

                } else {
                    //If there is a failure loading data displays toast message to user
                    Toast.makeText(QuestionActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }//else
                //cancel loading progress bar once data is retrieved
                loadingDialog.cancel();
            }
        });


        /*
        //Hard coded questions stored locally on device
        questionsList.add(new Question("Question 1", "A", "B", "C", "D", 1));
        questionsList.add(new Question("Question 2", "A", "B", "C", "D", 2));
        questionsList.add(new Question("Question 3", "A", "B", "C", "D", 3));
        questionsList.add(new Question("Question 4", "A", "B", "C", "D", 4));
        questionsList.add(new Question("Question 5", "A", "B", "C", "D", 1));
         */

    }//getQuestionList()

    private void setQuestion(){
        //Assigning Timer countdown value
        timer.setText(String.valueOf(10));

        //Assigning values to Question TextView
        question.setText(questionsList.get(0).getQuestion());

        //Assigning values to Option TextView
        option1.setText(questionsList.get(0).getOptionA());
        option2.setText(questionsList.get(0).getOptionB());
        option3.setText(questionsList.get(0).getOptionC());
        option4.setText(questionsList.get(0).getOptionD());

        //Assigning Question count number
        qCount.setText(String.valueOf(1) + "/" + String.valueOf(questionsList.size()));

        startTimer();

        questionNum = 0;

    }//setQuestion()

    private void startTimer(){
        //Setting the value of the timer to 10 seconds and to update every second
        countdown = new CountDownTimer(12000, 1000) {
            //Time remaining
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished < 10000){
                    //Convert it from milliseconds to seconds
                    timer.setText(String.valueOf(millisUntilFinished / 1000));
                }

            }

            @Override
            public void onFinish() {
                changeQuestion();
            }
        };//CountDownTimer()

        countdown.start();

    }//startTimer()

    private void changeQuestion(){
        //if the question number is less than the size of the list
        if(questionNum < questionsList.size() - 1){

            questionNum++;

            //Using animation for questions
            playAnim(question, 0, 0);
            playAnim(option1, 0, 1);
            playAnim(option2, 0, 2);
            playAnim(option3, 0, 3);
            playAnim(option4, 0, 4);

            qCount.setText(String.valueOf(questionNum + 1) + "/" + String.valueOf(questionsList.size()));

            //Restart the timer
            timer.setText(String.valueOf(10));
            startTimer();

        }
        //at last question proceed to Score Activity and end Question Activity so user can not go back to questions
        else{
            Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
            //passing users score to the ScoreActivity
            intent.putExtra("SCORE", String.valueOf(score) + "/" + String.valueOf(questionsList.size()));
            startActivity(intent);
            QuestionActivity.this.finish();
        }
    }//changeQuestion()

    @Override
    public void onClick(View v) {
        int selectedOption = 0;

        switch (v.getId()){
            case R.id.option1:
                selectedOption = 1;
                break;

            case R.id.option2:
                selectedOption = 2;
                break;

            case R.id.option3:
                selectedOption = 3;
                break;

            case R.id.option4:
                selectedOption = 4;
                break;

            default:
        }

        //stops the timer when user selects a question
        countdown.cancel();
        checkAnswer(selectedOption, v);

    }//onClick()

    private void checkAnswer(int selectedOption, View view){
        //if the user selects the right answer
        if (selectedOption == questionsList.get(questionNum).getCorrectAns()){
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

            //increment users score if they select correct answer
            score++;
        }
        //if user selects wrong answer
        else{
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            //sets colour of correct question to green
            switch (questionsList.get(questionNum).getCorrectAns()){
                case 1:
                    option1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    option2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    option3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    option4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
            }
        }

        //Delays question change for 2 seconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        }, 2000);

    }//checkAnswer()

    private void playAnim(final View view, final int value, final int viewNum){
        //Animation to shrink button to size 0 and expand again for 0.5 seconds
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //update question text and expand question button back to original size
                        if(value == 0){
                            //retrieve value of next question from the List and assign it to the view
                            switch (viewNum){
                                case 0:
                                    ((TextView) view).setText(questionsList.get(questionNum).getQuestion());
                                    break;
                                case 1:
                                    ((Button) view).setText(questionsList.get(questionNum).getOptionA());
                                    break;
                                case 2:
                                    ((Button) view).setText(questionsList.get(questionNum).getOptionB());
                                    break;
                                case 3:
                                    ((Button) view).setText(questionsList.get(questionNum).getOptionC());
                                    break;
                                case 4:
                                    ((Button) view).setText(questionsList.get(questionNum).getOptionD());
                                    break;
                            }//switch
                            //reset background of buttons for next question
                            if(viewNum != 0){
                                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#a9a9a9")));
                            }

                            playAnim(view, 1, viewNum);
                        }//if
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

    }//playAnim()

}//class