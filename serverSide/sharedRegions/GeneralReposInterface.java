package serverSide.sharedRegions;
import commInfra.Message;
import serverSide.main.SharedRegionInterface;


public class GeneralReposInterface implements SharedRegionInterface 
{
    public GeneralReposInterface()
    {

    }
    
    @Override
    public Message processAndReply(Message message) 
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasShutdown() 
    {
        // TODO Auto-generated method stub
        return false;
    }
    
}
