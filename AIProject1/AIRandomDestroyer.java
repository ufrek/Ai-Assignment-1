package AIProject1;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.util.ElementScanner14;
import java.util.Random;
import ProjectOneEngine.*;


public class AIRandomDestroyer implements Player
{
    public Move getMove(GameState state)
    {

        boolean done = false;
        PlayerID curPlayer = state.getCurPlayer();
        Move move = null;
        
        //find best win state or max Move
        List<GameState> successors = getSuccessors(state, curPlayer);
        int maxStones = 0;
        int finalbin = -1;
        int curPlayerStones = 0;
        int opponentStones = 0;
        for (GameState gs : successors)
        {
            if(gs == null)
                continue;
            
            curPlayerStones = gs.getHome(curPlayer);
            opponentStones = gs.getHome(GameRules.otherPlayer(curPlayer));
            
            if(gs.isGameOver())
            {
                if(curPlayerStones > opponentStones)
                {
                    move = new Move(successors.indexOf(gs), curPlayer);
                    return move;
                }
                else
                    continue;
            }

            //if not end game, jsut return the turn that gives you the max number of stones
            if(maxStones < curPlayerStones)
            {
                maxStones = curPlayerStones;
                finalbin = successors.indexOf(gs);
            }
        }

        if(finalbin == -1)          //if no moves are ideal, pick a random move
        {   
            Random rand = new Random();
            done = false;
            while ( ! done )
            {
                int bin = rand.nextInt(6);
                move = new Move(bin, curPlayer);
                if (GameRules.makeMove(state, move) != null)
                {
                    done = true;
                }
            }
            System.out.print("Random move");
            return move;
        }
        System.out.print(maxStones + "final bin" + finalbin);
        move = new Move(finalbin, curPlayer);
        return move;
    
    }

   List<GameState> getSuccessors(GameState state, PlayerID curPlayer)
    {
        List<GameState> successorList = new ArrayList<GameState>();
        Move tempMove;
        GameState tempState;
        for(int i = 0; i < 6; i++)
        {
            tempMove = new Move(i, curPlayer);
            tempState = GameRules.makeMove(state, tempMove);
            successorList.add(tempState);
            
        }
        if(successorList.isEmpty())
            return  null;
        else
            return successorList;
    }

    @Override
    public String getPlayName() {
        // TODO Auto-generated method stub
        return "Random Destoryer";
    }
}

