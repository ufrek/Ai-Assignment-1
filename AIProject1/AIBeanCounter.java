package AIProject1;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;
import ProjectOneEngine.*;

public class AIBeanCounter implements Player 
{
    AIMinimax mima;
    public AIBeanCounter()
    {
        mima = new AIMinimax(0, 13);
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
