package jp.ac.it_college.std.s13006.androidchallenge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class Board extends View implements GestureDetector.OnGestureListener{

    private final float mSize;
    private static final int HORIZONTAL_BLOCKS = 10;
    private static final int VERTICAL_BLOCKS = 20;
    static int[][] squares;
    static int posX = Tetrimino.posX;
    static int posY = Tetrimino.posY;
    private Block[] blocks;
    private GestureDetector gd;


    {
        float density = getResources().getDisplayMetrics().density;
        float wp = getResources().getDisplayMetrics().widthPixels;
        mSize = (wp - 10 * density) * 2 / 3 / HORIZONTAL_BLOCKS;
        blocks = new Block[Tetrimino.KINDS + 1];
        blocks[0] = new Block(mSize, Color.BLACK, Color.LTGRAY);
        blocks[1] = new Block(mSize, Color.CYAN, Color.BLACK);
        blocks[2] = new Block(mSize, Color.YELLOW, Color.BLACK);
        blocks[3] = new Block(mSize, Color.GREEN, Color.BLACK);
        blocks[4] = new Block(mSize, Color.RED, Color.BLACK);
        blocks[5] = new Block(mSize, Color.BLUE, Color.BLACK);
        blocks[6] = new Block(mSize, 0xfff08000, Color.BLACK);
        blocks[7] = new Block(mSize, 0xff800080, Color.BLACK);
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
        posX = Tetrimino.posX;
        posY = Tetrimino.posY;
        squares = Tetrimino.CreateTetrimino(random , posX , posY);
    }
    public static void createBlock(int r, int posX , int posY){
        squares = Tetrimino.CreateTetrimino(r , posX , posY);
    }

    public static boolean Check(int mvX,int mvY){
        if(squares[mvX + (posX - mvX)][mvY + (posY - mvY)] != 0){
            return false;
        }
        return true;
    }

    public static void mvDownBlock(){
    }
    public static void mvRightBlock(){
        posX++;
        if (Check(posX,posY)) {
            int r = squares[posX][posY] - 1;
            createBlock(r, posX, posY);
        }else{
            posX--;
        }
        Log.v("test","bbb");

    }
    public static void mvLeftBlock(){
        posX--;
        if (Check(posX,posY)) {
            int r = squares[posX][posY] - 1;
            createBlock(r, posX, posY);
        }else{
            posX++;
        }
        Log.v("test","aaa");

    }
    public static void fallBlock(){

    }
    public static void putBlock(){

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
        Log.v("test","fff");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.v("test","eee");
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
