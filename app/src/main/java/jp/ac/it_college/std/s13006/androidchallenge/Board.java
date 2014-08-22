package jp.ac.it_college.std.s13006.androidchallenge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Board extends View implements GestureDetector.OnGestureListener{

    private final float mSize;
    private static final int HORIZONTAL_BLOCKS = 10;
    private static final int VERTICAL_BLOCKS = 20;
    static int[][] squares = new int[HORIZONTAL_BLOCKS][VERTICAL_BLOCKS];
    private Block[] blocks;
    private GestureDetector gd;
    public static List<Integer> CurrentX = new ArrayList<Integer>();
    public static List<Integer> CurrentY = new ArrayList<Integer>();
    private Handler handler = new Handler();
    public static boolean gameover = true;
    public static int score;

    {
        float density = getResources().getDisplayMetrics().density;
        float wp = getResources().getDisplayMetrics().widthPixels;
        mSize = (wp - 10 * density) * 2 / 3 / HORIZONTAL_BLOCKS;
        blocks = new Block[Tetrimino.KINDS + 2];
        blocks[0] = new Block(mSize, Color.BLACK, Color.LTGRAY);
        blocks[1] = new Block(mSize, Color.LTGRAY, Color.BLACK);
        blocks[2] = new Block(mSize, Color.CYAN, Color.BLACK);
        blocks[3] = new Block(mSize, Color.YELLOW, Color.BLACK);
        blocks[4] = new Block(mSize, Color.GREEN, Color.BLACK);
        blocks[5] = new Block(mSize, Color.RED, Color.BLACK);
        blocks[6] = new Block(mSize, Color.BLUE, Color.BLACK);
        blocks[7] = new Block(mSize, 0xfff08000, Color.BLACK);
        blocks[8] = new Block(mSize, 0xff800080, Color.BLACK);

    }

    public Board(Context context) {
        super(context);
        gd = new GestureDetector(context,this);
        initGame();
        score = 0;
    }

    public void initGame(){
        for (int i = 0; i < HORIZONTAL_BLOCKS; i++){
            for (int j = 0; j < VERTICAL_BLOCKS; j++){
                squares[i][j] = 0;
            }
        }
        createBlock();
        gameover = true;
        Game game = new Game();
        game.start();
    }

    class Game implements Runnable{
        private boolean mIsRunning = true;
        private Thread mThread;
        @Override
        public void run() {
            try {
                while(mIsRunning) {
                    mvDownBlock();
                    if (!gameover) stop();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            invalidate();
                        }
                    });
                    sleep(100 * difficulty.fallspeed);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void start(){
            if (mThread == null){
                mThread = new Thread(this, "unk");
                mIsRunning = true;
                mThread.start();
            }
        }
        public void stop(){
            mIsRunning = false;
            try {
                mThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mThread = null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.v("test","ddd");
        for (int i = 0; i < HORIZONTAL_BLOCKS; i++) {
            for (int j = 0; j < VERTICAL_BLOCKS; j++) {
                canvas.save();
                canvas.translate(i * mSize, j * mSize);
                blocks[squares[i][j]].onDraw(canvas);
                canvas.restore();
            }
        }
    }

    public static boolean RotateBlock(){
        int r = squares[CurrentX.get(1)][CurrentY.get(1)];

        try {
            switch (r) {
                case 2:
                    for (int i = CurrentX.get(0); i < CurrentX.size(); i++) {
                        for (int j = CurrentY.get(0); j < CurrentY.size(); j++) {
                            if (squares[i][j] != 1 && squares[j][i] != 1) {
                                squares[i][j] = squares[j][i];
                            }
                        }
                    }
                    break;
                case 3:
                    return false;
                case 4:
                case 5:
                    return false;
                case 6:
                case 7:
                case 8:
                    int[][] temp = new int[3][3];
                    int x = 0;
                    int y = 0;
                    for (int i = CurrentX.get(1)-1; i <= CurrentX.get(1)+1; i++){
                        for (int j = CurrentY.get(1)-1; j <= CurrentY.get(1)+1; j++){
                            if (squares[i][j] == 1) return false;
                            temp[x][y] = squares[i][j];
                            y++;
                        }
                        y=0;
                        x++;
                    }
                    x = 0; y = 0;
                    for (int i = CurrentX.get(1)-1; i <= CurrentX.get(1)+1; i++) {
                        for (int j = CurrentY.get(1) - 1; j <= CurrentY.get(1) + 1; j++) {
                            squares[i][j] = temp[2 - y][2 - x];
                            y++;
                        }
                        y=0;
                        x++;
                    }
                    break;
            }
            int X = CurrentX.get(0);
            int Y = CurrentY.get(0);
            CurrentX.clear();
            CurrentY.clear();
            CurrentX.add(X);
            CurrentY.add(Y);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 20; j++) {
                    if (CurrentX.get(0) != i && CurrentY.get(0) != j && squares[i][j] == r){
                        CurrentX.add(i);
                        CurrentY.add(j);
                    }
                }
            }
            return true;
        }catch (ArrayIndexOutOfBoundsException e){
            Log.v("test","hoge");
            return false;
        }
    }

    public void createBlock(){
        for (int i = 0; i < HORIZONTAL_BLOCKS; i++){
            if (squares[i][0] == 1){
                gameover = false;
                Intent intent = new Intent(getContext(), resultActivity.class);
                getContext().startActivity(intent);
            }
        }

        Random rand = new Random();
        int random = rand.nextInt(7);
        CurrentX.clear();
        CurrentY.clear();
        for(int i = 0; i < 4; i++){
            CurrentX.add(5);
            CurrentY.add(0);
        }

        squares = Tetrimino.CreateTetrimino(random , CurrentX , CurrentY);

        CurrentX.clear();
        CurrentY.clear();
        for(int i = 4; i < 9; i++){
            for(int j = 0; j < 4; j++){
                if(squares[i][j] == random+2){
                    CurrentX.add(i);
                    CurrentY.add(j);
                }
            }
        }
    }


    public static void createBlock(int r, List<Integer> CurrentX, List<Integer> CurrentY){
        squares = Tetrimino.MoveTetrimino(r, CurrentX, CurrentY);

        CurrentX.clear();
        CurrentY.clear();
        for(int i = 0; i < squares.length; i++){
            for(int j = 0; j < squares[i].length; j++){
                if(squares[i][j] > 1){
                    CurrentX.add(i);
                    CurrentY.add(j);
                }
            }
        }
    }

    public static boolean SlideCheck(int[][] squares, int mvX){
        for (int i = 0; i < 4; i++)
            if (squares[CurrentX.get(i) + mvX][CurrentY.get(i)] >= 1 &&
                    squares[CurrentX.get(i)][CurrentY.get(i)] != squares[CurrentX.get(i) + mvX][CurrentY.get(i)])
                return false;

        for (int i = 0; i < 4; i++){
            squares[CurrentX.get(i)][CurrentY.get(i)] = 0;
            CurrentX.set(i , CurrentX.get(i) + mvX);
        }

            return true;
    }

    public boolean DownCheck(int[][] squares, int mvY){
        try {
            for (int i = 0; i < 4; i++)
                if (squares[CurrentX.get(i)][CurrentY.get(i) + mvY] >= 1 &&
                        squares[CurrentX.get(i)][CurrentY.get(i)] != squares[CurrentX.get(i)][CurrentY.get(i) + mvY]){
                    return false;
                }
        }catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
        for (int i = 0; i < CurrentX.size(); i++){
            squares[CurrentX.get(i)][CurrentY.get(i)] = 0;
            CurrentY.set(i , CurrentY.get(i) + mvY);
        }
        return true;
    }

    public boolean mvDownBlock(){
        Log.v("test","down");
        int r = squares[CurrentX.get(0)][CurrentY.get(0)];
        try{
            score++;
            if (DownCheck(squares,1)){
                createBlock(r,CurrentX,CurrentY);
                return true;
            }else{
                r = 1;
                createBlock(r, CurrentX, CurrentY);
                LineCheck();
                createBlock();
            }

        }catch (ArrayIndexOutOfBoundsException e){
            Log.v("test","auau");
            return false;
        }
        return false;
    }

    public static void mvRightBlock(){
        Log.v("test","right");
        int r = squares[CurrentX.get(0)][CurrentY.get(0)];
        try {
            if (SlideCheck(squares, 1)) {
                createBlock(r, CurrentX, CurrentY);
                Log.v("test","bbb");
            }
        }catch (ArrayIndexOutOfBoundsException e){
            Log.v("test","auau");
        }

    }
    public static void mvLeftBlock(){
        Log.v("test","left");

        int r = squares[CurrentX.get(0)][CurrentY.get(0)];
        try {
            if (SlideCheck(squares, -1)) {
                createBlock(r, CurrentX, CurrentY);
                Log.v("test","aaa");
            }
        }catch (ArrayIndexOutOfBoundsException e){
            Log.v("test","auau");
        }

    }
    public void fallBlock(){
        score += 10;
        while (mvDownBlock());
    }

    public static void LineCheck(){
        Log.v("test","test");
        for (int i = 0; i < Tetrimino.height; i++) {
            int check = 0;
            for (int j = 0; j < Tetrimino.width;  j++) {
                if (squares[j][i] != 1) check = 1;
            }
            if (check == 0) {
                score += 100;
                for (int k = i; k > 0; k--){
                    for (int l = 0; l < Tetrimino.width; l++){
                        squares[l][k] = squares[l][k-1];
                    }
                }
                for (int o = 0; o < Tetrimino.width; o++) squares[0][o] = 0;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        return gd.onTouchEvent(e);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.v("test","hhh");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.v("test","ggg");
/*        if(RotateBlock()){
            int r = squares[CurrentX.get(0)][CurrentY.get(0)];
            Tetrimino.MoveTetrimino(r,CurrentX,CurrentY);
            invalidate();
        }*/
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent){
        Log.v("test","eee");
        fallBlock();
        invalidate();
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float x, float y) {
        Log.v("test","ccc");
        if (0 < x){
            mvRightBlock();
            invalidate();
        }else {
            mvLeftBlock();
            invalidate();
        }
        return true;
    }
}
