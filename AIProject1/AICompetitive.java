package AIProject1;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;
import ProjectOneEngine.*;

public class AICompetitive implements Player 
{
    AIMinimax mima;
    public AICompetitive()
    {
        mima = new AIMinimax(3, 13);
    }

    @Override
    public Move getMove(GameState state) 
    {
       
        return mima.getMove(state);
    }

    @Override
    public String getPlayName() 
    {
        return mima.getPlayName();
    }
    
}
