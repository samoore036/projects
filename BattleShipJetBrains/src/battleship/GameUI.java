package battleship;
import java.util.*;
public class GameUI {
    Scanner sc = new Scanner(System.in);
    Player player1 = new Player();
    Player player2 = new Player();
    int currentPlayer = 1;
    int p1ShipsSunk = 0;
    int p1CarrierHits = 0;
    int p1BattleshipHits = 0;
    int p1SubHits = 0;
    int p1CruiserHits = 0;
    int p1DestroyerHits = 0;
    int p2ShipsSunk = 0;
    int p2CarrierHits = 0;
    int p2BattleshipHits = 0;
    int p2SubHits = 0;
    int p2CruiserHits = 0;
    int p2DestroyerHits = 0;
    boolean gameOver = false;

    ArrayList<String> p1HitHistory = new ArrayList<>();
    ArrayList<String> p2HitHistory =  new ArrayList<>();
    public void start() {
        this.setUp();
        this.play();
    }

    public void play() {
        this.printBoth();
        System.out.println("");
        this.shotLoop();
    }

    public void switchPlayers() {
        System.out.println("Press enter and pass the move to another player");
        String pass = this.sc.nextLine();
        while (!pass.isEmpty()) {
            System.out.println("Please only press enter to pass the move to another player");
            pass = this.sc.nextLine();
        }
        if (this.currentPlayer == 1) {
            this.switchToP2();
        } else {
            this.switchToP1();
        }
    }

    public void switchToP1() {
        this.currentPlayer = 1;
    }

    public void switchToP2() {
        this.currentPlayer = 2;
    }

    public void shotLoop() {
        if (this.p1ShipsSunk == 5 || this.p2ShipsSunk == 5) {
            return;
        }
        try {
            String shot = sc.nextLine().toUpperCase();
            this.checkHitHistory(shot);
            int shotRow = player1.translate(shot.substring(0, 1));
            int shotColumn = Integer.valueOf(shot.substring(1)) - 1;
            System.out.println("");
            this.checkShot(shotRow, shotColumn);
        } catch (RuntimeException e) {
            System.out.println("Error! You entered the wrong coordinates. Try again:");
            System.out.println("");
            this.shotLoop();
        }
    }

    public void checkShot(int shotRow, int shotColumn) {
        if (this.p1ShipsSunk == 5 || this.p2ShipsSunk == 5) {
            return;
        }
        if (this.currentPlayer == 1) {
            if (player2.gameBoard[shotRow][shotColumn] == 'O') {
                player1.fogOfWar[shotRow][shotColumn] = 'X';
                this.checkShipHit(shotRow, shotColumn);
            } else {
                this.player1.fogOfWar[shotRow][shotColumn] = 'M';
                System.out.println("You missed!");
            }
        }
        if (this.currentPlayer == 2) {
            if (player1.gameBoard[shotRow][shotColumn] == 'O') {
                player2.fogOfWar[shotRow][shotColumn] = 'X';
                this.checkShipHit(shotRow, shotColumn);
            } else {
                this.player2.fogOfWar[shotRow][shotColumn] = 'M';
                System.out.println("You missed!");
            }
        }
        if (this.p1ShipsSunk == 5 || this.p2ShipsSunk == 5) {
            return;
        }
        this.switchPlayers();
        this.play();
    }

