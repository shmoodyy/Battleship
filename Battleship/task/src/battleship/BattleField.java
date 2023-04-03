package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class BattleField {

    static Scanner scanner = new Scanner(System.in);
    static String[][] player1 = new String[11][11];
    static String[][] player2 = new String[11][11];
    static String[][] player1Fog = new String[11][11];
    static String[][] player2Fog = new String[11][11];
    static int player1CellsLeft = 17;
    static int player2CellsLeft = 17;
    static boolean shipHit;

    public BattleField() {
        System.out.println("Player 1, place your ships on the game field \n");
        printBattleMap(initializeMap(player1));
        takePositions(player1);
        initializeMap(player1Fog);
        System.out.println("\nPlayer 2, place your ships to the game field \n");
        printBattleMap(initializeMap(player2));
        takePositions(player2);
        initializeMap(player2Fog);
        fire(player1);
    }

    public static boolean shipSunk(String[][] player, int row, int column) {
        return !player[row][column].equals("O") && (row > 0 && !player[row - 1][column].equals("O"))
                && (row < 10 && !player[row + 1][column].equals("O"))
                && (column > 0 && !player[row][column - 1].equals("O"))
                && (column < 10 && !player[row][column + 1].equals("O"));
    }

    public static void fire(String[][] playerMap) {
        battleScene(playerMap);
        System.out.println("\nPlayer " + (playerMap == player1 ? "1" : "2") + ", it's your turn: \n");
        shotCoordinate(playerMap);
        while (true) {
            System.out.println("CELLS#1 LEFT = " + player1CellsLeft + " " + "CELLS#2 LEFT = " + player2CellsLeft);
            if (player1CellsLeft == 0 || player2CellsLeft == 0) break;
            System.out.println("\nPress Enter and pass the move to another player \n");
            scanner.nextLine();
            if (playerMap == player1) {
                fire(player2);
            } else if (playerMap == player2){
                fire(player1);
            }
        }
        printBattleMap(playerMap);
        System.out.println("\nYou sank the last ship. You won. Congratulations!");
    }

    public static void shotCoordinate(String[][] playerMap) {
        String[][] enemy = playerMap == player1 ? player2 : player1;
        String[][] enemyFog = playerMap == player1 ? player2Fog : player1Fog;
        String shotCell = scanner.nextLine().replaceAll("\\s", "").toUpperCase();
        System.out.println();
        String[] shotRow = shotCell.split("\\d");
        String[] shotColumn = shotCell.split("[A-Z]");
        int shotRowAsNumber = (shotRow[0].charAt(0)) - 64;
        int shotColumnAsNumber = Integer.parseInt(shotColumn[shotColumn.length - 1]);

        shipHit = false;
        try {
            if (enemy[shotRowAsNumber][shotColumnAsNumber].matches("O|X")) {
                shipHit = true;
                if (enemy == player1 && enemy[shotRowAsNumber][shotColumnAsNumber].equals("O")) player1CellsLeft--;
                else if (enemy == player2 && enemy[shotRowAsNumber][shotColumnAsNumber].equals("O")) player2CellsLeft--;
                enemy[shotRowAsNumber][shotColumnAsNumber] = "X";
                enemyFog[shotRowAsNumber][shotColumnAsNumber] = "X";
            } else if (player2[shotRowAsNumber][shotColumnAsNumber].matches("~|M")) {
                enemy[shotRowAsNumber][shotColumnAsNumber] = "M";
                enemyFog[shotRowAsNumber][shotColumnAsNumber] = "M";
            } else {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
                shotCoordinate(playerMap);
            }
        } catch (RuntimeException ShotException) {
            System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            shotCoordinate(playerMap);
        }
        System.out.println("You " + (shipHit ? "hit a ship!"
                + (shipSunk(enemy, shotRowAsNumber, shotColumnAsNumber) ? "\nYou sank a ship!" : "") : "missed!"));
    }

    public static void battleScene(String[][] playerMap) {
        if (playerMap == player1) {
            printBattleMap(player2Fog);
        } else {
            printBattleMap(player1Fog);
        }
        System.out.println("---------------------");
        printBattleMap(playerMap);
        System.out.println();
    }

    public static void takePositions(String[][] playerMap) {
        System.out.println("\nEnter the coordinates of the Aircraft Carrier (5 cells):");
        positionCoordinates(playerMap,"Aircraft Carrier", 5);
        System.out.println("\nEnter the coordinates of the Battleship (4 cells):");
        positionCoordinates(playerMap, "Battleship", 4);
        System.out.println("\nEnter the coordinates of the Submarine (3 cells):");
        positionCoordinates(playerMap, "Submarine", 3);
        System.out.println("\nEnter the coordinates of the Cruiser (3 cells):");
        positionCoordinates(playerMap, "Cruiser", 3);
        System.out.println("\nEnter the coordinates of the Destroyer (2 cells):");
        positionCoordinates(playerMap, "Destroyer", 2);
        System.out.println("\nPress Enter and pass the move to another player \n");
        scanner.nextLine();
    }

    public static void positionCoordinates(String[][] playerMap, String shipType, int shipLength) {
        String enteredCells = scanner.nextLine().replaceAll("\\s", "").toUpperCase();
        System.out.println();
        String[] cellRows = enteredCells.split("\\d");
        String[] cellColumns = enteredCells.split("[A-Z]");
        Arrays.sort(cellRows);
        Arrays.sort(cellColumns);

        int cellRow1AsNumber = (cellRows[cellRows.length - 2].charAt(0)) - 64;
        int cellColumn1AsNumber = Integer.parseInt(cellColumns[cellColumns.length - 2]);
        int cellRow2AsNumber = (cellRows[cellRows.length - 1].charAt(0)) - 64;
        int cellColumn2AsNumber = Integer.parseInt(cellColumns[cellColumns.length - 1]);

        try {
            boolean correctLocation =
                    (cellRow1AsNumber > 0 && cellRow2AsNumber > 0 && cellColumn1AsNumber > 0 && cellColumn2AsNumber > 0)
                            && (cellRows[cellRows.length - 2].equals(cellRows[cellRows.length - 1])
                            && !cellColumns[cellColumns.length - 2].equals(cellColumns[cellColumns.length - 1])
                            || !cellRows[cellRows.length - 2].equals(cellRows[cellRows.length - 1])
                            && cellColumns[cellColumns.length - 2].equals(cellColumns[cellColumns.length - 1]));

            boolean tooClose = cellRow1AsNumber > 1 && playerMap[cellRow1AsNumber - 1][cellColumn1AsNumber].equals("O")
                    || cellRow2AsNumber < 10 && playerMap[cellRow2AsNumber + 1][cellColumn2AsNumber].equals("O")
                    || cellColumn1AsNumber > 1 && playerMap[cellRow1AsNumber][cellColumn1AsNumber - 1].equals("O")
                    || cellColumn2AsNumber < 10 && playerMap[cellRow2AsNumber][cellColumn2AsNumber + 1].equals("O");

            boolean correctLength = Math.abs(cellColumn1AsNumber - cellColumn2AsNumber) == shipLength - 1
                    && cellRows[cellRows.length - 2].equals(cellRows[cellRows.length - 1])
                    || (Math.abs(cellRow1AsNumber - cellRow2AsNumber) == shipLength - 1
                    && !cellRows[cellRows.length - 2].equals(cellRows[cellRows.length - 1]));

            if (correctLocation && !tooClose && correctLength) {
                for (int i = cellRow1AsNumber; i <= cellRow2AsNumber; i++) {
                    if (cellColumn2AsNumber >= cellColumn1AsNumber) {
                        for (int j = cellColumn1AsNumber; j <= cellColumn2AsNumber; j++) {
                            playerMap[i][j] = "O";
                        }
                    } else {
                        for (int j = cellColumn2AsNumber; j <= cellColumn1AsNumber; j++) {
                            playerMap[i][j] = "O";
                        }
                    }
                }
                printBattleMap(playerMap);
            } else if (!correctLength){
                System.out.printf("Error! Wrong length of the %s! Try again:\n", shipType);
                positionCoordinates(playerMap, shipType, shipLength);
            } else if (tooClose) {
                System.out.println("Error! You placed it too close to another one. Try again:\n");
                positionCoordinates(playerMap, shipType, shipLength);
            }
            else {
                System.out.println("Error! Wrong ship location! Try again:\n");
                positionCoordinates(playerMap, shipType, shipLength);
            }
        } catch (RuntimeException positionException) {
            System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            positionCoordinates(playerMap, shipType, shipLength);
        }
    }

    public static void printBattleMap(String[][] map) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static String[][] initializeMap(String[][] map) {
        char rowLetters = (char) 65;
        int columnNumbers = 0;
        map[0][0] = " ";
        for (int rows = 1; rows < 11; rows++) {
            map[rows][0] = String.valueOf(rowLetters++);
            for (int columns = 1; columns < 11; columns++) {
                map[0][columns] = String.valueOf(columnNumbers + columns);
                map[rows][columns] = "~";
            }
        }
        return map;
    }
}