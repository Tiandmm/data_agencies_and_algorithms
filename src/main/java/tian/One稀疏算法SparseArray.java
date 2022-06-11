package tian;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class One稀疏算法SparseArray {

    public static void main(String[] args) throws IOException {

        /*场景
         *
         * 一盘围棋 有白棋/黑棋位置 -- 剩余的全是没有下的空白位置。
         *       把这棋盘保存到一个二位数组当中。完全可以保存了，
         *           白棋/黑棋分别对应1、2保存进入二位数组当中--但，还有很多空白的位置被自动填充了0。 这些数据占用 怎么简介一些？
         *  例如棋盘
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	白	0	0	0	0	0
         *           0	0	0	0	0	0	黑	0	0	0	0
         *           0	0	0	0	0	白	0	黑	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         * 有太多的0 可以使用 稀疏算法
         *          11	11	4	    11、11分别对应对应棋盘的行、列数
         *          1	5	1	    1、5分别代表第二行、第六列  为什么行、列总是比数(1、5)多1，因为数组下表是从0开始的
         *          2	6	2	    2、6分别代表第三行、第七列
         *          3	5	1       3、5分别代表第四行、第六列
         *          3	7	2       3、7分别代表第四行、第八列
         * */

        method1();

        // 方法二差点 影响不大，。。。懒得写了，脑袋晕，不难，请快一点就行了，为不想看了。吃饭去了
        method2();

    }

    /**
     * 方法二比方法一多了一个落盘的过程，棋盘转稀疏数组的时候落盘到本地文件，本地文件再还原稀疏数组转棋盘
     */
    public static void method1() {
        /*场景
         *
         * 一盘围棋 有白棋/黑棋位置 -- 剩余的全是没有下的空白位置。
         *       把这棋盘保存到一个二位数组当中。完全可以保存了，
         *           白棋/黑棋分别对应1、2保存进入二位数组当中--但，还有很多空白的位置被自动填充了0。 这些数据占用 怎么简介一些？
         *  例如棋盘
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	白	0	0	0	0	0
         *           0	0	0	0	0	0	黑	0	0	0	0
         *           0	0	0	0	0	白	0	黑	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         * 有太多的0 可以使用 稀疏算法
         *          11	11	4	    11、11分别对应对应棋盘的行、列数
         *          1	5	1	    1、5分别代表第二行、第六列  为什么行、列总是比数(1、5)多1，因为数组下表是从0开始的
         *          2	6	2	    2、6分别代表第三行、第七列
         *          3	5	1       3、5分别代表第四行、第六列
         *          3	7	2       3、7分别代表第四行、第八列
         * */

        // 把一个 围棋棋盘 转换成一个二位数组 --->>> 每一个点映射到一个坐标

        //01 创建一个两位数组 11*11
        int[][] chess = new int[11][11];

        //02 把棋子所在的位置写入二位数组中
        //  举例 如果有两个棋子 1代表白棋 2代表黑棋
        chess[1][5] = 1;
        chess[3][5] = 1;
        chess[2][6] = 2;
        chess[3][7] = 2;
        //累加棋子个数(不知道棋盘有几个棋子的情况下)

        int count = 0;
        for (int[] ints : chess) {
            for (int anInt : ints) {
                if (anInt != 0) {
                    ++count;
                }
                System.out.print(anInt + "\t");
            }
            System.out.println();
        }

        if (count == 0) return;
        int[][] chessTransfer = new int[++count][3];
        // 第一行数据分别保存二维数组的行、列、棋子数
        chessTransfer[0][0] = chess.length;
        chessTransfer[0][1] = chess[chess.length - 1].length;
        chessTransfer[0][2] = --count;
        int transferCount = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chess[i][j] != 0) {
                    chessTransfer[++transferCount][0] = i;
                    chessTransfer[transferCount][1] = j;
                    chessTransfer[transferCount][2] = chess[i][j];
                }
            }
        }

        for (int i = 0; i < chessTransfer.length; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(chessTransfer[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("上面是棋盘，下面是稀疏数组转的");
        for (int[] ints : chessTransfer) {
            for (int anInt : ints) {
                System.out.print(anInt + "\t");
            }
            System.out.println();
        }

        int[][] transferChess = new int[chessTransfer[0][0]][chessTransfer[0][1]];
        for (int i = 1; i < chessTransfer.length; i++) {
            transferChess[chessTransfer[i][0]][chessTransfer[i][1]] = chessTransfer[i][2];
        }

        System.out.println("稀疏数组转棋盘");
        for (int[] ints : transferChess) {
            for (int anInt : ints) {
                System.out.print(anInt + "\t");
            }
            System.out.println();
        }
        System.out.println("method1结尾");
    }

    public static void method2() throws IOException {
        /*场景
         *
         * 一盘围棋 有白棋/黑棋位置 -- 剩余的全是没有下的空白位置。
         *       把这棋盘保存到一个二位数组当中。完全可以保存了，
         *           白棋/黑棋分别对应1、2保存进入二位数组当中--但，还有很多空白的位置被自动填充了0。 这些数据占用 怎么简介一些？
         *  例如棋盘
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	白	0	0	0	0	0
         *           0	0	0	0	0	0	黑	0	0	0	0
         *           0	0	0	0	0	白	0	黑	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         *           0	0	0	0	0	0	0	0	0	0	0
         * 有太多的0 可以使用 稀疏算法
         *          11	11	4	    11、11分别对应对应棋盘的行、列数
         *          1	5	1	    1、5分别代表第二行、第六列  为什么行、列总是比数(1、5)多1，因为数组下表是从0开始的
         *          2	6	2	    2、6分别代表第三行、第七列
         *          3	5	1       3、5分别代表第四行、第六列
         *          3	7	2       3、7分别代表第四行、第八列
         * */

        // 把一个 围棋棋盘 转换成一个二位数组 --->>> 每一个点映射到一个坐标

        //01 创建一个两位数组 11*11
        int[][] chess = new int[11][11];

        //02 把棋子所在的位置写入二位数组中
        //  举例 如果有两个棋子 1代表白棋 2代表黑棋
        chess[1][5] = 1;
        chess[3][5] = 1;
        chess[2][6] = 2;
        chess[3][7] = 2;
        //累加棋子个数(不知道棋盘有几个棋子的情况下)

        int count = 0;
        for (int[] ints : chess) {
            for (int anInt : ints) {
                if (anInt != 0) {
                    ++count;
                }
                System.out.print(anInt + "\t");
            }
            System.out.println();
        }

        if (count == 0) return;
        int[][] chessTransfer = new int[++count][3];
        // 第一行数据分别保存二维数组的行、列、棋子数
        chessTransfer[0][0] = chess.length;
        chessTransfer[0][1] = chess[chess.length - 1].length;
        chessTransfer[0][2] = --count;
        int transferCount = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chess[i][j] != 0) {
                    chessTransfer[++transferCount][0] = i;
                    chessTransfer[transferCount][1] = j;
                    chessTransfer[transferCount][2] = chess[i][j];
                }
            }
        }


        FileOutputStream outputStream = new FileOutputStream(new File("./output/chessTransfer.txt"));


        // 把稀疏数组遍历保存到本地文件当中
        StringBuilder strB = new StringBuilder();
        for (int i = 0; i < chessTransfer.length; i++) {
            for (int j = 0; j < 3; j++) {
                strB.append(i + "," + j + "," + chessTransfer[i][j] + ",");
            }
            // 换行操作
            strB.append(System.getProperty("line.separator"));
            // 保存的结果就是 棋盘下同一行的数据保存在一行当中 第一个数据的横、纵、值数据，第二个数据的横、纵、值数据，
            outputStream.write(strB.toString().getBytes());
            strB = new StringBuilder();
        }


        System.out.println("上面是棋盘，下面是稀疏数组转的");
        for (int[] ints : chessTransfer) {
            for (int anInt : ints) {
                System.out.print(anInt + "\t");
            }
            System.out.println();
        }

        int[][] transferChess = new int[chessTransfer[0][0]][chessTransfer[0][1]];
        for (int i = 1; i < chessTransfer.length; i++) {
            transferChess[chessTransfer[i][0]][chessTransfer[i][1]] = chessTransfer[i][2];
        }


        FileReader fileReader = new FileReader(new File("./output/chessTransfer.txt"));
        BufferedReader reader = new BufferedReader(fileReader);
        int line = 1;
        String str = null;
        List<String> list = new ArrayList<>();
        while ((str = reader.readLine()) != null) {//BufferedReader有readLine()，可以实现按行读取
            list.add(str);
            line++;
        }

        String[][] inputTo = new String[--line][3];

        for (int i = 0; i < list.size(); i++) {
            String[] split = list.get(i).split(",");
            inputTo[i][0] = split[2];
            inputTo[i][1] = split[5];
            inputTo[i][2] = split[8];
        }

        System.out.println("稀疏数组转棋盘");
        for (String[] ints : inputTo) {
            for (String anInt : ints) {
                System.out.print(anInt + "\t");
            }
            System.out.println();
        }

    }


}
