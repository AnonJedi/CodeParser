package solve;

import java.io.*;

public class Writer implements Output
{
    BufferedWriter writer;

    public Writer(BufferedWriter writer)
    {
        this.writer = writer;
    }

    @Override
    public void Save(String s)
    {
        try
        {
            writer.write(s);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void Close()
    {
        try
        {
            this.writer.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
