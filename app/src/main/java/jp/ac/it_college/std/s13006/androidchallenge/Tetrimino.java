package jp.ac.it_college.std.s13006.androidchallenge;

import java.util.List;

public class Tetrimino {
    public static final int KINDS = 7;
    public static int width = 10;
    public static int height = 20;
    public static int[][] mino = new int[width][height];

    public static int[][] CreateTetrimino(int random, List<Integer> X , List<Integer> Y){


        switch (random){
            //I
            case 0:
                mino[X.get(0)][Y.get(0)] = 2;
                mino[X.get(1)+1][Y.get(1)] = 2;
                mino[X.get(2)+2][Y.get(2)] = 2;
                mino[X.get(3)+3][Y.get(3)] = 2;
                break;

            //O
            case 1:
                mino[X.get(0)][Y.get(0)] = 3;
                mino[X.get(1)+1][Y.get(1)] = 3;
                mino[X.get(2)][Y.get(2)+1] = 3;
                mino[X.get(3)+1][Y.get(3)+1] = 3;
                break;
            //S
            case 2:
                mino[X.get(0)+1][Y.get(0)] = 4;
                mino[X.get(1)][Y.get(1)] = 4;
                mino[X.get(2)-1][Y.get(2)+1] = 4;
                mino[X.get(3)][Y.get(3)+1] = 4;
                break;
            //Z
            case 3:
                mino[X.get(0)][Y.get(0)+1] = 5;
                mino[X.get(1)-1][Y.get(1)] = 5;
                mino[X.get(2)][Y.get(2)] = 5;
                mino[X.get(3)+1][Y.get(3)+1] = 5;
                break;
            //L
            case 4:
                mino[X.get(0)][Y.get(0)+1] = 6;
                mino[X.get(1)][Y.get(1)] = 6;
                mino[X.get(2)][Y.get(2)+2] = 6;
                mino[X.get(3)+1][Y.get(3)+2] = 6;
                break;
            //J
            case 5:
                mino[X.get(0)][Y.get(0)+1] = 7;
                mino[X.get(1)][Y.get(1)] = 7;
                mino[X.get(2)][Y.get(2)+2] = 7;
                mino[X.get(3)-1][Y.get(3)+2] = 7;
                break;
            //T
            case 6:
                mino[X.get(0)][Y.get(0)] = 8;
                mino[X.get(1)-1][Y.get(1)] = 8;
                mino[X.get(2)][Y.get(2)+1] = 8;
                mino[X.get(3)+1][Y.get(3)] = 8;
                break;

        }
        return mino;
    }
    public static int[][] MoveTetrimino(int random, List<Integer> cX , List<Integer> cY){
        for (int i = 0; i < 4; i++){
            mino[cX.get(i)][cY.get(i)] = random;
        }
        return mino;
    }
}
