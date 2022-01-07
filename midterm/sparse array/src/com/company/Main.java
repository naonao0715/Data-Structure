package com.company;

public class Main {

    public static void main(String[] args) {
	      // write your code here
         // creat a new original 2D array 11*11
         //0:没有棋子  1：黑子  2 ：蓝子
         int[][] chessArr1 = new int[11][11];
         chessArr1[1][2] = 1;
         chessArr1[2][3] = 2;
         chessArr1[3][4] = 3;
         for (int i = 0; i < chessArr1.length; i++){
             for (int j = 0; j < chessArr1[0].length;j++){
                 System.out.print(chessArr1[i][j] + " ");
             }
             System.out.println();//换行
         }
         int sum = 0;
         for (int i = 0 ; i < chessArr1.length; i ++){
             for (int j = 0; j < chessArr1[0].length;j++){
                 if (chessArr1[i][j] != 0) sum++;
             }
         }
         // System.out.print("sum:" + sum);
         int n = chessArr1.length;
         int m = chessArr1[0].length;
         int[][] spraseArr = new int[sum+1][3];
         spraseArr[0][0] = n;
         spraseArr[0][1] = m;
         spraseArr[0][2] = sum;
        System.out.println(spraseArr[0][0] + " " + spraseArr[0][1] + " " + spraseArr[0][2]);
         int count = 0;
        for (int i = 0 ; i < chessArr1.length; i ++){
            for (int j = 0; j < chessArr1[0].length;j++) {
                if (chessArr1[i][j] != 0) {
                    count++;
                    spraseArr[count][0] = i;
                    spraseArr[count][1] = j;
                    spraseArr[count][2] = chessArr1[i][j];
                    System.out.println(spraseArr[count][0] + " " + spraseArr[count][1] + " " + spraseArr[count][2]);
                }


            }
        }
        System.out.println();
        //还原成sprase array
        int [][] chessArr2 = new int[spraseArr[0][0]][spraseArr[0][1]];
        for (int i = 1; i < spraseArr.length; i++){
            for (int j = 0; j < spraseArr[0].length;j++){
                chessArr2[spraseArr[i][0]][spraseArr[i][1]] = spraseArr[i][2];
            }
        }
        for (int i = 0 ; i < chessArr2.length; i ++) {
            for (int j = 0; j < chessArr2[0].length; j++) {
                System.out.print(chessArr2[i][j] + " ");
               // System.out.print(chessArr2[i][j]);
            }
            System.out.println();
        }



    }
}
