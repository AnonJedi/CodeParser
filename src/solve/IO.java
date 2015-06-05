package solve;

import java.io.*;
import java.util.List;
import java.util.stream.Stream;

public class IO implements Input //класс чтения/записи
{
    public static void Writer(List<String> result)
    {
        try
        {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(new File("result.txt"), true));
            fileWriter.write(result.get(0) + "\r\n");

            fileWriter.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void CreateFile()
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

    @Override
    public char Reader(BufferedReader stream)
    {
        try
        {
            char []buffer = new char[1];
            buffer[0] = '$';
            stream.read(buffer);

            if (buffer[0] == '$')   //проверка конца файла
            {
                stream.close();
                throw new IndexOutOfBoundsException();
            }
            return buffer[0];
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
