package AIProject1;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;
import ProjectOneEngine.*;

public class AIFarSightedCounter implements Player 
{
    AIMinimax mima;
    public AIFarSightedCounter()
    {
        mima = new AIMinimax(2, 12);
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
