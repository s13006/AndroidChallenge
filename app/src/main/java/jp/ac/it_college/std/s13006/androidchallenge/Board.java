package jp.ac.it_college.std.s13006.androidchallenge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends View implements GestureDetector.OnGestureListener{

    private final float mSize;
    private static final int HORIZONTAL_BLOCKS = 10;
    private static final int VERTICAL_BLOCKS = 20;
    static int[][] squares;
    private Block[] blocks;
    private GestureDetector gd;
    public static List<Integer> CurrentX = new ArrayList<Integer>();
    public static List<Integer> CurrentY = new ArrayList<Integer>();

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

        //debug
        createBlock();
    }

    public Board(Context context) {
        super(context);
        gd = new GestureDetector(context,this);
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

    public static void RotateBlock(){

    }
    public static void createBlock(){
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
                if(squares[i][j] >= 1){
                    CurrentX.add(i);
                    CurrentY.add(j);
                }
            }
        }
    }
    public static void createBlock(int r, List<Integer> CurrentX, List<Integer> CurrentY){
        squares = Tetrimino.MoveTetrimino(r, CurrentX, CurrentY);


        for(int i = 4; i < 9; i++){
            for(int j = 0; j < 4; j++){
                if(squares[i][j] <= 1){
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

        Log.v("test","ora");
        for (int i = 0; i < 4; i++){
            squares[CurrentX.get(i)][CurrentY.get(i)] = 0;
            CurrentX.set(i , CurrentX.get(i) + mvX);
        }

            return true;
    }

    public static boolean DownCheck(int[][] squares, int mvY){
        try {
            for (int i = 0; i < 4; i++)
                if (squares[CurrentX.get(i)][CurrentY.get(i) + mvY] >= 1 &&
                        squares[CurrentX.get(i)][CurrentY.get(i)] != squares[CurrentX.get(i)][CurrentY.get(i) + mvY])
                    return false;
        }catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
        for (int i = 0; i < CurrentX.size(); i++){
            squares[CurrentX.get(i)][CurrentY.get(i)] = 0;
            CurrentY.set(i , CurrentY.get(i) + mvY);
        }
        return true;
    }

    public static void mvDownBlock(){
        Log.v("test","down");
        int r = squares[CurrentX.get(0)][CurrentY.get(0)];
        try{
            if (DownCheck(squares,1)){
                createBlock(r,CurrentX,CurrentY);
            }else{
                r = 1;
                createBlock(r,CurrentX,CurrentY);
                createBlock();
            }

        }catch (ArrayIndexOutOfBoundsException e){
            Log.v("test","auau");
        }
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

        int r = squares[CurrentX.get(0)][CurrentY.get(0)] ;
        try {
            if (SlideCheck(squares, -1)) {
                createBlock(r, CurrentX, CurrentY);
                Log.v("test","aaa");
            }
        }catch (ArrayIndexOutOfBoundsException e){
            Log.v("test","auau");
        }

    }
    public static void fallBlock(){

    }
    public static void putBlock(){
        Log.v("test","put");
        createBlock(1,CurrentX,CurrentY);
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
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent){
        Log.v("test","eee");
        mvDownBlock();
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
