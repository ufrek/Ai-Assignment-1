package AIProject1;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;
import ProjectOneEngine.*;

public class AINearSightedCounter implements Player 
{
    AIMinimax mima;
    public AINearSightedCounter()
    {
        mima = new AIMinimax(1, 12);
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