    public void checkShipHit(int shotRow, int shotColumn) {
        if (this.currentPlayer == 1) {
            if (this.player2.aircraftCarrier[shotRow][shotColumn] == 'O') {
                this.p2CarrierHits++;
                if (this.p2CarrierHits == 5) {
                    this.checkGameOver();
                    return;
                }
            }
            if (this.player2.battleship[shotRow][shotColumn] == 'O') {
                this.p2BattleshipHits++;
                if (this.p2BattleshipHits == 4) {
                    this.checkGameOver();
                    return;
                }
            }
            if (this.player2.submarine[shotRow][shotColumn] == 'O') {
                this.p2SubHits++;
                if (this.p2SubHits == 3) {
                    this.checkGameOver();
                    return;
                }
            }
            if (this.player2.cruiser[shotRow][shotColumn] == 'O') {
                this.p2CruiserHits++;
                if (this.p2CruiserHits == 3) {
                    this.checkGameOver();
                    return;
                }
            }
            if (this.player2.destroyer[shotRow][shotColumn] == 'O') {
                this.p2DestroyerHits++;
                if (this.p2DestroyerHits == 2) {
                    this.checkGameOver();
                    return;
                }
            }
        }
        if (this.currentPlayer == 2) {
            if (this.player1.aircraftCarrier[shotRow][shotColumn] == 'O') {
                this.p1CarrierHits++;
                if (this.p1CarrierHits == 5) {
                    this.checkGameOver();
                    return;
                }
            }
            if (this.player1.battleship[shotRow][shotColumn] == 'O') {
                this.p1BattleshipHits++;
                if (this.p1BattleshipHits == 4) {
                    this.checkGameOver();
                    return;
                }
            }
            if (this.player1.submarine[shotRow][shotColumn] == 'O') {
                this.p1SubHits++;
                if (this.p1SubHits == 3) {
                    this.checkGameOver();
                    return;
                }
            }
            if (this.player1.cruiser[shotRow][shotColumn] == 'O') {
                this.p1CruiserHits++;
                if (this.p1CruiserHits == 3) {
                    this.checkGameOver();
                    return;
                }
            }
            if (this.player1.destroyer[shotRow][shotColumn] == 'O') {
                this.p1DestroyerHits++;
                if (this.p1DestroyerHits == 2) {
                    this.checkGameOver();
                    return;
                }
            }
        }
        System.out.println("You hit a ship!");
    }

    public void checkGameOver() {
        if (this.currentPlayer == 1) {
            this.p2ShipsSunk++;
        } else if (this.currentPlayer == 2) {
            this.p1ShipsSunk++;
        }
        if (this.p1ShipsSunk == 5 || this.p2ShipsSunk == 5) {
            this.gameOver = true;
            System.out.println(String.format("Game over! %s has sunk all of %s's ships!", this.currentPlayer == 1 ? "Player 1" : "Player 2", this.currentPlayer == 1 ? "Player 2" : "Player 1"));
        } else {
            System.out.println("You sank a ship!");
        }
    }

    public void checkHitHistory(String shot) {
        if (this.currentPlayer == 1) {
            if (this.p1HitHistory.contains(shot)) {
                System.out.println("Error! Coordinates already entered. Try again:");
                this.shotLoop();
            } else {
                this.p1HitHistory.add(shot);
            }
        }
        if (this.currentPlayer == 2) {
            if (this.p2HitHistory.contains(shot)) {
                System.out.println("Error! Coordinates already entered. Try again:");
                this.shotLoop();
            } else {
                this.p2HitHistory.add(shot);
            }
        }
    }

    public void printBoth() {
        if (this.currentPlayer == 1) {
            player1.printFogOfWar();
            System.out.println("---------------------");
            player1.printGameboard();
            System.out.println("");
            System.out.println("Player 1, it's your turn:");
        }
        if (this.currentPlayer == 2) {
            player2.printFogOfWar();
            System.out.println("---------------------");
            player2.printGameboard();
            System.out.println("");
            System.out.println("Player 2, it's your turn:");
        }
    }
    public void setUp() {
        this.startBoard();
        System.out.println("Player 1, place your ships on the game field");
        System.out.println("");
        player1.printGameboard();
        player1.setShips();
        System.out.println("Player 2, place your ships on the game field");
        System.out.println("");
        player2.printGameboard();
        player2.setShips();
    }

    public void startBoard() {
        for (int i = 0; i < 10; i++) {
            Arrays.fill(player1.gameBoard[i], '~');
            Arrays.fill(player2.gameBoard[i], '~');
            Arrays.fill(player1.fogOfWar[i], '~');
            Arrays.fill(player2.fogOfWar[i], '~');
        }
    }
}
