package com.company.Display;

import com.company.Object.GameObject;
import java.util.Arrays;
import com.company.Coordinate.CoordinateInt;


public class Screen {
    private final CoordinateInt screenSize;
    private int[][] buffer;
    private static final char heartChar = '♥';
    private static final char notHeartChar = '♡';

    public CoordinateInt getScreenSize() {
        return screenSize;
    }

    public Screen(CoordinateInt screenSize) {
        this.screenSize = screenSize;
        this.buffer = new int[screenSize.x][screenSize.y];
    }

    private void paintPoint(CoordinateInt dot, int value) {
        int x = dot.x;
        int y = dot.y;
        if (x < 0 || x >= screenSize.x || y < 0 || y >= screenSize.y) {
            return;
        }

        // paint buffer
        buffer[x][y] = value;
    }

    // type defines an game object type to distinguish the printed char
    // 1 trace of bullet
    // 2 bullet current
    // 3 cannon
    // 4 target
    public void addObject(GameObject gameObject, int type) {
        CoordinateInt size = gameObject.getSize();
        CoordinateInt coordinate = gameObject.getCoordinate();
        
        int sizeX = size.x;
        int sizeY = size.y;
        int coorX = coordinate.x;
        int coorY = coordinate.y;

        int topLeftX = coorX - (sizeX - 1) / 2;
        int topLeftY = coorY - (sizeY - 1) / 2;
        for (int i = topLeftX; i < topLeftX + sizeX; i++) {
            for (int j = topLeftY; j < topLeftY + sizeY; j++) {
                paintPoint(new CoordinateInt(i, j), type);
            }
        }
    }

    public void clearBuffer() {
        // create a new buffer
        this.buffer = new int[screenSize.x][screenSize.y];
    }

    private void printGrass() {
        System.out.println(Color.GREEN_BOLD_BRIGHT);
        String[] grass = new String[screenSize.x];
        Arrays.fill(grass, "▲");
        for (int i = 0; i < screenSize.x; i++) {
            System.out.print(grass[i]);
        }
        System.out.println("");
        System.out.print(Color.RESET);
    }

    public void printOut() {
        StringBuffer sb = new StringBuffer('\n');
        for (int j = screenSize.y - 1; j >= 0; j--) {
            sb.append('\n');
            for (int i = 0; i < screenSize.x; i++) {
                // 1 trace of bullet
                // 2 bullet current
                // 3 cannon
                // 4 target
                switch (buffer[i][j]) {
                    case 1:
                        // trace bullet
                        sb.append('◼');
                        break;
                    case 2:
                        // bullet current
                        sb.append('▶');
                        break;
                    case 3:
                        sb.append('✪');
                        break;
                    case 4:
                        sb.append('⬢');
                        break;
                    default:
                        sb.append('◻');
                }
            }
            // wrap
        }

        String s = sb.toString();
        for (char c : s.toCharArray() ) {
            switch (c) {
                case '◼':
                    // trace bullet
                    System.out.print(c);
                    break;
                case '▶':
                    System.out.print(Color.RED_BRIGHT);
                    System.out.print(c);
                    System.out.print(Color.RESET);
                    // bullet current
                    break;
                case '✪':
                    System.out.print(Color.BLUE_BRIGHT);
                    System.out.print(c);
                    System.out.print(Color.RESET);
                    break;
                case '⬢':
                    System.out.print(Color.YELLOW_BRIGHT);
                    System.out.print(c);
                    System.out.print(Color.RESET);
                    break;
                default:
                    System.out.print(c);
            }
        }
        printGrass();
    }

    public static String colorString(String string, Color color) {
        StringBuffer sb = new StringBuffer();
        sb.append(color);
        sb.append(new String(string));
        sb.append(Color.RESET);
        return sb.toString();
    }

    public static String colorString(char[] charArray, Color color) {
        return colorString(new String(charArray), color);
    }

    // life counter
    public void showRemainedLife(int life) {
        if (life < 5 && life > 0) {
            char[] heartArr = new char[5];

            // print '♥'
            for (int i = 0; i < life; i++) {
                heartArr[i] = heartChar;
            }

            // print '♡'
            for (int i = life; i < 5; i++) {
                heartArr[i] = notHeartChar;
            }

            // print colorful characters
            System.out.println("Life: " + colorString(heartArr, Color.RED_BOLD));

        } else if (life <= 0) {
            System.out.println("Game over. Please try again\n");
        }
    }
}
