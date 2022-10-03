public class Party {
    private int turnNumber;
    private int playersWins;
    private int compWins;
    boolean cheaterFlag;
    TsuEFa playerChoice;
    TsuEFa computerChoice;

    public Party(boolean cheaterFlag) {
        this.cheaterFlag = cheaterFlag;
        playersWins = 0;
        compWins = 0;
    }

    //Calculate turn results
    public int turn(TsuEFa playersChoice) {
        this.playerChoice = playersChoice;
        computerChoice = computerTurn(playersChoice);
        int result = whoIsWinner(playersChoice, computerChoice);
        if (result == 1) {playersWins++;}
        if (result == 2) {compWins++;}
        turnNumber++;
        return result;
    }

    //Calculate winner (1 - player won, 2 - computer won, 0 - draw)
    private int whoIsWinner (TsuEFa playersChoice, TsuEFa computerChoice) {
        int result=0;
        if (playersChoice==computerChoice) {
            return result;
        }
        switch (playersChoice) {
            case БУМАГА: {
                if (computerChoice== TsuEFa.КАМЕНЬ) {
                    result=1;
                    break;
                }
                else {
                    result=2;
                    break;
                }
            }
            case КАМЕНЬ: {
                if (computerChoice== TsuEFa.НОЖНИЦЫ) {
                    result=1;
                    break;
                }
                else {
                    result=2;
                    break;
                }
            }
            case НОЖНИЦЫ: {
                if (computerChoice== TsuEFa.БУМАГА) {
                    result=1;
                    break;
                }
                else {
                    result=2;
                    break;
                }
            }
        }
        return result;
    }

    //Calculate computer choice
    private TsuEFa computerTurn(TsuEFa playersChoice) {
        TsuEFa compTurn=TsuEFa.НОЖНИЦЫ;
        //If cheater detected
        if (cheaterFlag) {
            switch (playersChoice) {
                case КАМЕНЬ: {
                    compTurn = TsuEFa.БУМАГА;
                    break;
                }
                case БУМАГА: {
                    compTurn= TsuEFa.НОЖНИЦЫ;
                    break;
                }
                case НОЖНИЦЫ: {
                    compTurn = TsuEFa.КАМЕНЬ;
                    break;
                }
            }
        }
        //If normal player detected
        else {
            double turn = Math.random();
            if (turn < 0.3) {
                compTurn = TsuEFa.БУМАГА;
            } else {
                if (turn < 0.6) {
                    compTurn = TsuEFa.КАМЕНЬ;
                }
            }
        }
        return compTurn;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public int getPlayersWins() {
        return playersWins;
    }

    public int getCompWins() {
        return compWins;
    }

    public  TsuEFa getComputerChoice() {return computerChoice;}
}
