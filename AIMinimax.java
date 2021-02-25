package AIProject1;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;
import ProjectOneEngine.*;


public class AIMinimax implements Player
{
    public Move getMove(GameState state)
    {
        int depth = 4;          //change this value to try out performance
        int maxEval = -1;
        int finalbin = -1;
        PlayerID curPlayer = state.getCurPlayer();
        int tempEval;
        List<GameState> successors = getSuccessors(state, curPlayer);
        for (GameState gameState : successors) 
        {
            if(gameState == null)
                continue;

           tempEval = Minimax(gameState, depth, GameRules.otherPlayer(curPlayer));
           //System.out.println("tempEval = " + tempEval);                  //check MinMax return value
           if(tempEval > maxEval)
           {
               finalbin = successors.indexOf(gameState);
               maxEval = tempEval;
           }     
        }
        System.out.println("maxEval" + maxEval);
        if(finalbin == -1)
        {
            System.out.println("MinMax messed up somewhere");
            return null;
        }
        else
        {
            Move move = new Move(finalbin, curPlayer);
            return move;
        }

    }

    //TODO: Fix something with null gamestate so we don't return illegal moves
    int Minimax(GameState gs, int depth, PlayerID curPlayer)
    {
        if(depth == 0 || gs.isGameOver() )
        {
            return evalFunc(gs);
        }

        List<GameState> successors = getSuccessors(gs, curPlayer);
       int eval;
        if(curPlayer == PlayerID.TOP)       //the AI we want to test and maximize
        {
            System.out.println("Top" + depth);
            int maxEval = -99999999;
            for (GameState gameState : successors)
            {
                if(gameState == null)                           //null == illegal move,figure out how to fix this
                    continue;
                
                eval = Minimax(gameState, depth-1, GameRules.otherPlayer(curPlayer));
                System.out.println("Eval, Depth " + eval + ", " + depth);
                maxEval = Integer.max(maxEval, eval); 
             }
             return maxEval;
        }
        else    //opponent is bottom minimize
        {
            System.out.println("Bot");
            int minEval = 99999999;
            for (GameState gameState : successors) 
            {
                if(gameState == null)
                    continue;
                eval = Minimax(gameState, depth-1, GameRules.otherPlayer(curPlayer));
                System.out.println("Eval, Depth " + eval + ", " + depth);
                minEval = Integer.min(minEval, eval); 
            }
            return minEval;
        }    
    }

    //just change the method called to change the evalFunction
    int evalFunc(GameState gs)
    {
        return howManyFarEvalFunc(gs, PlayerID.TOP);
        //return howManyNearEvalFunc(gs, PlayerID.TOP);
        //return howFarAheadEvalFunc(gs, PlayerID.TOP);
        //return stoneEvalFunc(gs, PlayerID.TOP);                         //assuming the top player is the minmax player
    }

    int stoneEvalFunc(GameState gs, PlayerID curPlayer)
    {
        return gs.getHome(curPlayer);
    }
    
    int howFarAheadEvalFunc(GameState gs, PlayerID curPlayer)
    {
        int difference =  gs.getHome(curPlayer) - gs.getHome(GameRules.otherPlayer(curPlayer));
        return difference;
    }

    int howManyNearEvalFunc(GameState gs, PlayerID curPlayer)
    {
        return gs.getStones(curPlayer, 0);
    }
    int howManyFarEvalFunc(GameState gs, PlayerID curPlayer)
    {
        return gs.getStones(curPlayer, 5);
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
            return null;
        else
            return successorList;
    }
    
    
    public String getPlayName()
    {
        return "Minimax AI";
    }
}
