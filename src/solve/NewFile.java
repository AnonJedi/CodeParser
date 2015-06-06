package solve;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NewFile implements Creator
{
    @Override
    public void Create()
    {
        try
        {
            BufferedWriter creator = new BufferedWriter(new FileWriter(new File("result.txt")));
            creator.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
