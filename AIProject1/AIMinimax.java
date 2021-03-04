package AIProject1;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;
import ProjectOneEngine.*;


public class AIMinimax implements Player
{
    int heuristic;
    int depth = 13; //change this value to try out performance
    PlayerID player;

    public AIMinimax(int heuristic, int depth)
    {
        this.heuristic = heuristic;
        this.depth = depth;
    }
    
    public Move getMove(GameState state)
    {
                 
        int maxEval = -999999999;
        int finalbin = -1;
        PlayerID curPlayer = state.getCurPlayer();
        player = curPlayer;
        int tempEval;
        List<GameState> successors = getSuccessors(state, curPlayer);
        for (GameState gameState : successors) 
        {
            if(gameState == null)
                continue;

           tempEval = Minimax(gameState, depth, -99999999, 99999999, GameRules.otherPlayer(curPlayer));
           //System.out.println("tempEval = " + tempEval);                  //check MinMax return value
           if(tempEval > maxEval)
           {
               finalbin = successors.indexOf(gameState);
               maxEval = tempEval;
           }     
        }
        if(heuristic == 3 && maxEval == 0)
        {
            Random rand = new Random();
            finalbin = rand.nextInt(6);
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

    
    int Minimax(GameState gs, int depth, int alpha, int beta, PlayerID curPlayer)
    {
        if(depth == 0 || gs.isGameOver() )
        {
            return evalFunc(gs);
        }

        List<GameState> successors = getSuccessors(gs, curPlayer);
       int eval;
        if(curPlayer == player)       //the AI we want to test and maximize
        {
            //System.out.println("Top" + depth);
            int maxEval = -99999999;
            for (GameState gameState : successors)
            {
                if(gameState == null)                           //null == illegal move,figure out how to fix this
                    continue;
                
                eval = Minimax(gameState, depth-1, alpha, beta, GameRules.otherPlayer(curPlayer));
                //System.out.println("Eval, Depth " + eval + ", " + depth);
                maxEval = Integer.max(maxEval, eval); 
                alpha = Math.max(alpha, eval);
                if(beta <= alpha)
                    break;
             }
             return maxEval;
        }
        else    //opponent is bottom minimize
        {
            //System.out.println("Bot");
            int minEval = 99999999;
            for (GameState gameState : successors) 
            {
                if(gameState == null)
                    continue;
                eval = Minimax(gameState, depth-1, alpha, beta, GameRules.otherPlayer(curPlayer));
                //System.out.println("Eval, Depth " + eval + ", " + depth);
                minEval = Integer.min(minEval, eval); 
                beta = Math.min(beta, eval);
                if(beta <= alpha)
                    break;
            }
            return minEval;
        }    
    }

    //just change the method called to change the evalFunction
    int evalFunc(GameState gs)                  
    {
        switch (heuristic)
         {
            case 0:
                return stoneEvalFunc(gs, player);
            case 1:
                return howManyFarEvalFunc(gs, player);
            case 2:
                return howManyNearEvalFunc(gs, player);
            case 3:
                return howFarAheadEvalFunc(gs, player);

            default:
                System.out.println("Invalid Heuristic");
                return -99999999;
                
        }
        
      
    
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
        return   gs.getStones(curPlayer, 0) +  2 * stoneEvalFunc(gs, curPlayer);
    }
    int howManyFarEvalFunc(GameState gs, PlayerID curPlayer)
    {
        return gs.getStones(curPlayer, 5) + stoneEvalFunc(gs, curPlayer);
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

    @Override
    public String getPlayName() 
    {
        String h;
        switch (heuristic)
         {
            case 0:
               h = "Bean Counter";
               break;
            case 1:
                h = "Near Sided";
                break;
            case 2:
                h ="Far Sided";
                break;
            case 3:
                h = "Conqueror of Baab";

            default:
                h = "Invalid Eval";
                break;
         }

         return "Minimax: " + h;

    }
    
}
